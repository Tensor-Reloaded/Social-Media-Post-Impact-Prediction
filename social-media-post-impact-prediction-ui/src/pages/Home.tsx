import React, { ReactElement } from "react";
import { Button } from "react-bootstrap";
import { Helmet } from "react-helmet";
import { startOAuthFlow, useAuthorizationContext } from "../services/AuthorizationService"
import "./css/Home.css";

export default function Home() {
  const title = "Home";

  const {state, logOut} = useAuthorizationContext();

  var button: ReactElement;
  if (!state.isLoggedIn) {
    button = <Button variant="primary" onClick={startOAuthFlow}>SIGN IN WITH TWITTER</Button>
  } else {
    button = <Button variant="primary" onClick={logOut}>LOG OUT</Button>
  }

  return (
    <>
      <Helmet>
        <title>{title}</title>
      </Helmet>
      <div className="page-container">
        <div className="title">
          <h1>Social Media</h1>
          <h1 className="white-fill">Post Impact Prediction</h1>
        </div>
        { button }
      </div>
    </>
  );
}
