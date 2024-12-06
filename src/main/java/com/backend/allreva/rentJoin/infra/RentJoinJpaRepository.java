package com.backend.allreva.rentJoin.infra;

import com.backend.allreva.rentJoin.command.domain.RentJoin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentJoinJpaRepository extends JpaRepository<RentJoin, Long> {

}
