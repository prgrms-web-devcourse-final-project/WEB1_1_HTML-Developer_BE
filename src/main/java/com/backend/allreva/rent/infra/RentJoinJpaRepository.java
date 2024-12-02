package com.backend.allreva.rent.infra;

import com.backend.allreva.rent.command.domain.RentJoin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentJoinJpaRepository extends JpaRepository<RentJoin, Long> {

}
