import { Post } from "../models/Post";
import { Prediction } from "../models/Prediction";
import { mockPredictions } from "../__mocks__/predictions";

export function getPrediction(post: Post): number {
  const min = 100;
  const max = 1000;
  const randomNumber = Math.floor(min + Math.random() * (max - min));
  return randomNumber;
}

export function getPredictions(): Prediction[] {
  return mockPredictions;
}

export function createPrediction(prediction: Prediction): void {}

export function removePredictionById(id: number): void {}
