package uaic.info.account_management_service.dto;

import twitter4j.auth.RequestToken;

public class GetAccessTokenRequestBody {
    private RequestToken oauthToken;

    private String oauthVerifier;

    public GetAccessTokenRequestBody(RequestToken oauthToken, String oauthVerifier) {
        this.oauthToken = oauthToken;
        this.oauthVerifier = oauthVerifier;
    }

    public RequestToken getOauthToken() {
        return oauthToken;
    }

    public void setOauthToken(RequestToken oauthToken) {
        this.oauthToken = oauthToken;
    }

    public String getOauthVerifier() {
        return oauthVerifier;
    }

    public void setOauthVerifier(String oauthVerifier) {
        this.oauthVerifier = oauthVerifier;
    }
}
