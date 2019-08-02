package com.pccw.backend.ctrl;

import java.util.ArrayList;
import java.util.Arrays;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.StockBalance.SearchCondition;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock/balance")
public class StockBalanceCtrl {
	@RequestMapping(method = RequestMethod.POST)
	public JsonResult<SearchCondition> search(@RequestBody SearchCondition sc) {

		return new JsonResult<SearchCondition>("", "3", new ArrayList<SearchCondition>(Arrays.asList(sc)));
	}
}
