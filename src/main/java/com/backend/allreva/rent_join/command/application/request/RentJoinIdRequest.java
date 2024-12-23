package com.backend.allreva.rent_join.command.application.request;

import jakarta.validation.constraints.NotNull;

public record RentJoinIdRequest(
        @NotNull
        Long rentJoinId
) {

}
