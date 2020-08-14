package com.pccw.backend.repository;

import com.pccw.backend.entity.DbResAttrValue;
import com.pccw.backend.entity.DbResReservation;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResReservationRepository extends BaseRepository<DbResReservation> {

    List<DbResReservation> findDbResReservationsByActiveEquals(String active);
}

