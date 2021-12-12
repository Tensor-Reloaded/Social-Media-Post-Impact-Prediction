package uaic.info.orchestrationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetAccessTokenRequestBody {
    @JsonProperty("oauthToken")
    private String oauthToken;
    @JsonProperty("oauthVerifier")
    private String oauthVerifier;
}
