import axios from 'axios';
import React, { useContext, useState } from 'react';
import config from '../enviroments/enviroment';

const LOGIN_ERROR_PAGE = "/login-error?code=500"

interface RedirectURLResponse {
    redirectURL: string
}

interface BearerTokenResponse {
    token: string
}

interface TokenExchangeRequest {
    requestToken: string,
    verifier: string
}

interface AuthorizationState {
    isLoggedIn: boolean,
    bearer: string
}

interface IAuthorizationContext {
    state: AuthorizationState,
    setBearer(bearer: string): void,
    logOut(): void
}



const AuthorizationContext = React.createContext<IAuthorizationContext>(null);

export function AuthenticationProvider({ children }) {

    const [state, setState] = useState<AuthorizationState>()

    function setBearer(bearer: string) {
        const isLoggedIn = true
        setState({ isLoggedIn, bearer })
    }

    function logOut() {
        setState({ isLoggedIn: false, bearer: null })
    }

    return <AuthorizationContext.Provider value={{ state, setBearer, logOut }}>
        { children }
    </AuthorizationContext.Provider>
}

function getRedirectURL(): Promise<string | null> {
    return axios
        .get<RedirectURLResponse>(config.apiPrefix + config.oauthRedirectEndpoint)
        .then(res => res.data.redirectURL)
        .catch(_ => { redirectErrorPage(); return null })
}

export const startOAuthFlow = () => getRedirectURL().then(url => {
    console.log(url)
    window.location.href = url;
})

export const useAuthorizationContext = () => useContext(AuthorizationContext)

export function exchangeForBearer(requestToken: string, verifier: string): Promise<string | null> {
    const payload: TokenExchangeRequest = { requestToken, verifier }
    return axios
        .post<BearerTokenResponse>(config.apiPrefix + config.oauthBearerEndpoint, payload)
        .then(res => res.data.token)
        .catch(_ => { redirectErrorPage(); return null });
}

export function redirectErrorPage() {
    window.location.href = LOGIN_ERROR_PAGE;
}