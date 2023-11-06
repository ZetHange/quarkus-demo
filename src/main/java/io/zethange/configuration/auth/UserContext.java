package io.zethange.configuration.auth;

import io.zethange.entity.User;
import jakarta.ws.rs.core.SecurityContext;
import lombok.Data;
import lombok.Getter;

import java.security.Principal;

@Data
public class UserContext implements SecurityContext {
    private User user;

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public boolean isUserInRole(String s) {
        return false;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return null;
    }
}