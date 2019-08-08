package com.pccw.backend.repository;

import com.pccw.backend.entity.DbResRight;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * ResRightRepository
 */

@Repository
public interface ResRightRepository extends JpaRepository<DbResRight,Long> {

    Page<DbResRight> findAll(Specification<DbResRight> spec,Pageable p);
}