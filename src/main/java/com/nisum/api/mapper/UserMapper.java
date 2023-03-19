package com.nisum.api.mapper;

import com.nisum.api.dto.request.UserRequest;
import com.nisum.api.dto.response.UserResponse;
import com.nisum.api.entity.User;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@DecoratedWith(UserMapperImpl.class)
@Mapper(componentModel = "spring")
public interface UserMapper {

    User buildUserEntity(UserRequest request, String uuid, String token);

    UserResponse buildUserResponse(User user);


}
