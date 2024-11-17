package com.kwanse.allreva.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.kwanse.allreva.common.dto.ResponseConst.SUCCESS;
import static com.kwanse.allreva.common.dto.ResponseConst.SUCCESS_MESSAGE;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"timeStamp", "code", "message", "result"})
public class Response<T> {
    private final LocalDateTime timeStamp = LocalDateTime.now();
    private String code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public Response(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> Response<T> onSuccess(T result) {
        return new Response<>(SUCCESS, SUCCESS_MESSAGE, result);
    }

    public static <T> Response<T> onSuccess() {
        return new Response<>(SUCCESS, SUCCESS_MESSAGE);
    }

    public static <T> Response<T> onFailure(String errorCode, String message) {
        return new Response<>(errorCode, message);
    }
}
