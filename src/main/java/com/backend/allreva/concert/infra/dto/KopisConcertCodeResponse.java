package com.backend.allreva.concert.infra.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Setter
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

    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Db {
        private String id;

        @XmlElement(name = "mt20id")
        public String getId() {
            return id;
        }

    }
}


