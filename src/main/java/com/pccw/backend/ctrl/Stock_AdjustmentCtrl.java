package com.pccw.backend.ctrl;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.stock_adjustment.LogMgtBean;
import com.pccw.backend.bean.stock_adjustment.LogMgtDtlBean;
import com.pccw.backend.bean.stock_adjustment.SearchBean;
import com.pccw.backend.entity.*;
import com.pccw.backend.repository.ResAdjustReasonRepository;
import com.pccw.backend.repository.ResLogMgtRepository;
import com.pccw.backend.repository.ResSkuRepoRepository;
import com.pccw.backend.repository.ResStockTypeRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/stock_adjustment")
@Api(value="Stock_AdjustmentCtrl",tags={"stock_adjustment"})
public class Stock_AdjustmentCtrl extends BaseCtrl<DbResLogMgt> {

    @Autowired
    ResLogMgtRepository logMgtRepository;
    @Autowired
    ResSkuRepoRepository skuRepoRepository;
    @Autowired
    ResAdjustReasonRepository reasonRepository;
    @Autowired
    ResStockTypeRepository stockTypeRepository;

    @ApiOperation(value="调货",tags={"stock_adjustment"},notes="说明")
    @RequestMapping("/confirm")
    public JsonResult confirm(@RequestBody LogMgtBean bean) {
        try {
            System.out.println("logMgtBean:"+bean);
            DbResLogMgt ent = new DbResLogMgt();
            ent.setLogRepoIn(bean.getLogRepoIn());
            ent.setLogRepoOut(bean.getLogRepoOut());
            ent.setLogTxtBum(bean.getTransactionNumber());
            ent.setLogType(StaticVariable.LOGTYPE_MANAGEMENT);
            ent.setLogOrderNature(StaticVariable.LOGORDERNATURE_STOCK_TAKE_ADJUSTMENT);
            ent.setStatus(StaticVariable.STATUS_WAITING);
            if(bean.getReason()!=0){
                ent.setAdjustReasonId(bean.getReason());
            } else {
                DbResAdjustReason resAdjustReason = new DbResAdjustReason();
                resAdjustReason.setAdjustReasonName("Other Reason");
                resAdjustReason.setRemark(bean.getRemark());
                resAdjustReason.setCreateAt(bean.getCreateDate());
                resAdjustReason.setUpdateAt(bean.getCreateDate());
                resAdjustReason.setActive("Y");
                reasonRepository.saveAndFlush(resAdjustReason);
                ent.setAdjustReasonId(resAdjustReason.getId());
            }
            ent.setCreateAt(bean.getCreateDate());
            ent.setUpdateAt(bean.getCreateDate());
            ent.setActive("Y");
            ent.setCreateBy(bean.getCreateBy());
            ent.setUpdateBy(bean.getUpdateBy());

            List<DbResLogMgtDtl> lstMgtDtl = new LinkedList<>();

            for(LogMgtDtlBean dtl:bean.getLine()) {
                DbResLogMgtDtl mgtDtl = new DbResLogMgtDtl();
                mgtDtl.setDtlRepoId(bean.getLogRepoOut());
                BeanUtils.copyProperties(dtl, mgtDtl);
                mgtDtl.setLogTxtBum(bean.getTransactionNumber());
                //mgtDtl.setDtlLogId();
                mgtDtl.setDtlAction(dtl.getDtlQty()>0 ? StaticVariable.DTLACTION_ADD : StaticVariable.DTLACTION_DEDUCT);
                mgtDtl.setLisStatus(StaticVariable.LISSTATUS_WAITING);
                mgtDtl.setCreateAt(bean.getCreateDate());
                mgtDtl.setUpdateAt(bean.getCreateDate());
                mgtDtl.setCreateBy(bean.getCreateBy());
                mgtDtl.setUpdateBy(bean.getUpdateBy());
                mgtDtl.setActive("Y");
                mgtDtl.setResLogMgt(ent);

                DbResStockType stockType = stockTypeRepository.findById(dtl.getCatalog()).get();

                if(stockType!=null){
                    String type = stockType.getStockTypeName();
                    String status = type.substring(type.indexOf("(")+1,type.indexOf(")"));
                    String subin = type.replace(type.substring(type.indexOf("("),type.indexOf(")")+1),"");
                    log.info("tp:"+type+",subin:"+subin+",status:"+status);
                    mgtDtl.setDtlSubin(subin);
                    mgtDtl.setStatus(status);
                }
                lstMgtDtl.add(mgtDtl);
                int res = skuRepoRepository.updateQtyByRepoAndShopAndTypeAndQty(bean.getLogRepoOut(),dtl.getDtlSkuId(),dtl.getCatalog(),dtl.getDtlQty());
                if(res<=0) return JsonResult.fail(new Exception());
            }

            ent.setLine(lstMgtDtl);
            logMgtRepository.saveAndFlush(ent);

            return JsonResult.success(Arrays.asList());
        } catch (BeansException e) {
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="sku库存",tags={"stock_adjustment"},notes="说明")
    @RequestMapping("/balanceSearch")
    public JsonResult balanceSearch(@RequestBody SearchBean bean) {
        try {
            DbResSkuRepo skuRepo = skuRepoRepository.findQtyByRepoAndShopAndType(bean.getDtlRepoId(),bean.getDtlSkuId(),bean.getCatalog());
            if(skuRepo==null){
                return JsonResult.fail(Arrays.asList());
            }
            return JsonResult.success(Arrays.asList(skuRepo));
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="根据repo查询sku库存",tags={"stock_adjustment"},notes="说明")
    @RequestMapping("/searchByRepo")
    public JsonResult searchByRepo(@RequestBody SearchBean bean) {
        try {
            DbResRepo repo = new DbResRepo();
            repo.setId(bean.getDtlRepoId());
            List<DbResSkuRepo> skuRepos = skuRepoRepository.findDbResSkuRepoByRepo(repo);
            List<SearchBean> resBeans = new LinkedList<>();
            for (DbResSkuRepo skuRepo : skuRepos) {
                SearchBean res = new SearchBean();
                DbResSku sku = skuRepo.getSku();
                res.setDtlSkuId(sku.getId());
                res.setSkuCode(sku.getSkuCode());
                res.setCatalog(skuRepo.getStockType().getId());
                res.setDtlRepoId(skuRepo.getRepo().getId());
                res.setDtlQty(skuRepo.getQty());
                resBeans.add(res);
            }
            return JsonResult.success(resBeans.stream().filter(distinctByKey(resBean -> resBean.getDtlSkuId())).collect(Collectors.toList()));
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
