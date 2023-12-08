package com.taskmanagement.system.helper;

import com.taskmanagement.system.dto.custom.ErrorResponse;

public class ResponseMessageHelper {
    public static ErrorResponse incorrect_id(String entityName, Integer id) {
        return new ErrorResponse(entityName + " with id=" + id + " is not found!");
    }

    public static ErrorResponse error_while_operation(String entityName, String operation) {
        return new ErrorResponse("An error occurred while " + operation + " " + entityName);
    }

    public static ErrorResponse common_error_message(String message) {
        return new ErrorResponse(message);
    }

}
