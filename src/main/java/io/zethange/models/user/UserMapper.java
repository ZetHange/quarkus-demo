package io.zethange.models.user;

import io.zethange.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "jakarta")
public interface UserMapper {
    UserDto toDto(User user);
    User toModel(UserDto userDto);
    List<UserDto> toDtoList(List<User> userList);
    List<User> toModelList(List<UserDto> userDtoList);
}
