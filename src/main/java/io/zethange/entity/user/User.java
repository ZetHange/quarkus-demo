package io.zethange.entity.user;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Entity(name = "person")
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends PanacheEntity {
    private String username;
    private String email;
    private String password;
    private String role;

    @Column(columnDefinition = "boolean default false")
    private Boolean emailApproved;

    @CreationTimestamp
    public Date registeredAt;
}



