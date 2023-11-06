package io.zethange.service.auth;

import io.smallrye.jwt.build.Jwt;
import io.zethange.entity.User;
import io.zethange.models.auth.LoginRequest;
import io.zethange.models.auth.LoginResponse;
import io.zethange.models.auth.RegisterRequest;
import io.zethange.service.user.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@ApplicationScoped
public class AuthService {
//    @Inject
//    JsonWebToken jwt;

    @Inject
    UserService userService;

    @ConfigProperty(name = "security.refresh")
    String refreshKey;

    @ConfigProperty(name = "security.access")
    String accessKey;

    public LoginResponse login(LoginRequest req) {
        return null;
    }

    public LoginResponse register(RegisterRequest req) {
        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());
        user.setRole("ADMIN");
        String token = generateAccessToken(user);
        return LoginResponse.builder()
                .type("Bearer")
                .accessToken("token")
                .refreshToken("token")
                .build();
    }

    private String generateAccessToken(User user) {
        System.out.println(user.toString());
        try {
            return Jwt
                    .upn(user.getUsername())
                    .groups(new HashSet<>(Arrays.asList(user.getRole())))
//                    .claim("id", user.id)
                    .claim("username", user.getUsername())
                    .claim("email", user.getEmail())
                    .claim("role", user.getRole())
                    .signWithSecret(accessKey);
        } catch (NullPointerException e) {
            System.out.println(e);
        }

        return "";
    }

    private String generateRefreshToken(String username) {
    return "";
    }
}
