package io.zethange.configuration.auth;

import io.zethange.entity.user.User;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class UserContextImpl implements UserContext {
    private User user;


    @Override
    public User getCurrentUser() {
        return user;
    }

    @Override
    public void setCurrentUser(User user) {
        this.user = user;
    }
}