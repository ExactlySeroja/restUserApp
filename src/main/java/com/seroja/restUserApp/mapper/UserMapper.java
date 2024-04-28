package com.seroja.restUserApp.mapper;

import com.seroja.restUserApp.dto.UserDto;
import com.seroja.restUserApp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Component
public interface UserMapper {

    UserDto toDto(User user);

    User toUser(UserDto userDto);

    List<UserDto> toDTOList(List<User> userList);

    List<User> toList(List<UserDto> userDto);

    @Mapping(source = "id", target = "id", ignore = true)
    void update(@MappingTarget User entity, User updateEntity);
}
