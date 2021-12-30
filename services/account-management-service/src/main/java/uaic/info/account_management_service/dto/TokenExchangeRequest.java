package uaic.info.account_management_service.dto;

import lombok.Data;

@Data
public final class TokenExchangeRequest {
    private final String requestToken;
    private final String verifier;
}
