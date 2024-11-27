package com.backend.allreva.concert.infra.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@XmlRootElement(name = "db")
public class Db {
    private String id;

    @XmlElement(name = "mt20id")
    public String getId() {
        return id;
    }
}