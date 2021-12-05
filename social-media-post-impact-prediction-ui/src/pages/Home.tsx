import React from "react";
import { Button } from "react-bootstrap";
import { Helmet } from "react-helmet";
import { signIn } from "../services/TwitterService";
import "./css/Home.css";

export default function Home() {
  const title = "Home";

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
        <Button variant="primary" onClick={() => signIn()}>
          SIGN IN WITH TWITTER
        </Button>
      </div>
    </>
  );
}
