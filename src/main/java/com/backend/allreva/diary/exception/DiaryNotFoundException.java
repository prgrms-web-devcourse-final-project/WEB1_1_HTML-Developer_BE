package com.backend.allreva.diary.exception;

import com.backend.allreva.common.exception.CustomException;

public class DiaryNotFoundException extends CustomException {

    public DiaryNotFoundException() {
        super(DiaryErrorCode.DIARY_NOT_FOUND);
    }
}
