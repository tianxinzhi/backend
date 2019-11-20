package com.pccw.backend.ctrl;


import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.stock_out.CreateBean;
import com.pccw.backend.entity.DbResLogMgt;
import com.pccw.backend.repository.ResLogMgtRepository;
import com.pccw.backend.repository.ResSkuRepoRepository;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@Slf4j
@RestController
@RequestMapping("/stock_out")
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
public class Stock_OutCtrl  extends BaseCtrl<DbResLogMgt> {

    @Autowired
    private ResLogMgtRepository repo;

    @Autowired
    ResSkuRepoRepository skuRepoRepository;

    @ApiOperation(value="创建stock_out",tags={"stock_out"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/create")
    public JsonResult create(@RequestBody CreateBean b){
        try {
            long t = new Date().getTime();
            b.setCreateAt(t);
            b.setActive("Y");
            b.setLogOrderNature(StaticVariable.LOGORDERNATURE_STOCK_TRANSFER_OUT);
            b.setStatus(StaticVariable.STATUS_WAITING);
            for(int i=0;i<b.getLine().size();i++) {
                b.getLine().get(i).setUpdateAt(t);
                b.getLine().get(i).setCreateAt(t);
                b.getLine().get(i).setActive("Y");
                b.getLine().get(i).setLogTxtBum(b.getLogTxtBum());
                if(b.getLine().get(i).getDtlAction().equals("D")){
                    b.getLine().get(i).setDtlRepoId(b.getLogRepoOut());
                }else {
                    b.getLine().get(i).setDtlRepoId(b.getLogRepoIn());
                }
            }
            //审批流完成后更新sku_repo
            for(int i=0;i<b.getLine().size();i++) {
                if (b.getLine().get(i).getDtlAction().equals("D")) {
                    System.out.println(-b.getLine().get(i).getDtlQty());
                    int res = skuRepoRepository.updateQtyByRepoAndShopAndTypeAndQty(b.getLogRepoOut(),b.getLine().get(i).getDtlSkuId(),3,-b.getLine().get(i).getDtlQty());
                    if(res<=0) return JsonResult.fail(new Exception());
                }
            }
           //

            return this.create(repo, DbResLogMgt.class, b);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }



}