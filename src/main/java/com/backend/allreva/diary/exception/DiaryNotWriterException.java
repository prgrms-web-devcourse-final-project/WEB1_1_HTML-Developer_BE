package com.backend.allreva.diary.exception;

import com.backend.allreva.common.exception.CustomException;

public class DiaryNotWriterException extends CustomException {

    public DiaryNotWriterException() {
      super(DiaryErrorCode.DIARY_NOT_WRITER);
    }
}
