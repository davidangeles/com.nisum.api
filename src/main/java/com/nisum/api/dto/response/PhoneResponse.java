package com.nisum.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhoneResponse {

    private Long id;

    private String number;

    private String citycode;

    private String contrycode;

    private LocalDateTime created;

    private LocalDateTime modified;

}
