package com.backend.allreva.member.command.application.request;

import lombok.Builder;

@Builder
public record RefundAccountRequest(
        String bank,
        String number
) {

}
