package com.pccw.backend.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccw.backend.entity.DbViewAdrAddress;

@Profile("dev1")
public interface ViewAdrAddressRepository extends JpaRepository<DbViewAdrAddress, Long>, JpaSpecificationExecutor<DbViewAdrAddress> {

}
