package com.pccw.backend.ctrl;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.ResultRecode;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.stock_in.CreateBean;
import com.pccw.backend.bean.stock_in.EditBean;
import com.pccw.backend.bean.stock_in.SearchBean;
import com.pccw.backend.entity.*;
import com.pccw.backend.repository.ResSkuRepoRepository;
import com.pccw.backend.repository.ResStockInRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/stock_in")
@Api(value="Stock_InCtrl",tags={"stock_in"})
public class Stock_InCtrl extends BaseCtrl<DbResLogMgt>{

    @Autowired
    ResStockInRepository rsipo;

    @Autowired
    ResSkuRepoRepository rsrr;

    @ApiOperation(value="stock_in",tags={"stock_in"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/create")
    public JsonResult create(@RequestBody CreateBean bean) {
        try {
            bean.setLogOrderNature(StaticVariable.LOGORDERNATURE_STOCK_TRANSFER_IN);
            List<DbResLogMgtDtl> lineList = bean.getLine();
            List<DbResSkuRepo> skuRepoList = new ArrayList<DbResSkuRepo>();
            for (int i = 0; i <lineList.size() ; i++) {
                lineList.get(i).setDtlSubin(StaticVariable.DTLSUBIN_AVAILABLE);
                lineList.get(i).setDtlAction(StaticVariable.DTLACTION_ADD);
                lineList.get(i).setStatus(StaticVariable.STATUS_AVAILABLE);
                lineList.get(i).setLisStatus(StaticVariable.LISSTATUS_WAITING);
                lineList.get(i).setId(null);

//                if (rsrr.findQtyByRepoAndShopAndType(bean.getLogRepoIn(),
//                                                     lineList.get(i).getDtlSkuId(),
//                                                     3) != null) {
//
//                    rsrr.updateQtyByRepoAndShopAndTypeAndQty(bean.getLogRepoIn(),
//                                                             lineList.get(i).getDtlSkuId(),
//                                                             3,
//                                                             lineList.get(i).getDtlQty());
//                }else {
//                    rsrr.saveRepoAndShopAndTypeAndQty(bean.getLogRepoIn(),
//                            lineList.get(i).getDtlSkuId(),
//                            3,
//                            lineList.get(i).getDtlQty());
//                }

                DbResSku dbResSku = new DbResSku();
                dbResSku.setId(lineList.get(i).getDtlSkuId());
                DbResRepo dbResRepo = new DbResRepo();
                dbResRepo.setId(bean.getLogRepoIn());
                DbResStockType dbResStockType = new DbResStockType();
                dbResStockType.setId(3L);
                DbResSkuRepo dbResSkuRepo = new DbResSkuRepo(null,dbResSku,dbResRepo,null,dbResStockType, Integer.parseInt(String.valueOf(lineList.get(i).getDtlQty())));
                rsrr.saveAndFlush(dbResSkuRepo);
            }
            bean.setLine(lineList);
//        System.out.println("attrValue:"+bean);
//        System.out.println("attrValue:"+bean);
          return this.create(rsipo,DbResLogMgt.class,bean);


        }catch (Exception e){
            return JsonResult.fail(e);
        }
    }

    @RequestMapping(method = RequestMethod.POST,path="/searchStockOutInfo")
    public JsonResult searchStockOutInfo(@RequestBody SearchBean bean){
        try {
            List stockOutInfo = rsipo.getStockOutInfo(bean.getLogTxtNum());
            List res = ResultRecode.returnHumpNameForList(stockOutInfo);

            return JsonResult.success(res);
        }catch (Exception e){
            return JsonResult.fail(e);
        }
    }
//
//    @RequestMapping(method = RequestMethod.POST,path="/edit")
//    public JsonResult edit(@RequestBody EditBean bean){
//        return this.edit(rsipo,DbResLogMgt.class,bean);
//    }
}
