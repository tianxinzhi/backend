package com.pccw.backend.repository;



import java.util.List;

import com.pccw.backend.entity.DbResRight;

import org.springframework.stereotype.Repository;


/**
 * ResRightRepository
 */

@Repository
public interface ResRightRepository extends BaseRepository<DbResRight> {

    List<DbResRight> findByRightPid(Long pid);
}