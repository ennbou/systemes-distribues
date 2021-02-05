import * as React from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import Box from '@material-ui/core/Box';
import Collapse from '@material-ui/core/Collapse';
import IconButton from '@material-ui/core/IconButton';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import KeyboardArrowDownIcon from '@material-ui/icons/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@material-ui/icons/KeyboardArrowUp';
import axios from 'axios';
import { useKeycloak } from "@react-keycloak/web";

const useRowStyles = makeStyles({
  root: {
    '& > *': {
      borderBottom: 'unset',
    },
  },
});



function Row(props) {
  const { row } = props;
  const [open, setOpen] = React.useState(false);
  const classes = useRowStyles();
  

  return (
    <React.Fragment>
      <TableRow className={classes.root}>
        <TableCell>
          <IconButton
            aria-label="expand row"
            size="small"
            onClick={() => setOpen(!open)}
          >
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        <TableCell component="th" scope="row">
          {row.id}
        </TableCell>
        <TableCell align="right">{row.customerId}</TableCell>
        <TableCell >{row.customerName}</TableCell>
        <TableCell align="right">{row.billDate}</TableCell>
        <TableCell align="right">{row.total}</TableCell>
      </TableRow>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <Box sx={{ margin: 1 }}>
              <Typography variant="h6" gutterBottom component="div">
                Products Items
              </Typography>
              <Table size="small" aria-label="purchases">
                <TableHead>
                  <TableRow>
                    <TableCell>Id</TableCell>
                    <TableCell>productID</TableCell>
                    <TableCell align="right">Price(MAD)</TableCell>
                    <TableCell align="right">Qte</TableCell>
                    <TableCell align="right">Total</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {row.products.map((p) => (
                    <TableRow key={p.id}>
                      <TableCell component="th" scope="row">
                        {p.id}
                      </TableCell>
                      <TableCell align="right">{p.productID}</TableCell>
                      <TableCell align="right">{p.price}</TableCell>
                      <TableCell align="right">{p.quantity}</TableCell>
                      <TableCell align="right">
                        {p.price*p.quantity}
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>
    </React.Fragment>
  );
}



export default function CollapsibleTable() {
  const [rows, setRows] = React.useState([]);
  
  const { keycloak } = useKeycloak()

  React.useEffect(() => {
    const config = {
      method: 'get',
      url: 'http://localhost:8888/BILLING-SERVICE/bills/full',
      headers: {
        'Authorization': 'Bearer ' + keycloak.token
      }
    };

    axios(config).then(resp => {
      setRows(resp.data.map(item => {
        return {
          id: item.id,
          customerId: item.customerid,
          customerName: item.customer?.name,
          billDate: item.billingdate,
          total: item.total,
          products: item.productItems
        };
      }))
    }).catch((err) => {
      console.error(err);
    })

  }, [])

  return (
    <TableContainer component={Paper}>
      <Table aria-label="collapsible table">
        <TableHead>
          <TableRow>
            <TableCell />
            <TableCell>Bill ID</TableCell>
            <TableCell align="right">Customer ID</TableCell>
            <TableCell >Customer Name</TableCell>
            <TableCell align="right">Bill Date</TableCell>
            <TableCell align="right">Amount(MAD)</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <Row key={row.id} row={row} />
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}