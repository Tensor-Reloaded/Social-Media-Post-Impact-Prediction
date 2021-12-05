import React from "react";
import GetPrediction from "../pages/GetPrediction";
import { render } from "@testing-library/react";
import { fireEvent, screen } from "@testing-library/dom";

describe("GetPrediction", () => {
  test("should display upload image button", () => {
    render(<GetPrediction />);
    const uploadImageButton = screen.getByText("UPLOAD IMAGE");
    expect(uploadImageButton).toBeTruthy();
  });

  test("should display predict button", () => {
    render(<GetPrediction />);
    const predictButton = screen.getByText("PREDICT");
    expect(predictButton).toBeTruthy();
  });

  test("should display post on Twitter button after predict button was pressed", () => {
    render(<GetPrediction />);
    const predictButton = screen.getByText("PREDICT");
    const descriptionInput = screen.getByLabelText("description-input");
    fireEvent.change(descriptionInput, { target: { value: "Description" } });
    fireEvent.click(predictButton);
    const postOnTwitterButton = screen.getByText("POST ON TWITTER");
    expect(postOnTwitterButton).toBeTruthy();
  });

  test("should display predict button after predict button was pressed and description was updated", () => {
    render(<GetPrediction />);
    const predictButton = screen.getByText("PREDICT");
    const descriptionInput = screen.getByLabelText("description-input");
    fireEvent.change(descriptionInput, { target: { value: "Description" } });
    fireEvent.click(predictButton);
    fireEvent.change(descriptionInput, {
      target: { value: "New description" },
    });
    const newPredictButton = screen.getByText("PREDICT");
    expect(newPredictButton).toBeTruthy();
  });
});
