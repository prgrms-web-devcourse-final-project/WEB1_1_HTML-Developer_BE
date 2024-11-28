package com.backend.allreva.common.exception;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.common.exception.code.GlobalErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        log.error("error: {}", e.getMessage(), e);
        return ResponseEntity.status(e.getErrorCode().status())
                .body(
                        Response.onFailure(
                                e.getErrorCode().code(),
                                e.getErrorCode().message()
                        )
                );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("error: {}", e.getMessage(), e);
        StringBuilder errorMessage = new StringBuilder();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorMessage.append(fieldError.getField())
                    .append(": ")
                    .append(fieldError.getDefaultMessage())
                    .append("\n");
        }

        return ResponseEntity.status(GlobalErrorCode.BAD_REQUEST_ERROR.getErrorCode().status())
                .body(
                        Response.onFailure(
                                GlobalErrorCode.BAD_REQUEST_ERROR.getErrorCode().code(),
                                errorMessage.toString()
                        )
                );
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error("error: {}", e.getMessage(), e);
        return ResponseEntity.status(GlobalErrorCode.SERVER_ERROR.getErrorCode().status())
                .body(
                        Response.onFailure(
                                GlobalErrorCode.SERVER_ERROR.getErrorCode().code(),
                                GlobalErrorCode.SERVER_ERROR.getErrorCode().message()
                               )
                );
    }
}
