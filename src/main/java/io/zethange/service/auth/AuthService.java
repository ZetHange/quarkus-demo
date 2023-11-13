package io.zethange.service.auth;

import com.auth0.jwt.exceptions.JWTVerificationException;
import io.zethange.configuration.auth.UserContext;
import io.zethange.entity.user.User;
import io.zethange.exception.BaseException;
import io.zethange.model.auth.LoginRequest;
import io.zethange.model.auth.LoginResponse;
import io.zethange.model.auth.RefreshRequest;
import io.zethange.model.auth.RegisterRequest;
import io.zethange.model.user.UserDto;
import io.zethange.model.user.UserMapper;
import io.zethange.service.user.UserService;
import io.zethange.utils.auth.TokenUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Objects;

@ApplicationScoped
public class AuthService {
    @Inject
    UserService userService;
    @Inject
    TokenUtil token;
    @Inject
    UserContext userContext;
    @Inject
    UserMapper userMapper;

    public LoginResponse login(LoginRequest req) {
        User user = userService.getByUsername(req.getUsername());

        if (user == null || !Objects.equals(user.getPassword(), req.getPassword())) {
            throw new BaseException(401, "Authorization error", "The username or password is incorrect");
        }

        return buildLoginResponse(userMapper.toDto(user));
    }

    public LoginResponse register(RegisterRequest req) {
        return LoginResponse.builder().build();
    }

    public LoginResponse refresh(RefreshRequest req) {
        try {
            Long id = token.parseRefreshToken(req.getRefreshToken());
            UserDto user = userService.getById(id);

            return buildLoginResponse(user);
        } catch (JWTVerificationException e) {
            throw new BaseException(400, "Validation error", "Failed to validate a refresh token");
        }
    }

    private LoginResponse buildLoginResponse(UserDto user) {
        String accessToken = token.generateAccessToken(user);
        String refreshToken = token.generateRefreshToken(user.getId());
        return LoginResponse.builder()
                .type("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public UserDto getMe() {
        return userMapper.toDto(userContext.getCurrentUser());
    }
}
