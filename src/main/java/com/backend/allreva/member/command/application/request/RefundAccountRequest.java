package com.backend.allreva.member.command.application.request;

public record RefundAccountRequest(
        String bank,
        String number
) {

}
