package com.pccw.backend.repository;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccw.backend.entity.DbResSku;


public interface ResSkuRepository extends JpaRepository<DbResSku, Long> {

	Page<DbResSku> findAll(Specification<DbResSku> spec,Pageable p);

}
