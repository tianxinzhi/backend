package com.pccw.backend.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccw.backend.entity.DbResRoleRepo;

@Profile("dev1")
public interface ResRoleRepoRepository extends JpaRepository<DbResRoleRepo, Long> {

}
