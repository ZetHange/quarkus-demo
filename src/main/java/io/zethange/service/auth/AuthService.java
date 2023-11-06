package io.zethange.service.auth;

import io.smallrye.jwt.build.Jwt;
import io.zethange.entity.User;
import io.zethange.models.auth.LoginRequest;
import io.zethange.models.auth.LoginResponse;
import io.zethange.models.auth.RegisterRequest;
import io.zethange.service.user.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

@ApplicationScoped
public class AuthService {
    @Inject
    UserService userService;

    @ConfigProperty(name = "security.refresh")
    String refresh;

    @ConfigProperty(name = "security.access")
    String access;

    public LoginResponse login(LoginRequest req) {
        User user = userService.getByUsername(req.getUsername());

        if (user == null || !Objects.equals(user.getPassword(), req.getPassword())) {
            throw new NotAuthorizedException("Not authorized");
        }

        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user.getUsername());
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public LoginResponse register(RegisterRequest req) {
        return null;
    }

    private String generateAccessToken(User user) {
        return Jwt
            .issuer("backend")
           .upn(user.getUsername())
           .groups(new HashSet<>(Arrays.asList(user.getRole())))
           .claim("id", user.id)
           .claim("username", user.getUsername())
           .claim("email", user.getEmail())
           .claim("role", user.getRole())
           .expiresIn(Duration.ofMinutes(30))
           .signWithSecret(access);
    }

    private String generateRefreshToken(String username) {
        return Jwt
                .issuer("backend")
                .upn(username)
                .expiresIn(Duration.ofDays(3))
                .signWithSecret(refresh);
    }
}
