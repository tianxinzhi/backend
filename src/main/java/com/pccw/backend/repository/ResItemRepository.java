package com.pccw.backend.repository;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccw.backend.entity.DbResItem;


public interface ResItemRepository extends JpaRepository<DbResItem, Long> {

}

