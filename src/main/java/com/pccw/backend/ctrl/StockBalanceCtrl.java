package com.pccw.backend.ctrl;

import java.util.List;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.StockBalance.SearchCondition;
// import com.pccw.backend.repository.ResItemRepoRepository;
import com.pccw.backend.repository.ResRepoRepository;
import com.pccw.backend.repository.ResSkuRepoRepository;
import com.pccw.backend.repository.ResSkuRepository;
import com.pccw.backend.util.Convertor;
// import com.pccw.backend.entity.DbResItemRepo;
import com.pccw.backend.entity.DbResRepo;
import com.pccw.backend.entity.DbResSku;
import com.pccw.backend.entity.DbResSkuRepo;

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
	private ResSkuRepoRepository repo;


	/**
	 * 
	 * @param sc
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(method=RequestMethod.POST,path="/search")
	public JsonResult<DbResSkuRepo> test(@RequestBody SearchCondition sc)
	 {
	    try {
			Specification<DbResSkuRepo> spec = Convertor.<DbResSkuRepo,SearchCondition>convertSpecification(SearchCondition.class,sc);
			List<DbResSkuRepo> res =repo.findAll(spec,PageRequest.of(sc.getPageIndex(),sc.getPageSize())).getContent();

			// List<DbResRepo> r = repoTest.findAll();
			// log.info(r.toString());

			return JsonResult.succss(res);
		} catch (Exception e) {
			// log.error(e, t);
			return JsonResult.fail();
		}
	}
}
