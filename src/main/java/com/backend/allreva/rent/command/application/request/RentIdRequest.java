package com.backend.allreva.rent.command.application.request;

import jakarta.validation.constraints.NotNull;

public record RentIdRequest(
        @NotNull
        Long rentId
) {

}
