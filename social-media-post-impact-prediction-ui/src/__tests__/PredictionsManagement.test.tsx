import React from "react";
import PredictionsManagement from "../pages/PredictionsManagement";
import { render } from "@testing-library/react";
import { fireEvent, screen } from "@testing-library/dom";
import { mockPredictions } from "../__mocks__/predictions";

describe("PredictionsManagement", () => {
  test("should display a specific message when no prediction is provided", () => {
    render(<PredictionsManagement predictions={[]} />);
    const youHaveNoPredictionMessage = screen.getByText(
      "You have no predictions"
    );
    expect(youHaveNoPredictionMessage).toBeTruthy();
  });

  test("should display a specific message after 1 prediction is provided and is removed", () => {
    render(<PredictionsManagement predictions={[mockPredictions[0]]} />);
    const removeButton = screen.getByText("Remove");
    fireEvent.click(removeButton);
    const youHaveNoPredictionMessage = screen.getByText(
      "You have no predictions"
    );
    expect(youHaveNoPredictionMessage).toBeTruthy();
  });

  test("should display 6 predictions when 6 predictions are provided", () => {
    render(<PredictionsManagement predictions={mockPredictions} />);
    const predictions = screen.queryAllByLabelText("prediction");
    expect(predictions.length).toBe(6);
  });

  test("should display 5 predictions after 6 predictions are provided and one of them is removed", () => {
    render(<PredictionsManagement predictions={mockPredictions} />);
    const firstRemoveButton = screen.getAllByText("Remove")[0];
    fireEvent.click(firstRemoveButton);
    const predictions = screen.queryAllByLabelText("prediction");
    expect(predictions.length).toBe(5);
  });
});
