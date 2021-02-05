import * as React from "react";
import PropTypes from "prop-types";
import AppBar from "@material-ui/core/AppBar";
import CssBaseline from "@material-ui/core/CssBaseline";
import Divider from "@material-ui/core/Divider";
import Drawer from "@material-ui/core/Drawer";
import Hidden from "@material-ui/core/Hidden";
import IconButton from "@material-ui/core/IconButton";
import InboxIcon from "@material-ui/icons/MoveToInbox";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";
import MenuIcon from "@material-ui/icons/Menu";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import {makeStyles, useTheme} from "@material-ui/core/styles";
import {BrowserRouter as Router, Link, Route, Switch} from "react-router-dom";
import Products from "./products/products";
import Customers from "./customers/customers";
import Test from './test';
import {useKeycloak} from '@react-keycloak/web'
import {Button} from "@material-ui/core";
import MenuItem from '@material-ui/core/MenuItem';
import Menu from '@material-ui/core/Menu';
import AccountCircle from '@material-ui/icons/AccountCircle';
import PrivateRoute from './private-route';
import EditCustomer from './customers/edit';
import EditProduct from './products/edit';
import Bills from './billing/list';
import AddBills from './billing/add';

export const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
  root: {
    display: "flex"
  },
  drawer: {
    [theme.breakpoints.up("sm")]: {
      width: drawerWidth,
      flexShrink: 0
    }
  },
  appBar: {
    [theme.breakpoints.up("sm")]: {
      width: `calc(100% - ${drawerWidth}px)`,
      marginLeft: drawerWidth
    }
  },
  menuButton: {
    marginRight: theme.spacing(2),
    [theme.breakpoints.up("sm")]: {
      display: "none"
    }
  },
  // necessary for content to be below app bar
  toolbar: theme.mixins.toolbar,
  drawerPaper: {
    boxSizing: "border-box",
    width: drawerWidth
  },
  content: {
    flexGrow: 1,
    padding: theme.spacing(3)
  }
}));

function ResponsiveDrawer(props) {
  const {window} = props;
  const classes = useStyles();
  const theme = useTheme();
  const [mobileOpen, setMobileOpen] = React.useState(false);

  const handleDrawerToggle = () => {
    setMobileOpen(!mobileOpen);
  };


  const drawer = (
    <div>
      <div className={classes.toolbar}/>
      <Divider/>
      <List>
        <ListItem button component={Link} to="/customers" key="1" onClick={() => mobileOpen && handleDrawerToggle()}>
          <ListItemIcon>
            <InboxIcon/>
          </ListItemIcon>
          <ListItemText primary="Customers"/>
        </ListItem>
        <ListItem button component={Link} to="/products" key="2" onClick={() => mobileOpen && handleDrawerToggle()}>
          <ListItemIcon>
            <InboxIcon/>
          </ListItemIcon>
          <ListItemText primary="Products"/>
        </ListItem>
      </List>
      <Divider/>
      <ListItem button component={Link} to="/editcustomer" key="3" onClick={() => mobileOpen && handleDrawerToggle()}>
        <ListItemIcon>
          <InboxIcon/>
        </ListItemIcon>
        <ListItemText primary="Add Customers"/>
      </ListItem>
      <ListItem button component={Link} to="/editproduct" key="4" onClick={() => mobileOpen && handleDrawerToggle()}>
        <ListItemIcon>
          <InboxIcon/>
        </ListItemIcon>
        <ListItemText primary="Add Products"/>
      </ListItem>
      <Divider/>
      <ListItem button component={Link} to="/addbilling" key="5" onClick={() => mobileOpen && handleDrawerToggle()}>
        <ListItemIcon>
          <InboxIcon/>
        </ListItemIcon>
        <ListItemText primary="Bills"/>
      </ListItem>
      <Divider/>
      <ListItem button component={Link} to="/test" onClick={() => mobileOpen && handleDrawerToggle()} key="10">
        <ListItemIcon>
          <InboxIcon/>
        </ListItemIcon>
        <ListItemText primary="Test"/>
      </ListItem>
    </div>
  );

  const container =
    window !== undefined ? () => window().document.body : undefined;

  const {keycloak, initialized} = useKeycloak()

  const [anchorEl, setAnchorEl] = React.useState(null);


  const handleMenu = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  return (
    <div className={classes.root}>
      <CssBaseline/>
      <AppBar position="fixed" className={classes.appBar}>
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            edge="start"
            onClick={handleDrawerToggle}
            className={classes.menuButton}
          >
            <MenuIcon/>
          </IconButton>
          <Typography variant="h6" noWrap component="div" style={{flexGrow: 1}}>
            APP 1 ENNBOU
          </Typography>
          {!!keycloak.authenticated ? (
              <div>
                <IconButton
                  aria-label="account of current user"
                  aria-controls="menu-appbar"
                  aria-haspopup="true"
                  onClick={handleMenu}
                  color="inherit"
                >
                  <AccountCircle/>
                </IconButton>
                <Menu
                  id="menu-appbar"
                  anchorEl={anchorEl}
                  anchorOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                  }}
                  keepMounted
                  transformOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                  }}
                  open={Boolean(anchorEl)}
                  onClose={handleClose}
                >
                  <MenuItem onClick={handleClose}>{keycloak.idTokenParsed.name}</MenuItem>
                  <MenuItem onClick={handleClose}>
                    <Button variant="contained" onClick={() => {
                      keycloak.logout();
                    }}>Logout</Button>
                  </MenuItem>
                </Menu>
              </div>
            ) :
            (
              <div><Button variant="contained" onClick={() => {
                keycloak.login();
              }}>Login</Button>
                <Button variant="contained" onClick={() => {
                  keycloak.register();
                }}>Register</Button>
              </div>)
          }
        </Toolbar>
      </AppBar>
      <Router>
        <nav className={classes.drawer} aria-label="mailbox folders">
          {/* The implementation can be swapped with js to avoid SEO duplication of links. */}
          <Hidden smUp implementation="css">
            <Drawer
              container={container}
              variant="temporary"
              anchor={theme.direction === "rtl" ? "right" : "left"}
              open={mobileOpen}
              onClose={handleDrawerToggle}
              classes={{
                paper: classes.drawerPaper
              }}
              ModalProps={{
                keepMounted: true // Better open performance on mobile.
              }}
            >
              {drawer}
            </Drawer>
          </Hidden>
          <Hidden smDown implementation="css">
            <Drawer
              classes={{
                paper: classes.drawerPaper
              }}
              variant="permanent"
              open
            >
              {drawer}
            </Drawer>
          </Hidden>
        </nav>

        <main className={classes.content}>
          <div className={classes.toolbar}/>
          <Switch>
            <PrivateRoute roles={['ADMIN']} path="/customers" component={Customers}/>
            <PrivateRoute roles={['ADMIN']} path="/products" component={Products}/>
            <PrivateRoute roles={['ADMIN']} path="/editcustomer" component={EditCustomer}/>
            <PrivateRoute roles={['ADMIN']} path="/editproduct" component={EditProduct}/>
            <PrivateRoute roles={['ADMIN']} path="/addbills" component={AddBills}/>
            <PrivateRoute roles={['ADMIN']} path="/bills" component={Bills}/>
            <Route path="/test">
              <Test/>
            </Route>
            <Route path="/">
              Sallam
            </Route>
          </Switch>
        </main>
      </Router>
    </div>
  );
}


ResponsiveDrawer.propTypes = {
  /**
   * Injected by the documentation to work in an iframe.
   * You won't need it on your project.
   */
  window: PropTypes.func
};

export default ResponsiveDrawer;
