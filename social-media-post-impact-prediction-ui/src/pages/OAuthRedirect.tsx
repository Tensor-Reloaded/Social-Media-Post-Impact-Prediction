import React, { useEffect } from "react";
import queryString from 'query-string';
import { exchangeForBearer, redirectErrorPage, useAuthorizationContext } from "../services/AuthorizationService";

const TOKEN_KEY = "oauth_token"
const VERIFIER_KEY = "oauth_verifier"

const redirectHome = () => {window.location.href = "/"}

export default function OAuthRedirect() {

    const { state, setBearer } = useAuthorizationContext();

    useEffect(() => {
        console.log(state)
        if (!state.isLoggedIn) {
            const params = queryString.parse(window.location.search);
            console.log(params)
            if (TOKEN_KEY in params && VERIFIER_KEY in params) {
                exchangeForBearer(params[TOKEN_KEY] as string, params[VERIFIER_KEY] as string)
                    .then(setBearer)
                    .then(redirectHome);
            } else {
                redirectErrorPage();
            }
        }
    })

    return (
        <h4>Loading...</h4>
    );
}