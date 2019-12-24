package com.pccw.backend.ctrl;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.ResultRecode;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.stock_in.CreateBean;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    ResSkuRepoRepository skuRepoRepository;

    @Autowired
    Process_ProcessCtrl processProcessCtrl;

    @ApiOperation(value="stock_in",tags={"stock_in"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/create")
    public JsonResult create(@RequestBody CreateBean bean) {
        try {
            long t = new Date().getTime();
            bean.setLogOrderNature(StaticVariable.LOGORDERNATURE_STOCK_IN_WITHOUT_PO_STW);
            List<DbResLogMgtDtl> lineList = bean.getLine();
            for (int i = 0; i <lineList.size() ; i++) {
                lineList.get(i).setDtlSubin(StaticVariable.DTLSUBIN_AVAILABLE);
                lineList.get(i).setDtlAction(StaticVariable.DTLACTION_ADD);
                lineList.get(i).setStatus(StaticVariable.STATUS_AVAILABLE);
                lineList.get(i).setLisStatus(StaticVariable.LISSTATUS_WAITING);
                lineList.get(i).setLogTxtBum(bean.getLogTxtBum());
                lineList.get(i).setDtlRepoId(bean.getLogRepoIn());
                lineList.get(i).setCreateAt(t);
                lineList.get(i).setUpdateAt(t);
                lineList.get(i).setId(null);
            }

            bean.setLine(lineList);

            JsonResult result = this.create(rsipo, DbResLogMgt.class, bean);

            if(result.getCode().equals("000")){
                //创建工作流对象
                DbResProcess process = new DbResProcess();

                process.setLogTxtBum(bean.getLogTxtBum());
                process.setRepoId(bean.getLogRepoIn());
                process.setRemark(bean.getRemark());
                process.setCreateAt(t);
                process.setUpdateAt(t);
                process.setLogOrderNature(bean.getLogOrderNature());

                //生成工作流数据
                processProcessCtrl.joinToProcess(process);
            }

            return result;


        }catch (Exception e){
            return JsonResult.fail(e);
        }
    }

    /**
     * stock in without PO 和 stock in 有PO 存skuRepo表
     * @param logTxtBum
     */
    public void UpdateSkuRepoQty(String logTxtBum) {

        DbResLogMgt bean = rsipo.findDbResLogMgtByLogTxtBum(logTxtBum);

        for (DbResLogMgtDtl line:bean.getLine()){
            DbResSku dbResSku = new DbResSku();
            dbResSku.setId(line.getDtlSkuId());
            DbResRepo dbResRepo = new DbResRepo();
            dbResRepo.setId(bean.getLogRepoIn());
            DbResStockType dbResStockType = new DbResStockType();
            dbResStockType.setId(3L);
            DbResSkuRepo skuRepo = skuRepoRepository.findDbResSkuRepoByRepoAndSkuAndStockType(dbResRepo, dbResSku, dbResStockType);

            if (Objects.isNull(skuRepo)){

                DbResSkuRepo dbResSkuRepo = new DbResSkuRepo(null,dbResSku,dbResRepo,null,dbResStockType, (long) Integer.parseInt(String.valueOf(line.getDtlQty())),null);
                dbResSkuRepo.setCreateBy(bean.getCreateBy());
                dbResSkuRepo.setCreateAt(bean.getCreateAt());
                dbResSkuRepo.setUpdateAt(bean.getCreateAt());
                dbResSkuRepo.setUpdateBy(bean.getCreateBy());
                rsrr.saveAndFlush(dbResSkuRepo);
            }else {
                skuRepoRepository.updateQtyByRepoAndShopAndTypeAndQty(bean.getLogRepoIn(),line.getDtlSkuId(),3L,line.getDtlQty());
            }

        }
    }

    @ApiOperation(value="stock_out_info",tags={"searchStockOutInfo"},notes="注意问题点")
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

    @ApiOperation(value="stock_in_PO",tags={"stock_in_PO"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/createPOInfos")
    public JsonResult createPOInfos(@RequestBody CreateBean bean) {
        try {
            long t = new Date().getTime();
            bean.setLogOrderNature(StaticVariable.LOGORDERNATURE_STOCK_IN_STS);
            List<DbResLogMgtDtl> lineList = bean.getLine();
            List<DbResSkuRepo> skuRepoList = new ArrayList<DbResSkuRepo>();
            for (int i = 0; i <lineList.size() ; i++) {
                lineList.get(i).setDtlSubin(StaticVariable.DTLSUBIN_AVAILABLE);
                lineList.get(i).setDtlAction(StaticVariable.DTLACTION_ADD);
                lineList.get(i).setStatus(StaticVariable.STATUS_AVAILABLE);
                lineList.get(i).setLisStatus(StaticVariable.LISSTATUS_WAITING);
                lineList.get(i).setCreateAt(t);
                lineList.get(i).setUpdateAt(t);
                lineList.get(i).setId(null);
            }
            bean.setLine(lineList);

            JsonResult result = this.create(rsipo, DbResLogMgt.class, bean);

            if(result.getCode().equals("000")){
                //创建工作流对象
                DbResProcess process = new DbResProcess();

                process.setLogTxtBum(bean.getLogTxtBum());
                process.setRepoId(bean.getLogRepoOut());
                process.setRemark(bean.getRemark());
                process.setCreateAt(t);
                process.setUpdateAt(t);
                process.setLogOrderNature(bean.getLogOrderNature());

                //生成工作流数据
                processProcessCtrl.joinToProcess(process);
            }

            return result;


        }catch (Exception e){
            return JsonResult.fail(e);
        }
    }
}
