import { Prediction } from "../models/Prediction";

let predictions: Prediction[] = [
  { id: 1, tweetId: 1, predictedNumberOfLikes: 50, description: "Test text" },
  { id: 2, tweetId: 2, predictedNumberOfLikes: 67, description: "Test text" },
  { id: 3, tweetId: 3, predictedNumberOfLikes: 85, description: "Test text" },
];

export function getPredictions(): Prediction[] {
  return predictions;
}

export function removePrediction(id: number): string {
  for (let prediction of predictions) {
    if (prediction.id === id) {
      // send remove prediction request to server
      return `Prediction with id ${id} removed`;
    }
  }
  return `Prediction with id ${id} not found`;
}
