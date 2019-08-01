package com.pccw.backend.repository;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccw.backend.entity.DbResItem;

@Profile("dev1")
public interface ResItemRepository extends JpaRepository<DbResItem, Long> {

	DbResItem findBySkuIdAndKeyItemValueAndStatusAndLotNum(Long skuId, String itemValue, String itemStatusAvailable, String lotNum);

//	@Query(value = "SELECT * FROM res_item u where u.id = :id AND u.status IN :statuses", nativeQuery = true)
//	DbResItem findItemByIdAndStatus(@Param("id") Long id, @Param("statuses") String[] statuses);

	DbResItem findBySkuIdAndKeyItemValueAndLotNum(Long skuId, String keyItemValue, String lotNum);

	List<DbResItem> findBySkuIdAndStatus(Long skuId, String status);

	DbResItem findByIdAndStatusIn(Long itemId, String[] statuses);
}

