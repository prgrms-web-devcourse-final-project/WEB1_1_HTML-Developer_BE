package com.backend.allreva.concert.command.application.dto;


import com.backend.allreva.concert.command.domain.Concert;
import com.backend.allreva.concert.command.domain.value.Code;
import com.backend.allreva.concert.command.domain.value.ConcertInfo;
import com.backend.allreva.concert.command.domain.value.Seller;

import java.util.List;

public class AddConcertRequest {

    private String facilityCode;
    private String hallCode;
    private String concertCode;

    private String title;
    private String price;

    private List<Seller> sellers;

    public Concert to() {
        return Concert.builder()
                .code(Code.builder()
                        .facilityCode(this.facilityCode)
                        .hallCode(this.hallCode)
                        .concertCode(this.concertCode)
                        .build()
                )
                .concertInfo(ConcertInfo.builder()
                        .title(this.title)
                        .price(this.price)
                        .build()
                )
                .sellers(this.sellers)
                .build();
    }
}
