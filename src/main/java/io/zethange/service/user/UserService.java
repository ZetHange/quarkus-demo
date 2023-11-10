package io.zethange.service.user;

import io.zethange.entity.User;
import io.zethange.exception.BaseException;
import io.zethange.model.user.UserDto;
import io.zethange.model.user.UserMapper;
import io.zethange.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class UserService {
    @Inject
    UserMapper userMapper;

    @Inject
    UserRepository userRepository;

    public List<UserDto> getAll() {
        return userMapper.toDtoList(userRepository.listAll());
    }

    public UserDto getById(Long id) {
        User user = userRepository.findById(id);
        if(user == null) {
            throw new BaseException(Response.Status.NOT_FOUND.getStatusCode(), "Not found", "User with this id not found");
        }
        return userMapper.toDto(user);
    }

    public User getByUsername(String username) {
        return userRepository.find("username", username).firstResult();
    }

    public Long getCount() {
        return userRepository.count();
    }
}
