package com.pccw.backend.repository;

import java.util.ArrayList;

import javax.transaction.Transactional;

import com.pccw.backend.entity.Base;
import com.pccw.backend.entity.DbResRight;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;



@Repository
@NoRepositoryBean
    public interface BaseRepository<T> extends JpaRepository<T,Integer>,JpaSpecificationExecutor<T> {

    // Page<DbResRight> findAll(Specification<DbResRight> spec,Pageable p);

    @Modifying
    @Transactional
    long deleteByIdIn(ArrayList<Long> ids);
}