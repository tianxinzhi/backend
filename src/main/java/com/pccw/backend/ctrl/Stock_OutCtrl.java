package com.pccw.backend.ctrl;


import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.ResultRecode;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.stock_out.CreateBean;
import com.pccw.backend.bean.stock_out.SearchBean;
import com.pccw.backend.entity.*;
import com.pccw.backend.repository.ResLogMgtRepository;
import com.pccw.backend.repository.ResRepoRepository;
import com.pccw.backend.repository.ResSkuRepoRepository;
import com.pccw.backend.repository.ResSkuRepoSerialRepository;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/stock_out")
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
public class Stock_OutCtrl  extends BaseCtrl<DbResLogMgt> {

    @Autowired
    private ResLogMgtRepository repo;

    @Autowired
    ResSkuRepoRepository skuRepoRepository;

    @Autowired
    ResSkuRepoSerialRepository serialRepository;

    @Autowired
    ResRepoRepository repoRepository;

    @Autowired
    Process_ProcessCtrl processProcessCtrl;



    @ApiOperation(value="",tags={""},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/search")
    public JsonResult search(@RequestBody SearchBean b) {
        log.info(b.toString());
        try {
            List<Map<String, Object>> list = skuRepoRepository.findByTypeIdAndRepoId(b.getFromRepoId(),b.getRepoType());
            List<Map<String, Object>> humpNameForList = ResultRecode.returnHumpNameForList(list);
            return JsonResult.success(humpNameForList);

        } catch (Exception e) {
            log.info(e.getMessage());
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="创建stock_out",tags={"stock_out"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/create")
    @Transactional(rollbackFor = Exception.class)
    public JsonResult create(@RequestBody CreateBean b){
        try {
            long t = new Date().getTime();
            b.setCreateAt(t);
            b.setActive("Y");
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
                    if(b.getRepoType().equals("W")) {
                        b.setLogOrderNature(StaticVariable.LOGORDERNATURE_STOCK_OUT_STW);
                    }else {
                        b.setLogOrderNature(StaticVariable.LOGORDERNATURE_STOCK_OUT_STS);
                    }

                }
            }
            JsonResult result =this.create(repo, DbResLogMgt.class, b);
            if(result.getCode().equals("000")){
                //创建工作流对象
//                DbResProcess process = new DbResProcess();
//                process.setLogTxtBum(b.getLogTxtBum());
//                process.setRepoId(b.getLogRepoOut());
//                process.setRemark(b.getRemark());
//                process.setCreateAt(t);
//                process.setUpdateAt(t);
//                process.setLogOrderNature(b.getLogOrderNature());
//                //生成工作流数据
//                processProcessCtrl.joinToProcess(process);
                //生成sku serial
                for (DbResLogMgtDtl dbResLogMgtDtl : b.getLine()) {
                    DbResSkuRepo sr = skuRepoRepository.findQtyByRepoAndShopAndType(b.getLogRepoOut(), dbResLogMgtDtl.getDtlSkuId(), 3L);
                    if(dbResLogMgtDtl.getLine() !=null && dbResLogMgtDtl.getLine().size()>0){
                        for (DbResSkuRepoSerial dbResSkuRepoSerial : dbResLogMgtDtl.getLine()) {
                            DbResSkuRepoSerial serial = new DbResSkuRepoSerial();
                            BeanUtils.copyProperties(dbResSkuRepoSerial,serial);
                            serial.setUpdateAt(t);
                            serial.setCreateAt(t);
                            serial.setUpdateBy(getAccount());
                            serial.setCreateBy(getAccount());
                            serial.setActive("Y");
                            serial.setSkuRepo(sr);
                            serialRepository.saveAndFlush(serial);
                        }
                    }
                }
                this.UpdateSkuRepoQty(b.getLogTxtBum());

            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
    }

    /**
     * 审批流完成后更新sku_repo
     * @param logTxtNum
     */
    public void UpdateSkuRepoQty(String logTxtNum) {
        try {
            DbResLogMgt b =repo.findDbResLogMgtByLogTxtBum(logTxtNum);
            long time = System.currentTimeMillis();
            for(int i=0;i<b.getLine().size();i++) {
                if (b.getLine().get(i).getDtlAction().equals("D")) {
                    System.out.println(-b.getLine().get(i).getDtlQty());
                    int res = skuRepoRepository.updateQtyByRepoAndShopAndTypeAndQty(b.getLogRepoOut(),b.getLine().get(i).getDtlSkuId(),3,-b.getLine().get(i).getDtlQty());
                } else {
                    DbResSkuRepo inSkuRepo = skuRepoRepository.findQtyByRepoAndShopAndType(b.getLogRepoIn(), b.getLine().get(i).getDtlSkuId(), 3L);
                    if(inSkuRepo==null){
                        DbResSkuRepo value = new DbResSkuRepo();
                        DbResRepo repo = new DbResRepo();repo.setId(b.getLogRepoIn());
                        value.setRepo(repo);
                        DbResSku sku = new DbResSku();sku.setId(b.getLine().get(i).getDtlSkuId());
                        value.setSku(sku);
                        DbResStockType stockType = new DbResStockType();stockType.setId(3L);
                        value.setStockType(stockType);
                        value.setQty(b.getLine().get(i).getDtlQty());
                        value.setRemark(b.getRemark());
                        value.setCreateAt(time);
                        value.setUpdateAt(time);
                        value.setCreateBy(getAccount());
                        value.setUpdateBy(getAccount());
                        value.setActive("Y");
                        skuRepoRepository.saveAndFlush(value);
                    } else {
                        inSkuRepo.setQty(inSkuRepo.getQty()+b.getLine().get(i).getDtlQty());
                        inSkuRepo.setUpdateAt(time);
                        inSkuRepo.setUpdateBy(getAccount());
                        skuRepoRepository.saveAndFlush(inSkuRepo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
