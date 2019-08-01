package com.pccw.backend.ctrl;

import java.util.ArrayList;
import java.util.Arrays;

import com.pccw.backend.bean.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock/balance")
public class StockBalanceCtrl {

	
	@RequestMapping("/search")
	public JsonResult<String> search(){
		return new JsonResult<String>("", "3", new ArrayList<String>(Arrays.asList("ok!","333")));
	}
}
