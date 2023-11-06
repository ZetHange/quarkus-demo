package io.zethange.service.user;

import io.zethange.entity.User;
import io.zethange.models.user.UserDto;
import io.zethange.models.user.UserMapper;
import io.zethange.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.mapstruct.Mapper;

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
            throw new NotFoundException("User not found");
        }
        return userMapper.toDto(user);
    }

    public UserDto getByUsername(String username) {
        User user = userRepository.find("username", username).firstResult();
        if(user == null) {
            throw new NotFoundException("User not found");
        }

        return userMapper.toDto(user);
    }

    public Long getCount() {
        return userRepository.count();
    }
}
