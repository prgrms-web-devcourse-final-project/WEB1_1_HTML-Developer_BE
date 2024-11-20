package com.backend.allreva.member.exception;

import com.backend.allreva.common.exception.CustomException;

public class MemberNotFoundException extends CustomException {
    public MemberNotFoundException() {
        super(MemberErrorCode.MEMBER_NOT_FOUND);
    }
}
