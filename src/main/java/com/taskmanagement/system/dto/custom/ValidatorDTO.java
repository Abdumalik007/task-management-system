package com.taskmanagement.system.dto.custom;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidatorDTO {
    private String fieldName;
    private String error;
}
