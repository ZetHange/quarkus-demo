package io.zethange.model.auth;

import lombok.Data;

@Data
public class RefreshRequest {
    private String refreshToken;
}
