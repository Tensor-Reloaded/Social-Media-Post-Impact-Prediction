import React from "react";
import { Container, Nav, Navbar } from "react-bootstrap";
import { useAuthorizationContext } from "../services/AuthorizationService";

export default function CustomNavbar() {
  const {state} = useAuthorizationContext()
  return (
    <Navbar collapseOnSelect expand="lg" bg="primary" variant="dark">
      <Container>
        <Navbar.Brand href="/">
          Social Media Post Impact Prediction
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link href="/">Home</Nav.Link>
            { state.isLoggedIn && <Nav.Link href="/get-prediction">Get a prediction</Nav.Link> }
            { state.isLoggedIn && <Nav.Link href="/predictions-management">
              Manage predictions
            </Nav.Link>}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}
