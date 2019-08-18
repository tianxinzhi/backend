package com.pccw.backend.ctrl;

import java.util.List;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.stockbalance.*;
// import com.pccw.backend.repository.ResItemRepoRepository;
import com.pccw.backend.repository.ResSkuRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.jpa.domain.Specification;


import lombok.extern.slf4j.Slf4j;



@Slf4j
@RestController
@RequestMapping("/stock/balance")
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
public class StockBalanceCtrl {

	// @Autowired
	// private ResSkuRepoRepository repo;


	// /**
	//  * 
	//  * @param sc
	//  * @return
	//  * @throws IllegalArgumentException
	//  * @throws IllegalAccessException
	//  */
	// @RequestMapping(method=RequestMethod.POST,path="/search")
	// public JsonResult<DbResSkuRepo> test(@RequestBody SearchCondition sc)
	//  {
	//     try {
	// 		Specification<DbResSkuRepo> spec = Convertor.<DbResSkuRepo,SearchCondition>convertSpecification(SearchCondition.class,sc);
	// 		List<DbResSkuRepo> res =repo.findAll(spec,PageRequest.of(sc.getPageIndex(),sc.getPageSize())).getContent();

	// 		// List<DbResRepo> r = repoTest.findAll();
	// 		// log.info(r.toString());

	// 		return JsonResult.success(res);
	// 	} catch (Exception e) {
	// 		// log.error(e, t);
	// 		return JsonResult.fail();
	// 	}
	// }
}
