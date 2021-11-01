import React from "react";
import { render } from "@testing-library/react";
import { screen } from "@testing-library/dom";
import PredictionsManagement from "../components/PredictionsManagement";
import { predictions } from "../__mocks__/predictions";

describe("PredictionsManagement", () => {
  test("should display 3 remove prediction buttons when user has 3 predictions", () => {
    render(<PredictionsManagement predictions={predictions} />);
    const buttons = screen.queryAllByText("Remove");
    expect(buttons.length).toBe(3);
  });

  test("should display a specific text when user has no predictions", () => {
    render(<PredictionsManagement predictions={[]} />);
    const youHaveNoPredictionsText = screen.getByText("You have no predictions");
    expect(youHaveNoPredictionsText).toBeTruthy();
  });
});
