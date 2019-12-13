package com.pccw.backend.ctrl;


import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.ResultRecode;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.stock_out.CreateBean;
import com.pccw.backend.bean.stock_out.SearchBean;
import com.pccw.backend.entity.DbResLogMgt;
import com.pccw.backend.entity.DbResProcess;
import com.pccw.backend.repository.ResLogMgtRepository;
import com.pccw.backend.repository.ResRepoRepository;
import com.pccw.backend.repository.ResSkuRepoRepository;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    ResRepoRepository repoRepository;

    @Autowired
    Process_ProcessCtrl processProcessCtrl;



    @ApiOperation(value="查询shop",tags={"masterfile_repo"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/search")
    public JsonResult search(@RequestBody SearchBean b) {
        log.info(b.toString());
        try {
            List<Map<String, Object>> list = skuRepoRepository.findByTypeIdAndRepoId(b.getFromRepoId(),b.getToRepoId());
            List<Map<String, Object>> humpNameForList = ResultRecode.returnHumpNameForList(list);
            return JsonResult.success(humpNameForList);

        } catch (Exception e) {
            log.info(e.getMessage());
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="创建stock_out",tags={"stock_out"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/create")
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
                    int res = repoRepository.getRepoType(b.getLogRepoIn());
                    if(res>0) {
                        b.setLogOrderNature(StaticVariable.LOGORDERNATURE_STOCK_OUT_STW);
                    }else {
                        b.setLogOrderNature(StaticVariable.LOGORDERNATURE_STOCK_OUT_STS);
                    }
                }
            }
            JsonResult result =this.create(repo, DbResLogMgt.class, b);
            if(result.getCode().equals("000")){
                //创建工作流对象
                DbResProcess process = new DbResProcess();
                process.setLogTxtBum(b.getLogTxtBum());
                process.setRepoId(b.getLogRepoOut());
                process.setRemark(b.getRemark());
                process.setCreateAt(t);
                process.setUpdateAt(t);
                process.setLogOrderNature(b.getLogOrderNature());
                //生成工作流数据
                processProcessCtrl.joinToProcess(process);

            }
            return result;
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }


    //审批流完成后更新sku_repo
    public void UpdateSkuRepoQty(String logTxtNum) {
        DbResLogMgt  b =repo.findDbResLogMgtByLogTxtBum(logTxtNum);
        for(int i=0;i<b.getLine().size();i++) {
            if (b.getLine().get(i).getDtlAction().equals("D")) {
                System.out.println(-b.getLine().get(i).getDtlQty());
                int res = skuRepoRepository.updateQtyByRepoAndShopAndTypeAndQty(b.getLogRepoOut(),b.getLine().get(i).getDtlSkuId(),3,-b.getLine().get(i).getDtlQty());
            }
        }
    }


}