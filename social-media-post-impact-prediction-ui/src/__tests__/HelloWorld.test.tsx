import { fireEvent } from "@testing-library/react";
import React from "react";
import { render } from "@testing-library/react";
import HelloWorld from "../components/HelloWorld";
import { screen } from "@testing-library/dom";

describe("HelloWorld", () => {
  test("should display Hello world when the button is clicked", () => {
    render(<HelloWorld />);
    const button = screen.getByText("Press me");
    fireEvent.click(button);
    const helloWorld = screen.getByText("Hello world");
    expect(helloWorld).toBeTruthy();
  });
});
