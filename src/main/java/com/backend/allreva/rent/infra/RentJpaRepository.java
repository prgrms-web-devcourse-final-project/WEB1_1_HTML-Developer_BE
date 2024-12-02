package com.backend.allreva.rent.infra;

import com.backend.allreva.rent.command.domain.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentJpaRepository extends JpaRepository<Rent, Long> {

}
