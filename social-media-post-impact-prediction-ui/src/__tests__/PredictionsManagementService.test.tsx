import {
  getPredictions,
  removePrediction,
} from "../services/PredictionsManagementService";

describe("PredictionsManagementService", () => {
  test("should retrieve 3 predictions when user has 3 predictions", () => {
    let predictions = getPredictions();
    expect(predictions.length).toBe(3);
  });

  test("should remove prediction with id 1 when prediction with id 1 exists", () => {
    const predictionId = 1;
    const response = removePrediction(predictionId);
    expect(response).toBe("Prediction with id 1 removed");
  });

  test("should return prediction with id 5 not found when prediction with id 5 doesn't exist", () => {
    const predictionId = 5;
    const response = removePrediction(predictionId);
    expect(response).toBe("Prediction with id 5 not found");
  });
});
