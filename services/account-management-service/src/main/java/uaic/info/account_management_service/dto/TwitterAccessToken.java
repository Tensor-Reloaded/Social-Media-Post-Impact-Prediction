package uaic.info.account_management_service.dto;

public class TwitterAccessToken {

    private String accessToken;

    public TwitterAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
