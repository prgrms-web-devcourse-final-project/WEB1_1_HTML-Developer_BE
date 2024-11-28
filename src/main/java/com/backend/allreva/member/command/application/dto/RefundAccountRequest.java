package com.backend.allreva.member.command.application.dto;

public record RefundAccountRequest(
        String bank,
        String number
) {

}
