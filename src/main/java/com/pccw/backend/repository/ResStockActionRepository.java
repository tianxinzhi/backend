package com.pccw.backend.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccw.backend.entity.DbResStockAction;

@Profile("dev1")
public interface ResStockActionRepository extends JpaRepository<DbResStockAction, Long> {

}
