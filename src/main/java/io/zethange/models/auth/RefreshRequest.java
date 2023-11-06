package io.zethange.models.auth;

import lombok.Data;

@Data
public class RefreshRequest {
    private String refreshToken;
}
