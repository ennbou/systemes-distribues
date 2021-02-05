import React from "react";
import {StylesProvider} from "@material-ui/core";
import {ReactKeycloakProvider} from '@react-keycloak/web'
import Test from './components/test';
import Drawer from './components/drawer';

import keycloak from "./keycloak";

export default function App() {

  return (
    <ReactKeycloakProvider authClient={keycloak}>
      <StylesProvider injectFirst>
        <Drawer />
      </StylesProvider>
    </ReactKeycloakProvider>
  );
}
