import React from 'react'
import { getPrediction } from '../services/PredictionService'

export default function GetPrediction() {

    return (
        <div className="wrapper">
            <h1>Give a tweet (image + description) and receive a prediction</h1>
            <form>
                <fieldset>
                    <label>
                        <p>Image:</p>
                        <input id="image-field" name="image" />
                    </label>
                    <br />
                    <label>
                        <p>Description:</p>
                        <input id="description-field" name="description" type="text"/>
                    </label>
                    <br />
                    <button type="button" onClick={getPrediction} >Submit</button>
                </fieldset>
            </form>
            {/* <span id="response"></span> */}
        </div>
    );
}