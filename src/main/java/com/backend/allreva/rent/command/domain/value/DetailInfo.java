package com.backend.allreva.rent.command.domain.value;

import com.backend.allreva.common.model.Image;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class DetailInfo {

    @Column(nullable = false)
    private String title;

    @Embedded
    @AttributeOverride(name = "url", column = @Column(name = "image", nullable = false))
    private Image image;

    @Column(nullable = false)
    private String artistName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Region region;

    @Column(nullable = false)
    private String depositAccount;
}
