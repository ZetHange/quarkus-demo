package io.zethange.service.auth;

import io.zethange.entity.User;
import io.zethange.models.auth.LoginRequest;
import io.zethange.models.auth.LoginResponse;
import io.zethange.models.auth.RefreshRequest;
import io.zethange.models.auth.RegisterRequest;
import io.zethange.service.user.UserService;
import io.zethange.utils.auth.TokenUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;

import java.util.Objects;

@ApplicationScoped
public class AuthService {
    @Inject
    UserService userService;
    @Inject
    TokenUtil token;

    /**
     * Authenticates the user with the provided username and password, and returns a LoginResponse.
     *
     * @param req The login request containing the username and password.
     * @return The login response containing the access and refresh tokens.
     * @throws NotAuthorizedException If the user is not authorized.
     */
    public LoginResponse login(LoginRequest req) {
        User user = userService.getByUsername(req.getUsername());

        if (user == null || !Objects.equals(user.getPassword(), req.getPassword())) {
            throw new NotAuthorizedException("Not authorized");
        }

        return buildLoginResponse(user);
    }

    public LoginResponse register(RegisterRequest req) {
        return null;
    }

    /**
     * Refreshes the access token using the provided refresh token.
     *
     * @param req The refresh request containing the refresh token.
     * @return The login response containing the new access and refresh tokens.
     */
    public LoginResponse refresh(RefreshRequest req) {
        String username = token.parseRefreshToken(req.getRefreshToken());
        User user = userService.getByUsername(username);

        return buildLoginResponse(user);
    }

    /**
     * Builds a LoginResponse object using the provided User object.
     * The LoginResponse object contains the access and refresh tokens.
     *
     * @param user The User object for which the LoginResponse will be generated.
     * @return The LoginResponse object.
     */
    private LoginResponse buildLoginResponse(User user) {
        String accessToken = token.generateAccessToken(user);
        String refreshToken = token.generateRefreshToken(user.getUsername());
        return LoginResponse.builder()
                .type("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
