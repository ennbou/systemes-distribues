import * as React from "react";
import Avatar from "@material-ui/core/Avatar";
import Button from "@material-ui/core/Button";
import CssBaseline from "@material-ui/core/CssBaseline";
import TextField from "@material-ui/core/TextField";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import Link from "@material-ui/core/Link";
import Grid from "@material-ui/core/Grid";
import Box from "@material-ui/core/Box";
import LockOutlinedIcon from "@material-ui/icons/LockOutlined";
import Typography from "@material-ui/core/Typography";
import Container from "@material-ui/core/Container";
import axios from "axios";
import qs from "qs";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";

function Copyright(props) {
  return (
    <Typography variant="body2" color="textSecondary" align="center" {...props}>
      {"Copyright © "}
      <Link color="inherit" href="ennbou.github.io">
        ENNBOU.com
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

export default function SignIn() {
  const [show, setShow] = React.useState(true);
  const [tokenAccess, setTokenAccess] = React.useState("");
  const [tokenRefresh, setTokenRefresh] = React.useState("");

  const handleSubmit = (event) => {
    event.preventDefault();

    const form = event.target;
    //form.username.value
    //form.password.value
    const data = qs.stringify({
      username: "bouchaib",
      password: "1234",
      grant_type: "password",
      client_id: "test"
    });

    const config = {
      method: "post",
      url:
        "http://localhost:8080/auth/realms/achat-en-ligne/protocol/openid-connect/token",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: data
    };

    axios(config)
      .then(function (response) {
        setTokenAccess(response.data.access_token);
        setTokenRefresh(response.data.refresh_token);
        setShow(false);
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  return (
    <div>
      {show ? (
        <Container component="main" maxWidth="xs">
          <CssBaseline />
          <Box
            sx={{
              marginTop: 8,
              display: "flex",
              flexDirection: "column",
              alignItems: "center"
            }}
          >
            <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
              <LockOutlinedIcon />
            </Avatar>
            <Typography component="h1" variant="h5">
              Sign in
            </Typography>
            <Box
              component="form"
              onSubmit={(e) => handleSubmit(e)}
              noValidate
              sx={{
                width: "100%", // Fix IE11 issue.
                mt: 1
              }}
            >
              <TextField
                margin="normal"
                required
                fullWidth
                id="username"
                label="Username"
                name="username"
                autoComplete="username"
                autoFocus
              />
              <TextField
                margin="normal"
                required
                fullWidth
                name="password"
                label="Password"
                type="password"
                id="password"
                autoComplete="current-password"
              />
              <FormControlLabel
                control={<Checkbox value="remember" color="primary" />}
                label="Remember me"
              />
              <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
              >
                Sign In
              </Button>
              <Grid container>
                <Grid item xs>
                  <Link href="#" variant="body2">
                    Forgot password?
                  </Link>
                </Grid>
                <Grid item>
                  <Link href="#" variant="body2">
                    {"Don't have an account? Sign Up"}
                  </Link>
                </Grid>
              </Grid>
            </Box>
          </Box>
          <Copyright sx={{ mt: 8, mb: 4 }} />
        </Container>
      ) : (
        <div>
          <Token name="Access" token={tokenAccess} />
          <Token name="Rfresh" token={tokenRefresh} />
        </div>
      )}
    </div>
  );
}

function Token(props) {
  return (
    <Card
      style={{ minWidth: 275, maxWidth: "calc(100vw - 300px)" }}
      variant="outlined"
    >
      <CardContent>
        <Typography variant="h5" component="div">
          {props.name}
        </Typography>
        <CssBaseline />
        <Typography component="div" style={{ wordWrap: "break-word" }}>
          {props.token}
        </Typography>
      </CardContent>
    </Card>
  );
}
