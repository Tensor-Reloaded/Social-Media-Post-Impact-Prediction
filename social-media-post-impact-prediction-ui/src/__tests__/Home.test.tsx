import { render } from "@testing-library/react";
import React from "react";
import Home from "../pages/Home";
import { screen } from "@testing-library/dom";

describe("Home", () => {
  test("should display sign in with Twitter button when user is not logged in", () => {
    render(<Home />);
    const signInWithTwitterButton = screen.getByText("SIGN IN WITH TWITTER");
    expect(signInWithTwitterButton).toBeTruthy();
  });
});
