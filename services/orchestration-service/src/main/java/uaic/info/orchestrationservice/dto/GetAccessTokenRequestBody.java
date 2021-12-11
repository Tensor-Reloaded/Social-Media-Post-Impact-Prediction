package uaic.info.orchestrationservice.dto;

import lombok.Data;

@Data
public class GetAccessTokenRequestBody {
    private final String oauthToken;
    private final String oauthVerifier;
}
