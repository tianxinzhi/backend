package com.pccw.backend.ctrl;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.StockBalance.SearchCondition;
import com.pccw.backend.repository.ResItemRepoRepository;
import com.pccw.backend.util.Convertor;
import com.pccw.backend.entity.DbResItemRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;



import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.jpa.domain.Specification;


import lombok.extern.slf4j.Slf4j;



@Slf4j
@RestController
@RequestMapping("/stock/balance")

public class StockBalanceCtrl {

	@Autowired
	private ResItemRepoRepository repo;


	@RequestMapping(method=RequestMethod.POST)
	public JsonResult<DbResItemRepo> test(@RequestBody SearchCondition sc)
			throws IllegalArgumentException, IllegalAccessException {
				log.info(sc.toString());
		Specification<DbResItemRepo> spec = Convertor.<DbResItemRepo,SearchCondition>convertSpecification(SearchCondition.class,sc);

		return new JsonResult<DbResItemRepo>("", "", repo.findAll(spec,PageRequest.of(sc.getPageIndex(),sc.getPageSize())).getContent());
	}
}
