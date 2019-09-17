package com.pccw.backend.ctrl;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import lombok.extern.slf4j.Slf4j;



@Slf4j
@RestController
@RequestMapping("/stock_balance")
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
public class Stock_BalanceCtrl {

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
