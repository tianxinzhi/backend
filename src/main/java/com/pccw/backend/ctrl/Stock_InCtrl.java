package com.pccw.backend.ctrl;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.stock_in.CreateBean;
import com.pccw.backend.entity.DbResLogMgt;
import com.pccw.backend.repository.ResStockInRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/stock_in")
@Api(value="Stock_InCtrl",tags={"stock_in"})
public class Stock_InCtrl extends BaseCtrl<DbResLogMgt>{

    @Autowired
    ResStockInRepository rsipo;

    @ApiOperation(value="创建attr_value",tags={"masterfile_attr_value"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/create")
    public JsonResult create(@RequestBody CreateBean bean) {
        System.out.println("-------------------");
//        System.out.println("attrValue:"+bean);
//        System.out.println("attrValue:"+bean);
        return this.create(rsipo,DbResLogMgt.class,bean);
    }
}
