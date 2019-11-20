package com.pccw.backend.ctrl;


import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.ResultRecode;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.stock_replenishment.CreateReplBean;
import com.pccw.backend.bean.stock_replenishment.CreateReplDtlBean;
import com.pccw.backend.bean.stock_replenishment.SearchBean;
import com.pccw.backend.entity.DbResLogRepl;
import com.pccw.backend.entity.DbResLogReplDtl;
import com.pccw.backend.entity.DbResSkuRepo;
import com.pccw.backend.repository.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;


@Slf4j
@RestController
@RequestMapping("/stock_replenishment")
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
public class Stock_ReplenishmentCtrl extends BaseCtrl<DbResLogRepl> {

    @Autowired
    ResLogReplRepository repo;
    @Autowired
    ResSkuRepoRepository rsRepo;
    @Autowired
    ResRepoRepository rRepo;
    @Autowired
    ResStockTypeRepository rstRepo;
    @Autowired
    ResSkuRepository resSkuRepo;

    /**
     * 收货
     * @param b
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    @ApiOperation(value="创建replenishment",tags={"stock_replenishment"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    public JsonResult create(@RequestBody CreateReplBean b) {
        try {
            long t = new Date().getTime();
            b.setCreateAt(t);
            b.setActive("Y");
            if(StaticVariable.LOGORDERNATURE_REPLENISHMENT_RECEIVEN.equals(b.getReplType())){
                b.setRepoIdFrom(b.getLogRepoOut());
                b.setRepoIdTo(b.getLogRepoIn());
                b.setLogOrderNature(StaticVariable.LOGORDERNATURE_REPLENISHMENT_RECEIVEN);
                b.setStatus(StaticVariable.STATUS_DONE);
            }else{
                b.setLogOrderNature(StaticVariable.LOGORDERNATURE_REPLENISHMENT_REQUEST);
                b.setStatus(StaticVariable.STATUS_WAITING);
            }
            b.setLogType(StaticVariable.LOGTYPE_REPL);

            DbResLogRepl repl = new DbResLogRepl();
            BeanUtils.copyProperties(b, repl);
            List<DbResLogReplDtl> dtlss = new ArrayList<DbResLogReplDtl>();
            repl.setLine(dtlss);
            List<CreateReplDtlBean> dtls = b.getLine();
            for(CreateReplDtlBean dt:dtls){
                dt.setCreateAt(t);
                dt.setLogTxtBum(b.getLogTxtBum());
                DbResLogReplDtl dtl = new DbResLogReplDtl();
                BeanUtils.copyProperties(dt, dtl);
                if(StaticVariable.LOGORDERNATURE_REPLENISHMENT_RECEIVEN.equals(b.getReplType())){
                    dtl.setDtlAction(StaticVariable.DTLACTION_ADD);
                    dtl.setDtlSubin(StaticVariable.DTLSUBIN_GOOD);
                    dtl.setLisStatus(StaticVariable.LISSTATUS_WAITING);
                    dtl.setStatus(StaticVariable.STATUS_AVAILABLE);

                    //插入表res_sku_repo 添加或修改qty
                    DbResSkuRepo skuShop = rsRepo.findQtyByRepoAndShopAndType(b.getRepoIdTo(), dtl.getDtlSkuId(), 3l);
                    if(!Objects.isNull(skuShop)){
                        DbResSkuRepo skuShop1 = rsRepo.findById(skuShop.getId()).get();
                        skuShop1.setQty((int) (skuShop.getQty()+dtl.getDtlQty()));
                        skuShop1.setUpdateAt(t);
                    }else{
                        DbResSkuRepo skuShop2 = new DbResSkuRepo();
                        skuShop2.setCreateAt(t);
                        skuShop2.setUpdateAt(t);
                        skuShop2.setActive("Y");
                        skuShop2.setQty((int) dtl.getDtlQty());
                        skuShop2.setRepo(rRepo.findById(b.getRepoIdTo()).get());
                        skuShop2.setStockType(rstRepo.findById(3l).get());
                        skuShop2.setSku(resSkuRepo.findById(dtl.getDtlSkuId()).get());
                        rsRepo.saveAndFlush(skuShop2);
                    }
                }else{
                    dtl.setLisStatus(StaticVariable.LISSTATUS_WAITING);

                    //res_sku_repo仓库数量减
                    DbResSkuRepo skuRepo = rsRepo.findQtyByRepoAndShopAndType(b.getRepoIdFrom(), dtl.getDtlSkuId(), 3l);
                    if(!Objects.isNull(skuRepo)){
                        DbResSkuRepo skuRepo1 = rsRepo.findById(skuRepo.getId()).get();
                        skuRepo1.setUpdateAt(t);
                        long qty = skuRepo.getQty() - dtl.getDtlQty();
                        if(qty > 0){
                            skuRepo1.setQty((int) qty);
                        }else {
                            rsRepo.deleteById(skuRepo.getId());
                        }
                    }
                }
                dtl.setDbResLogRepl(repl);
                repl.getLine().add(dtl);
            }
            repo.saveAndFlush(repl);
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            System.out.println(e);
            return JsonResult.fail(e);
        }
    }

    @RequestMapping(method = RequestMethod.POST,path="/searchReplenishmentInfo")
    public JsonResult searchReplenishmentInfo(@RequestBody SearchBean bean){
        try {
            log.info(bean.toString());
            String batchId = Objects.isNull(bean.getLogBatchId()) ? "" : bean.getLogBatchId();
            String dnNum = Objects.isNull(bean.getLogDnNum()) ? "" : bean.getLogDnNum();
            List res = repo.getReplenishmentInfo(batchId, dnNum);
            List list = ResultRecode.returnHumpNameForList(res);
            return JsonResult.success(list);
        }catch (Exception e){
            return JsonResult.fail(e);
        }
    }
}
