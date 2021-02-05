const keycloak = new window.Keycloak({
  "auth-server-url": 'http://localhost:8080/auth',
  realm: 'achat-en-ligne',
  clientId: 'test',
  "public-client": true,
  "confidential-port": 0
});

export default keycloak;
