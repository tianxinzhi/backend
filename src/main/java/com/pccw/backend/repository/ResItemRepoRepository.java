package com.pccw.backend.repository;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.pccw.backend.entity.DbResItemRepo;

@Profile("dev1")
public interface ResItemRepoRepository extends JpaRepository<DbResItemRepo, Long> {
	
	@Query(value = "select qty from res_item_repo left join res_item on res_item_repo.item_id = res_item.id where res_item_repo.sku_id = :typeId and res_item.status = :status", nativeQuery = true)
	List<Integer> getQuantity(@Param("typeId") Long typeId, @Param("status") String status);
	
	List<DbResItemRepo> findBySkuId(Long skuId);	
	
//	@Query(value = "SELECT SUM(qty) total FROM res_item_repo where sku_id = :typeId", nativeQuery = true)
//	int getAllQuantity(@Param("typeId") Long typeId);

	DbResItemRepo findBySkuIdAndStatusAndRepoId(Long skuId, String status, Long repoId);

	DbResItemRepo findBySkuIdAndItemIdAndRepoId(Long skuId, Long itemId, Long repoId);

//	@Query(value = "SELECT SUM(qty) total FROM res_item_repo where sku_id = :skuId and res_item_repo.status = :status", nativeQuery = true)
//	Integer getAllQuantityByStatus(@Param("skuId") Long skuId, @Param("status") String status);

	DbResItemRepo findByItemId(Long itemId);

	DbResItemRepo findByItemIdAndRepoId(Long itemId, Long repoId);

	List<DbResItemRepo> findBySkuIdAndStatus(Long skuId, String status);

	List<DbResItemRepo> findByItemIdAndSkuIdAndRepoId(Long itemId, Long skuId, Long repoId);

}
