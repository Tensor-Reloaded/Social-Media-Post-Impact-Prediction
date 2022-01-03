import axios from "axios";
import config from "../enviroments/enviroment";
import { Post } from "../models/Post";
import { Prediction } from "../models/Prediction";
import { mockPredictions } from "../__mocks__/predictions";

export function getPrediction(post: Post): number {
  const min = 100;
  const max = 1000;
  const randomNumber = Math.floor(min + Math.random() * (max - min));
  return randomNumber;
}

export function getPredictions(bearer: String): Promise<Prediction[]> {
  return axios
    .get<Prediction[]>(config.apiPrefix + config.allPredictionsEndpoint, {
      headers: {
        "Authorization": `${bearer}`
      }
    })
    .then(res => res.data);
}

export function createPrediction(prediction: Prediction): void { }

export function removePredictionById(bearer: String, id: number): Promise<boolean> { 
  return axios
  .delete<Prediction[]>(config.apiPrefix + config.allPredictionsEndpoint + `/${id}`, {
    headers: {
      "Authorization": `${bearer}`
    }
  })
  .then(res => res.status == 200);
}
