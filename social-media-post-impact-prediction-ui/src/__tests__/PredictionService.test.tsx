import {getPrediction} from "../services/PredictionService";

describe("PredictionService", () => {
    test("should return the number of likes predicted for the given tweet", () => {
        const response = getPrediction();
        expect(response).toBe("Predicted number of likes: 100");
    });
});