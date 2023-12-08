package com.taskmanagement.system.controller;
import com.taskmanagement.system.dto.custom.ErrorResponse;
import com.taskmanagement.system.dto.custom.ValidatorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidatorDTO>> handle(MethodArgumentNotValidException e) {
        List<ValidatorDTO> errors = e.getBindingResult().getFieldErrors().stream()
                .map(ee -> ValidatorDTO.builder()
                        .fieldName(ee.getField())
                        .error(ee.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handle(HttpMessageNotReadableException e) {
        ErrorResponse errorResponse =
                new ErrorResponse(e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentTypeMismatchException e) {
        ErrorResponse errorResponse =
                new ErrorResponse(e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
