import React from "react";
import { Prediction } from "../models/Prediction";
import { removePrediction } from "../services/PredictionsManagementService";

export default function PredictionsManagement(props: {
  predictions: Prediction[];
}) {
  const { predictions } = props;

  if (predictions.length === 0) {
    return <h1>You have no predictions</h1>;
  }

  return (
    <div>
      {predictions.map((prediction: Prediction) => {
        return (
          <div key={prediction.tweetId}>
            <h1>Prediction for tweet {prediction.tweetId}</h1>
            <h2>Description: {prediction.description}</h2>
            <h3>
              Predicted number of likes: {prediction.predictedNumberOfLikes}
            </h3>
            <button onClick={removePrediction(prediction.id)}>Remove</button>
          </div>
        );
      })}
    </div>
  );
}
