import React, {useState} from "react";
import {useKeycloak} from '@react-keycloak/web'

export default () => {

  const {keycloak, initialized} = useKeycloak()

  return (
    <div>
      <p>Status</p>
      <p>{` isAuthenticated: ${!!keycloak.authenticated}`}</p>
      <p>{` token: ${keycloak.token}`}</p>
      <p>{`initialized : ${initialized}`}</p>
      <p>{`authe : ${keycloak.authenticated}`}</p>
      <button onClick={() => {
        keycloak.login();
      }}>login
      </button>
      <button onClick={() => {
        keycloak.logout();
      }}>logout
      </button>
      <button onClick={() => {
        keycloak.register();
      }}>register
      </button>

      <div>
        {

          JSON.stringify(keycloak.idTokenParsed)
        }

      </div>
    </div>
  );
};

