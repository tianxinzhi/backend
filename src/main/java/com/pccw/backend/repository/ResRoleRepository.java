package com.pccw.backend.repository;

import com.pccw.backend.entity.DbResRole;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ResRoleRepository
 */
public interface ResRoleRepository extends JpaRepository<DbResRole,Long>{

    Page<DbResRole> findAll(Specification<DbResRole> spec,Pageable p);  
}