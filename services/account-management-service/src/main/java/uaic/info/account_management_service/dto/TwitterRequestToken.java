package uaic.info.account_management_service.dto;

import twitter4j.auth.RequestToken;

public class TwitterRequestToken {

    private RequestToken requestToken;

    public TwitterRequestToken(RequestToken requestToken) {
        this.requestToken = requestToken;
    }

    public RequestToken getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(RequestToken requestToken) {
        this.requestToken = requestToken;
    }
}
