package io.zethange.model.user;

import io.zethange.entity.user.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "jakarta")
public interface UserMapper {
    UserDto toDto(User user);
    User toModel(UserDto userDto);
    List<UserDto> toDtoList(List<User> userList);
    List<User> toModelList(List<UserDto> userDtoList);
}
