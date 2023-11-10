package io.zethange.configuration.auth;

import io.zethange.entity.User;

public interface UserContext {
    User getCurrentUser();
    void setCurrentUser(User user);
}
