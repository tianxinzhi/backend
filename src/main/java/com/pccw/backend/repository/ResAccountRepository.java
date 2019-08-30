package com.pccw.backend.repository;

import com.pccw.backend.entity.DbResAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * ResAccountRepository
 */
public interface ResAccountRepository extends JpaRepository<DbResAccount,Long>,JpaSpecificationExecutor{

    
}