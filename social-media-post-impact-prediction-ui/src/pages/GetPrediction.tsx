import React, { useState } from "react";
import { useEffect } from "react";
import { useRef } from "react";
import { ChangeEvent } from "react";
import { Button, FloatingLabel, Form } from "react-bootstrap";
import { Helmet } from "react-helmet";
import { getPrediction } from "../services/PredictionService";
import { postTweet } from "../services/TwitterService";
import "./css/GetPrediction.css";

export default function GetPrediction() {
  const [description, setDescription] = useState("");
  const [image, setImage] = useState(null);
  const [imageURL, setImageURL] = useState(null);
  const [predictedNumberOfLikes, setPredictedNumberOfLikes] = useState(0);
  const [
    isPredictedNumberOfLikesDisplayed,
    setIsPredictedNumberOfLikesDisplayed,
  ] = useState(false);
  const fileInput = useRef(null);
  const title = "Get a prediction";

  function onImageChange(event: ChangeEvent<HTMLInputElement>): void {
    if (event.target.files && event.target.files.length > 0) {
      setImage(event.target.files[0]);
    } else {
      setImage(null);
    }
  }

  useEffect(() => {
    if (image !== null) {
      setImageURL(URL.createObjectURL(image));
    } else {
      setImageURL(null);
    }
  }, [image]);

  return (
    <>
      <Helmet>
        <title>{title}</title>
      </Helmet>
      <div className="page-container">
        <div className="title">
          <h2>{title}</h2>
        </div>
        <form>
          <input
            type="file"
            ref={fileInput}
            accept="image/*"
            onChange={(event) => {
              onImageChange(event);
              setIsPredictedNumberOfLikesDisplayed(false);
            }}
          />
          <Button variant="primary" onClick={() => fileInput.current.click()}>
            UPLOAD IMAGE
          </Button>
          {imageURL !== null && <img src={imageURL} />}
          <FloatingLabel controlId="descriptionTextarea" label="Description">
            <Form.Control
              as="textarea"
              aria-label="description-input"
              placeholder="Description"
              style={{ height: "8em", width: "25em" }}
              value={description}
              onChange={(event) => {
                setDescription(event.target.value);
                setIsPredictedNumberOfLikesDisplayed(false);
              }}
            />
          </FloatingLabel>
        </form>
        {!isPredictedNumberOfLikesDisplayed ? (
          <Button
            variant="primary"
            onClick={() => {
              const result = getPrediction({ description, image });
              setPredictedNumberOfLikes(result);
              setIsPredictedNumberOfLikesDisplayed(true);
            }}
          >
            PREDICT
          </Button>
        ) : (
          <>
            <p id="number-of-likes">
              Number of likes: {predictedNumberOfLikes}
            </p>
            <Button
              variant="primary"
              onClick={() => {
                postTweet({ description, image });
                // TODO: create prediction
              }}
            >
              POST ON TWITTER
            </Button>
          </>
        )}
      </div>
    </>
  );
}
