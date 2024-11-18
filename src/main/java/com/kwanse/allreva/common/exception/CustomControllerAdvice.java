package com.kwanse.allreva.common.exception;

import com.kwanse.allreva.common.dto.Response;
import com.kwanse.allreva.common.exception.code.GlobalErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getErrorCode().status())
                .body(
                        Response.onFailure(
                                e.getErrorCode().code(),
                                e.getErrorCode().message()
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
