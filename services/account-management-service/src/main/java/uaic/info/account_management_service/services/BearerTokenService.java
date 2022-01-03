package uaic.info.account_management_service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import twitter4j.auth.AccessToken;
import uaic.info.account_management_service.entities.Account;
import uaic.info.account_management_service.exceptions.InvalidBearerTokenException;

import javax.validation.constraints.NotNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class BearerTokenService {
    private final AccountService accountService;
    private final JwtService jwtService;

    private AccessToken retrieveAccessTokenAndSecret(@NotNull String bearerToken) {
        final var extractedId = jwtService.extractTwitterId(bearerToken);
        Account queryResponse = accountService.getById(extractedId);
        log.info("Retrieved access token and secret");
        return new AccessToken(queryResponse.getKey(), queryResponse.getSecret());
    }

    private String renewBearerToken(@NotNull String bearerToken) {
        final var extractedId = jwtService.extractTwitterId(bearerToken);
        log.info("Renewed bearer token");
        return jwtService.generate(extractedId);
    }

    private String verifyBearerToken(@NotNull String bearerToken) throws InvalidBearerTokenException {
        if(!jwtService.isValid(bearerToken)){
            log.info("Bearer token is either expired or invalid");
            if(!jwtService.checkTokenSubject(jwtService.obtainJWTClaims(bearerToken))){
                log.info("Invalid bearer token");
                throw new InvalidBearerTokenException();
            } else {
                log.info("Expired bearer token");
                return renewBearerToken(bearerToken);
            }
        }
        else {
            log.info("Bearer token is valid, so it is not changed");
            return bearerToken;
        }
    }
}
