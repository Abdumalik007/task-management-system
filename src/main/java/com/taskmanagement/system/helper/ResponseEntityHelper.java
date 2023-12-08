package com.taskmanagement.system.helper;

import com.taskmanagement.system.dto.custom.ErrorResponse;
import org.springframework.http.ResponseEntity;

public class ResponseEntityHelper {

    public static ResponseEntity<ErrorResponse> ERROR_RESPONSE(ErrorResponse response, Integer statusCode){
        return ResponseEntity.status(statusCode).body(response);
    }

    public static <T> ResponseEntity<T> SUCCESS_RESPONSE(T data){
        return ResponseEntity.ok(data);
    }

}
