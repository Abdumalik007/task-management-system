package com.taskmanagement.system.dto.custom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}
