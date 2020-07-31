package com.pccw.backend.ctrl;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.stock_take.SearchBean;
import com.pccw.backend.entity.DbResStockTake;
import com.pccw.backend.repository.ResStockTakeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: ChenShuCheng
 * @create: 2020-07-30 10:54
 **/
@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/stock_take")
public class Stock_TakeCtrl extends BaseCtrl<DbResStockTake>{

    @Autowired
    ResStockTakeRepository stockTakeRepository;

    @RequestMapping(method = RequestMethod.POST,path = "/search")
    public JsonResult search(@RequestBody SearchBean b){
        log.info(b.toString());
        return this.search(stockTakeRepository,b);
    }
}
