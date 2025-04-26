package com.store.api.management.user.model.mapper;

import com.store.api.management.user.model.UserDTO;
import com.store.api.management.user.model.domain.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);

    List<UserDTO> userListToUserDTOList(List<User> users);
    List<User> userDTOListToUserList(List<UserDTO> userDTOs);
}
