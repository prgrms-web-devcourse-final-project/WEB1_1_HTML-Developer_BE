package com.backend.allreva.artist.exception;

import com.backend.allreva.common.exception.code.ErrorCode;
import com.backend.allreva.common.exception.code.ErrorCodeInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ArtistErrorCode implements ErrorCodeInterface {
    ARTIST_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "ARTIST_NOT_FOUND", "존재하지 않는 아티스트 입니다."),
    ARTIST_SEARCH_NO_CONTENT(HttpStatus.NO_CONTENT.value(), "ARTIST_SEARCH_NO_CONTENT", "아티스트 검색 결과가 없습니다.");
    private final Integer status;
    private final String errorCode;
    private final String message;

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.of(status, errorCode, message);
    }
}
