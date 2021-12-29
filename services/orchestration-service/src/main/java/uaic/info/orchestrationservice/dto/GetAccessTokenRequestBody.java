package uaic.info.orchestrationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import twitter4j.auth.RequestToken;

@Data
public class GetAccessTokenRequestBody {
    @JsonProperty("oauthToken")
    private RequestToken oauthToken;
    @JsonProperty("oauthVerifier")
    private String oauthVerifier;
}
