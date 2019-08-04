package com.pccw.backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


import com.pccw.backend.entity.DbResItemRepo;



public interface  ResItemRepoRepository extends JpaRepository<DbResItemRepo, Long> {
	
	// @Query(
	// 	value = "select * from res_item_repo a where a.sku_num=?1 and a.repo_num=?2",
	// 	countQuery = "select count(*) from res_item_repo a where a.sku_num=?1 and a.repo_num=?2",
	//     nativeQuery = true)
	// List<DbResItemRepo> getSku(String skuNum,String repoNum,PageRequest p);

	// @Query(
	// value = "select * from res_item_repo",
	// nativeQuery = true)
	// List<DbResItemRepo> getSku(PageRequest p);

	Page<DbResItemRepo> findAll(Specification<DbResItemRepo> spec,Pageable p);

}