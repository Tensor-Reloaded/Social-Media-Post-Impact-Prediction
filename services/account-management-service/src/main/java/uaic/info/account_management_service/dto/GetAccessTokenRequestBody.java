package uaic.info.account_management_service.dto;

public class GetAccessTokenRequestBody {
    private String oauthToken;

    private String oauthVerifier;

    public GetAccessTokenRequestBody(String oauthToken, String oauthVerifier) {
        this.oauthToken = oauthToken;
        this.oauthVerifier = oauthVerifier;
    }

    public String getOauthToken() {
        return oauthToken;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    public String getOauthVerifier() {
        return oauthVerifier;
    }

    public void setOauthVerifier(String oauthVerifier) {
        this.oauthVerifier = oauthVerifier;
    }
}
