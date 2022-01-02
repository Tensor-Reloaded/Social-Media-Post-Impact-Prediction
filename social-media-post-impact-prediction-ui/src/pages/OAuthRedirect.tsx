import React, { useEffect } from "react";
import queryString from 'query-string';
import { exchangeForBearer, redirectErrorPage, useAuthorizationContext } from "../services/AuthorizationService";

const TOKEN_KEY = "oauth_token"
const VERIFIER_KEY = "oauth_verifier"

export default function OAuthRedirect() {

    const { state, setBearer } = useAuthorizationContext();

    useEffect(() => {
        console.log("asd")
        const params = queryString.parse(window.location.search);
        console.log(params)
        if (TOKEN_KEY in params && VERIFIER_KEY in params) {
            exchangeForBearer(params[TOKEN_KEY] as string, params[VERIFIER_KEY] as string)
                .then(setBearer);
        } else {
            // redirectErrorPage();
        }
    })

    return (
        // <p>{state.bearer}, {state.isLoggedIn}</p>
        <p>asd</p>
    );
}