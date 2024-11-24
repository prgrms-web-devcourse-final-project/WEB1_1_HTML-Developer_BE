package com.backend.allreva.member.command.application.dto;

public record RefundAccountRegisterRequest(
        String bank,
        String number
) {

}
