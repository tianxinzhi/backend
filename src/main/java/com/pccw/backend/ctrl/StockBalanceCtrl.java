package com.pccw.backend.ctrl;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;




import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.StockBalance.SearchCondition;
import com.pccw.backend.repository.ResItemRepoRepository;
import com.pccw.backend.util.Convertor;
import com.pccw.backend.entity.DbResItemRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;



import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/stock/balance")

public class StockBalanceCtrl {

	@Autowired
	private ResItemRepoRepository repo;


	@RequestMapping(method = RequestMethod.POST)
	public JsonResult<SearchCondition> search(@RequestBody SearchCondition sc) {

		return new JsonResult<SearchCondition>("", "3", new ArrayList<SearchCondition>(Arrays.asList(sc)));
	}

	@RequestMapping("/test")
	public JsonResult<String> test(){
		PageRequest p = PageRequest.of(0,3);
		// List<DbResItemRepo> res = repo.getSku("*","*",p);
		// ArrayList<DbResItemRepo> list = new ArrayList<DbResItemRepo>();
		// SearchCondition sc = new SearchCondition();
		// DbResItemRepo r =  res.stream().findFirst().get();
		// sc.itemNum = r.itemNum;
		// // return new JsonResult<DbResItemRepo>("", "", list);
		
		// sc.skuNum = "12121";
		// return sc;
		
		// List<DbResItemRepo> res=jpa_repo.findAll(example)
	    // Specification<DbResItemRepo> spec =	
		// Page<DbResItemRepo> res = repo.findAll(Convertor.<DbResItemRepo>convertSpecification(),PageRequest.of(0,1));
		  
		// return new JsonResult<DbResItemRepo>("", "", res.stream().collect(Collectors.toList()));
		return new JsonResult<String>("", "", Arrays.asList(Convertor.test()));
	}
}
