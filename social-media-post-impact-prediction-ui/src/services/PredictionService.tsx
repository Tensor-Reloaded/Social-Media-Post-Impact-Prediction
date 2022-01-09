import axios from "axios";
import config from "../enviroments/enviroment";
import { Post } from "../models/Post";
import { Prediction } from "../models/Prediction";
// import { mockPredictions } from "../__mocks__/predictions";


export function getPrediction(post: Post, bearer: string): Promise<number> {
  const formData = new FormData();
  formData.append("tweetText", post.description);
  formData.append("image", post.image);
  return axios
    .post<Prediction>(config.apiPrefix + config.predictEndpoint, formData, {
      headers: {
        "Authorization": `${bearer}`,
        "Content-Type": "multipart/form-data"
      }
    })
    .then(res => res.data)
    .then(data => data.predictedNumberOfLikes)
    .catch(err => {
      console.error(err);
      return -1;
    })
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

export function removePredictionById(bearer: String, id: number): Promise<boolean> { 
  return axios
  .delete<Prediction[]>(config.apiPrefix + config.allPredictionsEndpoint + `/${id}`, {
    headers: {
      "Authorization": `${bearer}`
    }
  })
  .then(res => res.status == 200);
}
