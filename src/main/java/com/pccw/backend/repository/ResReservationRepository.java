package com.pccw.backend.repository;

import com.pccw.backend.entity.DbResAttrValue;
import com.pccw.backend.entity.DbResReservation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResReservationRepository extends BaseRepository<DbResReservation> {
}

