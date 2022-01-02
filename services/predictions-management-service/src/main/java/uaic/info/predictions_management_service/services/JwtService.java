package uaic.info.predictions_management_service.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotNull;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class JwtService {
    private final static SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
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
        final Key signingKey = getSigningKey();
        return Jwts.builder()
                .setClaims(Map.of("twitterID", twitterID))
                .setIssuedAt(now)
                .setSubject(twitterID.toString())
                .setIssuer(issuer)
                .setExpiration(expiration)
                .signWith(SIGNATURE_ALGORITHM, signingKey)
                .compact();
    }

    private Key getSigningKey() {
        final byte[] keyBytes = DatatypeConverter.parseBase64Binary(secretKey);
        return new SecretKeySpec(keyBytes, SIGNATURE_ALGORITHM.getJcaName());
    }

    public boolean isValid(@NotNull String bearerToken) {
        final Key signingKey = getSigningKey();
        final Claims claims = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(bearerToken)
                .getBody();
        final Long twitterID = claims.get("twitterID", Long.class);
        final String subject = claims.getSubject();
        return claims.getExpiration().before(Date.from(Instant.now())) &&
                twitterID.toString().equals(subject);
    }

    public Long extractTwitterId(@NotNull String bearerToken) {
        final Key signingKey = getSigningKey();
        return Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(bearerToken)
                .getBody()
                .get("twitterID", Long.class);
    }
}
