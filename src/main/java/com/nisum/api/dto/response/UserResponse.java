package com.nisum.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nisum.api.dto.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

  private String id;

  private String name;

  private String password;

  private LocalDateTime created;

  private LocalDateTime modified;

  private LocalDateTime lastLogin;

  private String token;

  private UserStatus isActive;

  private List<PhoneResponse> phones;

}
