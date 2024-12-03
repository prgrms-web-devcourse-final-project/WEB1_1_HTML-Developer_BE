package com.backend.allreva.rent.command.domain;

import java.util.Optional;

public interface RentJoinRepository {

    Optional<RentJoin> findById(Long id);

    boolean existsById(Long id);

    RentJoin save(RentJoin rentJoin);

    void delete(RentJoin rentJoin);
}
