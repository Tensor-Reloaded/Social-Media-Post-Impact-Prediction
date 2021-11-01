import React from "react";
import { getPredictions } from "../services/PredictionsManagementService";
import HelloWorld from "./HelloWorld";
import PredictionsManagement from "./PredictionsManagement";

export default function App() {
  return (
    <div>
      <HelloWorld />
      <PredictionsManagement 
        predictions={getPredictions()}
      />
    </div>
  );
}
