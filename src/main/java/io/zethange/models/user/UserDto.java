package io.zethange.models.user;

import lombok.Data;

import java.sql.Date;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String role;
    private Boolean emailApproved;
    public Date registeredAt;
}
