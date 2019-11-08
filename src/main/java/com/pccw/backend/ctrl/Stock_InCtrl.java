package com.pccw.backend.ctrl;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.stock_in.CreateBean;
import com.pccw.backend.bean.stock_in.EditBean;
import com.pccw.backend.bean.stock_in.SearchBean;
import com.pccw.backend.entity.DbResLogMgt;
import com.pccw.backend.entity.DbResLogMgtDtl;
import com.pccw.backend.repository.ResStockInRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/stock_in")
@Api(value="Stock_InCtrl",tags={"stock_in"})
public class Stock_InCtrl extends BaseCtrl<DbResLogMgt>{

    @Autowired
    ResStockInRepository rsipo;

    @ApiOperation(value="stock_in",tags={"stock_in"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/create")
    public JsonResult create(@RequestBody CreateBean bean) {
        System.out.println("-------------------");
        List<DbResLogMgtDtl> lineList = bean.getLine();
        for (int i = 0; i <lineList.size() ; i++) {
            lineList.get(i).setDtlAction(StaticVariable.DTLACTION_ADD);
            lineList.get(i).setStatus(StaticVariable.STATUS_AVAILABLE);
            lineList.get(i).setLisStatus(StaticVariable.LISSTATUS_WAITING);
            lineList.get(i).setId(null);
        }
        bean.setLine(lineList);
//        System.out.println("attrValue:"+bean);
//        System.out.println("attrValue:"+bean);
        return this.create(rsipo,DbResLogMgt.class,bean);
    }

//    @RequestMapping(method = RequestMethod.POST,path="/search")
//    public JsonResult search(@RequestBody SearchBean bean){
//        return this.search(rsipo,bean);
//    }
//
//    @RequestMapping(method = RequestMethod.POST,path="/edit")
//    public JsonResult edit(@RequestBody EditBean bean){
//        return this.edit(rsipo,DbResLogMgt.class,bean);
//    }
}
