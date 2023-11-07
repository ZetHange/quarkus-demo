package io.zethange.utils.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.zethange.entity.User;
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

    /**
     * Generates an access token for the given user.
     *
     * @param user The user for which the access token will be generated.
     * @return The generated access token.
     */
    public String generateAccessToken(User user) {
        Algorithm algorithmAccess = Algorithm.HMAC256(access);
        return JWT.create()
                .withIssuer("access")
                .withClaim("id", user.id)
                .withClaim("username", user.getUsername())
                .withExpiresAt(Instant.now().plus(30, ChronoUnit.MINUTES))
                .sign(algorithmAccess);
    }

    public String generateRefreshToken(String username) {
        Algorithm algorithmRefresh = Algorithm.HMAC256(refresh);
        return JWT.create()
                .withIssuer("refresh")
                .withClaim("username", username)
                .sign(algorithmRefresh);
    }

    /**
     * Parses the access token and returns the user associated with it.
     *
     * @param token The access token to be parsed.
     * @return The user associated with the access token.
     * @throws JWTVerificationException if the verification of the access token fails.
     */
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

    /**
     * Parses the refresh token and returns the username associated with it.
     *
     * @param token The refresh token to be parsed.
     * @return The username associated with the refresh token.
     */
    public String parseRefreshToken(String token) {
        Algorithm algorithmRefresh = Algorithm.HMAC256(refresh);

        JWTVerifier verifier = JWT.require(algorithmRefresh)
                .withIssuer("refresh")
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);

        return decodedJWT.getClaim("username").as(String.class);
    }
}
