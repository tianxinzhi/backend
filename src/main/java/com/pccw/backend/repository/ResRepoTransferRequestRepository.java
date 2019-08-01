package com.pccw.backend.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccw.backend.entity.DbResRepoTransferRequest;

@Profile("dev1")
public interface ResRepoTransferRequestRepository extends JpaRepository<DbResRepoTransferRequest, Long> {

}
