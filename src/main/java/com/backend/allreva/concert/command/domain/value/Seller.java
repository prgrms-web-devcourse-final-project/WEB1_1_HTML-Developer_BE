package com.backend.allreva.concert.command.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"name", "salesUrl"})
@Embeddable
public class Seller {

    @Column(nullable = false, name = "relate_name")
    private String name;

    @Column(nullable = false, name = "relate_url")
    private String salesUrl;

    @Builder
    private Seller(final String name, final String salesUrl) {
        this.name = name;
        this.salesUrl = salesUrl;
    }
}
