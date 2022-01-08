package uaic.info.orchestrationservice.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class JwtService {
    @Value("${bearerSecretKey}")
    private String secretKey;
    @Value("${issuer}")
    private String issuer;
    @Value("${expInMinutes}")
    private Long expInMinutes;

    public String generate(@NotNull Long twitterID) {
        log.info("Generating Bearer token for twitter user " + twitterID);
        final Date now = new Date(System.currentTimeMillis());
        final Date expiration = Date.from(Instant.now().plus(expInMinutes, ChronoUnit.MINUTES));
        return JWT.create()
                .withIssuer(issuer)
                .withIssuedAt(now)
                .withSubject(twitterID.toString())
                .withExpiresAt(expiration)
                .withClaim("twitterID", twitterID)
                .sign(Algorithm.HMAC256(secretKey));
    }



    public void ensureValid(@NotNull String bearerToken) {
        JWT.require(Algorithm.HMAC256(secretKey))
                .withIssuer(issuer)
                .build()
                .verify(bearerToken);
    }

    public Long extractTwitterId(@NotNull String bearerToken) {
        return JWT.decode(bearerToken).getClaim("twitterID").asLong();
    }
}
