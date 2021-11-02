import React from "react";
import { render } from "@testing-library/react";
import GetPrediction from "../components/GetPrediction";

describe("GetPrediction", () => {
    test("should display an input field for adding images" , () => {
        const result = render(<GetPrediction />);
        const imageField = result.container.querySelector('#image-field');
        expect(imageField).toBeTruthy();
    });

    test("should display an input field for adding a tweet description", () => {
        const result = render(<GetPrediction />);
        const descriptionField = result.container.querySelector('#description-field');
        expect(descriptionField).toBeTruthy();
    });
});