package com.taskmanagement.system.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(value = {"password", "id"}, allowSetters = true)
public class UserDto implements Serializable {
    private Integer id;
    @NotBlank(message = "Email must not be empty")
    private String email;

    @Size(min = 6, message = "Password must contain at least 6 characters")
    @NotBlank(message = "Password must not be empty")
    private String password;
}

