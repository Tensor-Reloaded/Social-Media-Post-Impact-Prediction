package uaic.info.account_management_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public final class TokenExchangeRequest {
    private String requestToken;
    private String verifier;
}
