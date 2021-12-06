package uaic.info.account_management_service.dto;

public class TwitterRequestToken {

    private String requestToken;

    public TwitterRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }
}
