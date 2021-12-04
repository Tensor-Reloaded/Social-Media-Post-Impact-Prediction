import React from "react";
import "./Application.css";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Home from "./pages/Home";
import GetPrediction from "./pages/GetPrediction";
import PredictionsManagement from "./pages/PredictionsManagement";
import CustomNavbar from "./components/CustomNavbar";
import { getPredictions } from "./services/PredictionService";

export default function Application() {
  return (
    <Router>
      <CustomNavbar />
      <Switch>
        <Route path="/" exact render={() => <Home />} />
        <Route path="/get-prediction" render={() => <GetPrediction />} />
        <Route
          path="/predictions-management"
          render={() => (
            <PredictionsManagement predictions={getPredictions()} />
          )}
        />
      </Switch>
    </Router>
  );
}
