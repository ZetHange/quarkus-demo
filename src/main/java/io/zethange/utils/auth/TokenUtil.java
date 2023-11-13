package io.zethange.utils.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.zethange.entity.user.User;
import io.zethange.model.user.UserDto;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Utility class for generating and parsing access and refresh tokens.
 */
@ApplicationScoped
public class TokenUtil {
    @ConfigProperty(name = "security.refresh")
    String refresh;

    @ConfigProperty(name = "security.access")
    String access;

    public String generateAccessToken(UserDto user) {
        Algorithm algorithmAccess = Algorithm.HMAC256(access);
        return JWT.create()
                .withIssuer("access")
                .withClaim("id", user.getId())
                .withExpiresAt(Instant.now().plus(30, ChronoUnit.MINUTES))
                .sign(algorithmAccess);
    }

    public String generateRefreshToken(Long id) {
        Algorithm algorithmRefresh = Algorithm.HMAC256(refresh);
        return JWT.create()
                .withIssuer("refresh")
                .withClaim("id", id)
                .withExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS))
                .sign(algorithmRefresh);
    }

    public User parseAccessToken(String token) throws JWTVerificationException {
        User user = new User();
        Algorithm algorithmAccess = Algorithm.HMAC256(access);

        JWTVerifier verifier = JWT.require(algorithmAccess)
            .withIssuer("access")
            .build();
        DecodedJWT decodedJWT = verifier.verify(token);

        user.id = decodedJWT.getClaim("id").as(Long.class);
        user.setUsername(decodedJWT.getClaim("username").as(String.class));
        return user;
    }

    public Long parseRefreshToken(String token) throws JWTVerificationException {
        Algorithm algorithmRefresh = Algorithm.HMAC256(refresh);

        JWTVerifier verifier = JWT.require(algorithmRefresh)
                .withIssuer("refresh")
                .build();

        DecodedJWT decodedJWT = verifier.verify(token);

        return decodedJWT.getClaim("id").as(Long.class);
    }
}
