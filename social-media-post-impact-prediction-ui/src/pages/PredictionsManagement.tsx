import React, { useState } from "react";
import { Button, Card, Container, Row } from "react-bootstrap";
import { Helmet } from "react-helmet";
import { Prediction } from "../models/Prediction";
import { removePredictionById } from "../services/PredictionService";
import "./css/PredictionsManagement.css";

interface Props {
  predictions: Prediction[];
}

export default function PredictionsManagement(props: Props) {
  function removePrediction(predictionId: number): void {
    setPredictions(
      predictions.filter((prediction) => prediction.id !== predictionId)
    );
    removePredictionById(predictionId);
  }

  const title = "Manage predictions";
  const [predictions, setPredictions] = useState(props.predictions);

  return (
    <>
      <Helmet>
        <title>{title}</title>
      </Helmet>
      <div className="page-container">
        <div className="title">
          <h2>{title}</h2>
        </div>
        {predictions.length > 0 ? (
          <Container>
            <Row>
              {predictions.map((prediction) => (
                <Card
                  style={{ width: "18rem", margin: "15px", padding: "5px" }}
                  key={prediction.id}
                  aria-label="prediction"
                >
                  <Card.Title>
                    {prediction.predictedNumberOfLikes} likes
                  </Card.Title>
                  <Card.Img variant="top" src={prediction.image} />
                  <Card.Body>
                    <Card.Text>{prediction.description}</Card.Text>
                  </Card.Body>
                  <Button
                    size="sm"
                    variant="danger"
                    onClick={() => removePrediction(prediction.id)}
                  >
                    Remove
                  </Button>
                </Card>
              ))}
            </Row>
          </Container>
        ) : (
          <h3>You have no predictions</h3>
        )}
      </div>
    </>
  );
}
