package com.pccw.backend.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccw.backend.entity.DbresClassTypeRelation;

@Profile("dev1")
public interface ResClassTypeRelationRepository extends JpaRepository<DbresClassTypeRelation, Long> {

	DbresClassTypeRelation findByClassId(Long classId);
}
