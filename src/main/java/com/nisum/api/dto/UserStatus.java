package com.nisum.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UserStatus {

    INACTIVE(0), ACTIVE (1);

    private Integer code;

    public static UserStatus getByCode(Integer code) {
        return Arrays.stream(values())
            .filter(value -> value.getCode().compareTo(code) == 0)
            .findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
