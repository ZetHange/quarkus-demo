package io.zethange.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.zethange.entity.user.User;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
}
