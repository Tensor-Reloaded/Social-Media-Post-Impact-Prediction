import { Post } from "../models/Post";
import axios from 'axios';
import queryString from 'query-string';
import { useEffect } from 'react';
import { Prediction } from "../models/Prediction";
import { StatusUpdate } from "../models/StatusUpdate";
import config from "../enviroments/enviroment";


function getRedirectURL() : Promise<string | void> {
    return axios({
            url: `/api/v1/request_token`,
            method: 'GET'
        }).then(function(response) : string {
            return response.data.redirectUrl;
        }).catch(error => {
            console.log(error);
            window.location.href="/login-error?code=500";
        });
}

function getAccessToken(requestToken, oauthVerifier) {
    if (requestToken && oauthVerifier) {
        axios({
            url: `/api/v1/access_token`,
            method: 'POST',
            data: { requestToken, oauthVerifier }
        }).then(response => {
            localStorage.setItem('accessToken', response.data.accessToken);
            window.location.href="/get-prediction";
        }).catch(error => {
            console.log(error);
            window.location.href="/login-error?code=500";
        });
    }
    else {
        window.location.href="/login-error?code=Unauthorized App";
    }
}

function retrieveTokensFromURL(responseURL) {
    const {oauth_token, oauth_verifier} = queryString.parse(window.location.search);
    return {oauth_token, oauth_verifier};
}

export function signIn(): void {
    let redirectUrl = getRedirectURL();
    const redirectUrlString = `${redirectUrl}`
    window.location.href = redirectUrlString;

    useEffect(() => {
        (async() => {
            const {oauth_token, oauth_verifier} = retrieveTokensFromURL(window.location.search);
            getAccessToken(oauth_token, oauth_verifier);
        })();
    }, []);
}

export function postTweet(token: string, post: StatusUpdate): Promise<void> {
    return axios
        .post<void>(
            config.apiPrefix + config.updateStatusEndpoint,
            post, {
                headers: {
                    "Authorization": `${token}`
                }
            })
        .then(() => {
            console.log("Succesfully posted status update");
        })
}