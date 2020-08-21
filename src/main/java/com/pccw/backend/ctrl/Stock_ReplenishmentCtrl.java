package com.pccw.backend.ctrl;


import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.stock_replenishment.CreateReplBean;
import com.pccw.backend.bean.stock_replenishment.EditBean;
import com.pccw.backend.bean.stock_replenishment.SearchBean;
import com.pccw.backend.entity.*;
import com.pccw.backend.repository.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Slf4j
@RestController
@RequestMapping("/stock_replenishment")
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
public class Stock_ReplenishmentCtrl extends BaseStockCtrl<DbResStockReplenishment> {

    @Autowired
    ResLogMgtRepository logMgtRepository;
    @Autowired
    ResSkuRepoRepository rsRepo;
    @Autowired
    ResRepoRepository rRepo;
    @Autowired
    ResStockTypeRepository rstRepo;
    @Autowired
    ResSkuRepository resSkuRepo;
    @Autowired
    ResStockReplenishmentRepository replenishmentRepository;

    @ApiOperation(value="补货搜索",tags={"stock_replenishment"},notes="查询")
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public JsonResult search(@RequestBody SearchBean bean) {
        System.out.println(bean.toString());
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"id"));
        List<DbResStockReplenishment> list = replenishmentRepository.getDbResStockReplenishmentsByActiveEquals("Y",Sort.by(orders));

        return JsonResult.success(list);
    }

    /**
     * 收货
     * @param bean
     * @return
     */
    @ApiOperation(value="创建replenishment",tags={"stock_replenishment"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    public JsonResult create(@RequestBody CreateReplBean bean) {
        try {
            bean.setLogTxtNum(genTranNum(new Date(),"RP",rRepo.findById(bean.getFromChannelId()).get().getRepoCode()));
            long time = System.currentTimeMillis();
            this.create(replenishmentRepository,DbResStockReplenishment.class,bean);
            DbResSku sku = new DbResSku();sku.setId(bean.getSkuId());
            DbResRepo repo = new DbResRepo();repo.setId(bean.getFromChannelId());
            DbResRepo repo2 = new DbResRepo();repo.setId(bean.getToChannelId());
            DbResStockType stockType = new DbResStockType();stockType.setId(3L);
            DbResSkuRepo skuRepo = rsRepo.findDbResSkuRepoByRepoAndSkuAndStockType(repo, sku, stockType);
            //扣减fromchannel available qty加到tochanel
            if(skuRepo != null){
                DbResSkuRepo skuRepo2 = rsRepo.findDbResSkuRepoByRepoAndSkuAndStockType(repo2, sku, stockType);
                //to channel 存在avaible，修改qty
                if(skuRepo2 != null){
                    skuRepo2.setUpdateBy(getAccount());
                    skuRepo2.setUpdateAt(time);
                    skuRepo2.setQty(skuRepo2.getQty()+bean.getQty());
                    rsRepo.saveAndFlush(skuRepo2);
                } else {
                    //不存在avaible，新增一条
                    DbResSkuRepo value = new DbResSkuRepo();
                    value.setRepo(repo2);
                    value.setSku(sku);
                    value.setStockType(stockType);
                    value.setQty(bean.getQty());
//                    value.setRemark(bean.get);
                    value.setCreateAt(time);
                    value.setUpdateAt(time);
                    value.setCreateBy(getAccount());
                    value.setUpdateBy(getAccount());
                    value.setActive("Y");
                    rsRepo.saveAndFlush(value);
                }
                //扣除对应available的qty
                skuRepo.setQty(skuRepo.getQty()-bean.getQty());
                skuRepo.setUpdateBy(getAccount());
                skuRepo.setUpdateAt(time);
                rsRepo.saveAndFlush(skuRepo);
            }
            //插入日志
            DbResLogMgt logMgt = new DbResLogMgt();
            logMgt.setLogRepoOut(bean.getFromChannelId());
            logMgt.setLogRepoIn(bean.getToChannelId());
            logMgt.setLogTxtBum(bean.getLogTxtNum());
            logMgt.setLogType(StaticVariable.LOGTYPE_REPL);
            logMgt.setLogOrderNature(StaticVariable.LOGORDERNATURE_REPLENISHMENT_REQUEST);
            logMgt.setStatus(StaticVariable.STATUS_WAITING);
//            logMgt.setRemark(bean.getRemarks());
            logMgt.setCreateAt(time);
            logMgt.setUpdateAt(time);
            logMgt.setCreateBy(getAccount());
            logMgt.setUpdateBy(getAccount());
            logMgt.setActive("Y");

            List<DbResLogMgtDtl> line = new LinkedList<>();
            DbResLogMgtDtl dtl = new DbResLogMgtDtl();
            dtl.setDtlRepoId(bean.getToChannelId());
            dtl.setDtlSkuId(bean.getSkuId());
            dtl.setDtlQty(bean.getQty());
            dtl.setLogTxtBum(bean.getLogTxtNum());
            dtl.setStatus(StaticVariable.STATUS_AVAILABLE);
            dtl.setLisStatus(StaticVariable.LISSTATUS_WAITING);
            dtl.setDtlAction(StaticVariable.DTLACTION_ADD);
            dtl.setDtlSubin(StaticVariable.DTLSUBIN_AVAILABLE);
            dtl.setCreateAt(time);
            dtl.setUpdateAt(time);
            dtl.setCreateBy(getAccount());
            dtl.setUpdateBy(getAccount());
            dtl.setActive("Y");

            DbResLogMgtDtl dtl2 = new DbResLogMgtDtl();
            BeanUtils.copyProperties(dtl,dtl2);
            dtl2.setDtlRepoId(bean.getFromChannelId());
            dtl2.setStatus(StaticVariable.STATUS_AVAILABLE);
            dtl2.setLisStatus(StaticVariable.LISSTATUS_WAITING);
            dtl2.setDtlAction(StaticVariable.DTLACTION_DEDUCT);
            dtl2.setDtlSubin(StaticVariable.DTLSUBIN_AVAILABLE);

            line.add(dtl);
            line.add(dtl2);
            logMgt.setLine(line);
            logMgtRepository.saveAndFlush(logMgt);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
        return JsonResult.success(Arrays.asList());
    }


    @ApiOperation(value="删除",tags={"stock_replenishment"},notes="")
    @RequestMapping(method = RequestMethod.POST,value = "/delete")
    public JsonResult delete(@RequestBody EditBean bean) {
        try {
            System.out.println(bean);
            long time = System.currentTimeMillis();
            DbResStockReplenishment replenishment = replenishmentRepository.findById(bean.getId()).get();
            replenishment.setUpdateAt(time);
            replenishment.setUpdateBy(getAccount());
            replenishment.setActive("N");
            replenishmentRepository.saveAndFlush(replenishment);

            DbResSku sku = new DbResSku();sku.setId(bean.getSkuId());
            DbResRepo repo = new DbResRepo();repo.setId(bean.getFromChannelId());
            DbResRepo repo2 = new DbResRepo();repo.setId(bean.getToChannelId());
            DbResStockType stockType = new DbResStockType();stockType.setId(3L);
            DbResSkuRepo skuRepo = rsRepo.findDbResSkuRepoByRepoAndSkuAndStockType(repo, sku, stockType);
            //之前扣减的加到available qty
            if(skuRepo != null){
                skuRepo.setQty(skuRepo.getQty()+replenishment.getQty());
                skuRepo.setUpdateAt(time);
                skuRepo.setUpdateBy(getAccount());
                rsRepo.saveAndFlush(skuRepo);
            }
            DbResSkuRepo skuRepo2 = rsRepo.findDbResSkuRepoByRepoAndSkuAndStockType(repo2, sku, stockType);
            //扣减之前available的qty
            if(skuRepo2 != null){
                skuRepo2.setQty(skuRepo2.getQty()-replenishment.getQty());
                skuRepo2.setUpdateAt(time);
                skuRepo2.setUpdateBy(getAccount());
                rsRepo.saveAndFlush(skuRepo2);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
        return JsonResult.success(Arrays.asList());
    }

    @ApiOperation(value="修改",tags={"stock_replenishment"},notes="")
    @RequestMapping(method = RequestMethod.POST,value = "/edit")
    public JsonResult edit(@RequestBody EditBean bean) {
        try {
            System.out.println(bean.toString());
            DbResStockReplenishment replenishment = replenishmentRepository.findById(bean.getId()).get();
            long time = System.currentTimeMillis();
            //没有修改repo和sku，即扣减之前available的qty，添加新的available qty
            if(bean.getFromChannelId() == replenishment.getFromChannelId() &&
                    bean.getSkuId() == replenishment.getSkuId() &&
                    bean.getToChannelId() == replenishment.getToChannelId()){
                DbResSku sku = new DbResSku();sku.setId(bean.getSkuId());
                DbResRepo repo = new DbResRepo();repo.setId(bean.getFromChannelId());
                DbResRepo repo2 = new DbResRepo();repo.setId(bean.getToChannelId());
                DbResStockType stockType = new DbResStockType();stockType.setId(3L);

                DbResSkuRepo skuRepo = rsRepo.findDbResSkuRepoByRepoAndSkuAndStockType(repo, sku, stockType);
                //avalible的加上之前faulty的qty，扣减新的faulty qty
                if(skuRepo!=null){
                    skuRepo.setQty(skuRepo.getQty()+replenishment.getQty()-bean.getQty());
                    skuRepo.setUpdateAt(time);
                    skuRepo.setUpdateBy(getAccount());
                    rsRepo.saveAndFlush(skuRepo);
                }
                //faulty的减去之前的qty加上现在的qty
                DbResSkuRepo skuRepo2 = rsRepo.findDbResSkuRepoByRepoAndSkuAndStockType(repo2, sku, stockType);
                if(skuRepo2 != null){
                    skuRepo2.setUpdateAt(time);
                    skuRepo2.setUpdateBy(getAccount());
                    skuRepo2.setQty(skuRepo2.getQty()-replenishment.getQty()+bean.getQty());
                    rsRepo.saveAndFlush(skuRepo2);
                }
            } else {
                //修改了repo或者sku
                DbResSku sku2 = new DbResSku();sku2.setId(replenishment.getSkuId());
                DbResRepo repo2 = new DbResRepo();repo2.setId(replenishment.getFromChannelId());
                DbResRepo repo3 = new DbResRepo();repo2.setId(replenishment.getToChannelId());
                DbResStockType stockType2 = new DbResStockType();stockType2.setId(3L);
                DbResSkuRepo value = rsRepo.findDbResSkuRepoByRepoAndSkuAndStockType(repo2, sku2, stockType2);
                //找到之前的available加上之前的faulty qty
                if(value != null) {
                    value.setQty(value.getQty()+replenishment.getQty());
                    value.setUpdateAt(time);
                    value.setUpdateBy(getAccount());
                    rsRepo.saveAndFlush(value);

                    DbResSku sku3 = new DbResSku();sku3.setId(bean.getSkuId());
                    DbResSkuRepo value2 = rsRepo.findDbResSkuRepoByRepoAndSkuAndStockType(repo2, sku3, stockType2);
                    //找到现在的available，减去现在的qty
                    if(value2!=null){
                        value2.setQty(value2.getQty()-bean.getQty());
                        value2.setUpdateAt(time);
                        value2.setUpdateBy(getAccount());
                        rsRepo.saveAndFlush(value2);

                        //找到之前的faulty减去之前qty
                        DbResSkuRepo resereved = rsRepo.findDbResSkuRepoByRepoAndSkuAndStockType(repo2, sku2, stockType2);
                        if(resereved!=null){
                            resereved.setUpdateAt(time);
                            resereved.setUpdateBy(getAccount());
                            resereved.setQty(resereved.getQty()-replenishment.getQty());
                            rsRepo.saveAndFlush(resereved);

                            //找到现在的faulty加上现在的qty
                            DbResSkuRepo resereved1 = rsRepo.findDbResSkuRepoByRepoAndSkuAndStockType(repo3, sku3, stockType2);
                            if(resereved1 != null){
                                resereved1.setUpdateAt(time);
                                resereved1.setUpdateBy(getAccount());
                                resereved1.setQty(resereved1.getQty()+bean.getQty());
                                rsRepo.saveAndFlush(value2);
                            } else {
                                DbResSkuRepo newReserved = new DbResSkuRepo();
                                newReserved.setRepo(repo3);
                                newReserved.setSku(sku3);
                                newReserved.setStockType(stockType2);
                                newReserved.setQty(bean.getQty());
//                                newReserved.setRemark(bean.getRemarks());
                                newReserved.setCreateAt(time);
                                newReserved.setUpdateAt(time);
                                newReserved.setCreateBy(getAccount());
                                newReserved.setUpdateBy(getAccount());
                                newReserved.setActive("Y");
                                rsRepo.saveAndFlush(value);
                            }
                        }
                    }
                }
            }
            this.edit(replenishmentRepository, DbResStockReplenishment.class, bean);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
        return JsonResult.success(Arrays.asList());
    }

    /**
     * stock replenishment 修改表 skuRepo
     * @param logTxtBum
     */
    public void UpdateSkuRepoQty(String logTxtBum) {

        DbResLogMgt cb = logMgtRepository.findDbResLogMgtByLogTxtBum(logTxtBum);
        List<DbResLogMgtDtl> line = cb.getLine();
        long t = new Date().getTime();
        //插入表res_sku_repo 添加或修改qty(工作流加入后 需要process的status为approve状态时再入库sku_repo)
        line.forEach(dtl->{
            if(!StaticVariable.LOGORDERNATURE_REPLENISHMENT_REQUEST.equals(cb.getLogOrderNature())){
                DbResSkuRepo skuShop = rsRepo.findQtyByRepoAndShopAndType(cb.getLogRepoIn(), dtl.getDtlSkuId(), 3l);
                if(!Objects.isNull(skuShop)){
                    DbResSkuRepo skuShop1 = rsRepo.findById(skuShop.getId()).get();
                    skuShop1.setQty((skuShop.getQty()+dtl.getDtlQty()));
                    skuShop1.setUpdateAt(t);
                    skuShop1.setUpdateBy(getAccount());
                }else{
                    DbResSkuRepo skuShop2 = new DbResSkuRepo();
                    skuShop2.setCreateAt(t);
                    skuShop2.setCreateBy(getAccount());
                    skuShop2.setUpdateAt(t);
                    skuShop2.setUpdateBy(getAccount());
                    skuShop2.setActive("Y");
                    skuShop2.setQty(dtl.getDtlQty());
                    skuShop2.setRepo(rRepo.findById(cb.getLogRepoIn()).get());
                    skuShop2.setStockType(rstRepo.findById(3l).get());
                    skuShop2.setSku(resSkuRepo.findById(dtl.getDtlSkuId()).get());
                    rsRepo.saveAndFlush(skuShop2);
                }
            }
        });

    }

}
