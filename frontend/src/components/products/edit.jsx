import * as React from 'react';
import TextField from '@material-ui/core/TextField';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import Typography from '@material-ui/core/Typography';
import SaveIcon from '@material-ui/icons/Save';
import LoadingButton from '@material-ui/lab/LoadingButton';
import axios from "axios";
import Snackbar from "@material-ui/core/Snackbar";
import MuiAlert from "@material-ui/core/Alert";
import { useKeycloak } from "@react-keycloak/web";
import { useHistory } from 'react-router-dom'

const useStyles = makeStyles((theme) => ({
  root: {
    '& .MuiTextField-root': {
      margin: theme.spacing(1),
      width: 400,
    },
  },
}));

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={2} ref={ref} variant="filled" {...props} />;
});

export default function Edit(props) {
  const classes = useStyles();
  const history = useHistory();
  const [pending, setPending] = React.useState(false);
  const [name, setName] = React.useState("");
  const [price, setPrice] = React.useState(0.0);
  const [success, setSuccess] = React.useState(true);

  const { keycloak } = useKeycloak()

  React.useEffect(() => {
    document.title = "Add Product";

    if (props.location.id) {
      document.title = "Edit Product";
      const config = {
        method: "get",
        url: "http://localhost:8888/INVENTORY-SERVICE/products/"+props.location.id,
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + keycloak.token
        }
      };

      axios(config)
        .then(function (response) {
          console.log(response);
          setName(response.data.name)
          setPrice(response.data.price)

        })
        .catch(function (error) {
          console.log(error);

        });
    }

  }, []);

  function handleSubmit(e) {
    e.preventDefault();
    setPending(true);
    console.log("sending....")
    let data = { name, price }
    if (props.location.id) data.id = Number(props.location.id);
    sendData(data);
  }

  function sendData(data) {
    console.log(data)


    const config = {
      method: `${data.id ? 'patch' : 'post'}`,
      url: `http://localhost:8888/INVENTORY-SERVICE/products/${data.id || ''}`,
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + keycloak.token
      },
      data: JSON.stringify(data)
    };

    axios(config)
      .then(function (response) {
        setSuccess(true);
        setTimeout(() => {
          setPending(false);
          setName("");
          setPrice(0.0);
          setOpen(true);
          setTimeout(() => history.push(`/products`),1000)
        }, 1000);
      })
      .catch(function (error) {
        console.log(error);
        setPending(false);
        setSuccess(false);
        setOpen(true);
      });
  }

  const [open, setOpen] = React.useState(false);

  const handleClose = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }
    setOpen(false);
  };

  return (
    <Container maxWidth="sm">
      <Typography variant="h4" component="h4">
      {props.location.id ? "Edit " : "Add "} Customer
      </Typography>
    <form className={classes.root}  autoComplete="off" onSubmit={handleSubmit}>
      <div>
        <TextField
          id="outlined-error"
          label="Name"
          name="name"
          value={name}
          onChange={(e)=> setName(e.target.value)}
          required
        />
      </div>
      <div>
        <TextField
          id="outlined-error-helper-text"
          label="Price(MAD)"
          name="price"
          min="0.01" step="0.01"
          type="number"
          value={price}
          onChange={(e)=> setPrice(e.target.value)}
          required
        />

      </div>
      <div>

        <LoadingButton
          type="submit"
          color="primary"
          pending={pending}
          pendingPosition="start"
          startIcon={<SaveIcon />}
          variant="contained"
        >
          Save
        </LoadingButton>

      </div>

    </form>
      <Snackbar open={open} autoHideDuration={2000} onClose={handleClose}>
        <Alert
          onClose={handleClose}
          severity={success ? "success" : "error"}
          className={classes.alert}
        >
          {success ? `${name} added successfully` : `failed adding ${name}`}
        </Alert>
      </Snackbar>
    </Container>
  );
}