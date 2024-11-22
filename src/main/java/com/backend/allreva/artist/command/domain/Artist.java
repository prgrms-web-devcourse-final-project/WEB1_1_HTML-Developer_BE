package com.backend.allreva.artist.command.domain;

import com.backend.allreva.common.application.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is NULL")
@SQLDelete(sql = "UPDATE artist SET deleted_at = NOW() WHERE id = ?")

@Entity
public class Artist extends BaseEntity {

    @Id
    private String id;

    private String name;

}
