package com.backend.allreva.concert.infra.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "dbs")
public class ConcertCodeResponse {
    private List<Db> dbList = new ArrayList<>();  // 빈 리스트로 초기화

    @XmlElement(name = "db")
    public List<Db> getDbList() {
        return dbList != null ? dbList : new ArrayList<>();
    }
}

