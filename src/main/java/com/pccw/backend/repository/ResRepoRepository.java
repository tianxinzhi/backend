package com.pccw.backend.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.pccw.backend.entity.DbResRepo;


public interface ResRepoRepository extends JpaRepository<DbResRepo, Long>,JpaSpecificationExecutor {

}
