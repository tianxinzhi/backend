package com.pccw.backend.repository;

import com.pccw.backend.entity.DbResAccount;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ResAccountRepository
 */
public interface ResAccountRepository extends JpaRepository<DbResAccount,Long>{

    
}