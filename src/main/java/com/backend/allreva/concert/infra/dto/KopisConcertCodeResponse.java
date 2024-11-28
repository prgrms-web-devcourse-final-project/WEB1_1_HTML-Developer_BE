package com.backend.allreva.concert.infra.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "dbs")
@XmlAccessorType(XmlAccessType.FIELD)
public class KopisConcertCodeResponse {
    @XmlElement(name = "db")
    private List<Db> dbList;

    public List<Db> getDbList() {
        return dbList == null ? new ArrayList<>() : dbList;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Db {
        @XmlElement(name = "mt20id")
        private String id;

    }
}


