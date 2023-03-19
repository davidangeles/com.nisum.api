package com.nisum.api.mapper;

import com.nisum.api.dto.UserStatus;
import com.nisum.api.dto.request.UserRequest;
import com.nisum.api.dto.response.PhoneResponse;
import com.nisum.api.dto.response.UserResponse;
import com.nisum.api.entity.Phone;
import com.nisum.api.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements UserMapper {

    public User buildUserEntity(UserRequest request, String uuid, String token) {
        User user = User
                .builder()
                .id(uuid)
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .isActive(UserStatus.ACTIVE)
                .token(token)
                .build();

        List<Phone> phoneList = request.getPhones().stream().map(phoneRequest ->
                Phone
                        .builder()
                        .user(user)
                        .number(phoneRequest.getNumber())
                        .cityCode(phoneRequest.getCitycode())
                        .countryCode(phoneRequest.getContrycode())
                        .build()
        ).collect(Collectors.toList());

        user.setPhoneList(phoneList);
        return user;
    }

    public UserResponse buildUserResponse(User user) {
        List<PhoneResponse> lstPhoneResponse = user.getPhoneList().stream().map(p ->
                PhoneResponse
                        .builder()
                        .id(p.getId())
                        .number(p.getNumber())
                        .citycode(p.getCityCode())
                        .contrycode(p.getCountryCode())
                        .created(p.getCreatedDate())
                        .modified(p.getModifiedDate())
                        .build()
        ).collect(Collectors.toList());

        return UserResponse
                .builder()
                .id(user.getId())
                .name(user.getName())
                .created(user.getCreatedDate())
                .modified(user.getModifiedDate())
                .lastLogin(user.getLastLogin())
                .token(user.getToken())
                .isActive(user.getIsActive())
                .phones(lstPhoneResponse)
                .build();
    }

}
