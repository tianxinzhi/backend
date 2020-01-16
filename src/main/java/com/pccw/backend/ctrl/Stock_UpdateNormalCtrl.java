package com.pccw.backend.ctrl;


import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.ResultRecode;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.stock_update_normal.InputBean;
import com.pccw.backend.bean.stock_update_normal.OutputBean;
import com.pccw.backend.bean.stock_update_normal.OutputDataBean;
import com.pccw.backend.bean.stock_update_normal.OutputItemBean;
import com.pccw.backend.entity.DbResLogMgt;
import com.pccw.backend.entity.DbResLogRor;
import com.pccw.backend.entity.DbResLogRorDtl;
import com.pccw.backend.entity.DbResProcess;
import com.pccw.backend.repository.ResLogRorRepository;
import com.pccw.backend.repository.ResRepoRepository;
import com.pccw.backend.repository.ResSkuRepoRepository;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/api/external/ppos/v1")
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
public class Stock_UpdateNormalCtrl extends BaseCtrl<DbResLogRor> {

    @Autowired
    private ResLogRorRepository repo;

    @Autowired
    ResSkuRepoRepository skuRepoRepository;

    @Autowired
    ResRepoRepository repoRepository;

    @Autowired
    Process_ProcessCtrl processProcessCtrl;


    @ApiOperation(value="创建stock_update",tags={"stock_update"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/stock_update")
    public OutputBean create(@RequestBody InputBean b){
        OutputBean output = new OutputBean();
        try {
            //输入验证
            long t = new Date().getTime();
            DbResLogRor logRor= new DbResLogRor(null,b.getOrder_system(),b.getOrder_id(),null,"N",b.getSales_id(),b.getTx_date(),b.getBiz_date(),null);
            logRor.setLogOrderNature(b.getRequest_nature());
            logRor.setLogTxtBum("2020test");
            logRor.setRemark(b.getRemarks());
            logRor.setLogType("O");
            logRor.setActive("Y");
            logRor.setUpdateAt(t);
            logRor.setCreateAt(t);
            logRor.setCreateBy(getAccount());
            logRor.setUpdateBy(getAccount());
            List<DbResLogRorDtl> logRorDtls=new ArrayList<>();
            if(Objects.nonNull(b.getItem_details()) && b.getItem_details().size() > 0){
                b.getItem_details().forEach(item -> {
                    DbResLogRorDtl rorDtl=new DbResLogRorDtl(null,item.getSku_id(),item.getItem_id(),item.getRepo_id(),Long.parseLong(item.getQuantity()),item.getCcc(),item.getWo(),item.getDetail_id(),logRor);
                    rorDtl.setActive("Y");
                    rorDtl.setUpdateAt(t);
                    rorDtl.setCreateAt(t);
                    rorDtl.setCreateBy(getAccount());
                    rorDtl.setUpdateBy(getAccount());
                    if(b.getRequest_nature().equals("ASG")){
                        rorDtl.setDtlAction("D");
                        rorDtl.setStatus("AVL");
                        rorDtl.setDtlSubin("Good");
                    }else if(b.getRequest_nature().equals("RET")){
                        rorDtl.setDtlAction("A");
                        rorDtl.setStatus("FAU");
                        rorDtl.setDtlSubin("Faulty");
                    }else if(b.getRequest_nature().equals("EXC")){
                        rorDtl.setDtlAction("A");
                        rorDtl.setStatus("FAU");
                        rorDtl.setDtlSubin("Faulty");
                        DbResLogRorDtl secRorDtl=new DbResLogRorDtl();
                        BeanUtils.copyProperties(rorDtl,secRorDtl);
                        secRorDtl.setDtlAction("D");
                        secRorDtl.setStatus("AVL");
                        secRorDtl.setDtlSubin("Good");
                        logRorDtls.add(secRorDtl);
                    }
                    logRorDtls.add(rorDtl);
                });
            }
            logRor.setLine(logRorDtls);
            repo.saveAndFlush(logRor);
            //通过输入参数的order_id查询log表数据，构造输出
            DbResLogRor resLogRor =repo.findDbResLogRorByLogOrderId(b.getOrder_id());
            OutputDataBean outputData =new OutputDataBean();
            if(Objects.nonNull(resLogRor)){
             output.setState("success");
             output.setCode("200");
             output.setMsg("stock update successfully");
             //EXC只取一行
             List<DbResLogRorDtl>  resRorDtl= new ArrayList<>();
             if(resLogRor.getLogOrderNature().equals("EXC")){
                resRorDtl= resLogRor.getLine().stream().filter( ror ->"FAU".equals(ror.getStatus()) ).collect(Collectors.toList());
             }else {
                resRorDtl=resLogRor.getLine();
             }
             List<OutputItemBean> itemLine=resRorDtl.stream().map(item->{
                 OutputItemBean outputItem= new OutputItemBean(item.getDetailId(),item.getDtlSkuId(),String.valueOf(item.getDtlQty()),
                         item.getDtlItemId(), item.getDtlRepoId(),item.getCcc(),item.getWo());
                 return outputItem;
             }).collect(Collectors.toList());
             outputData.setItem_details(itemLine);
             outputData.setTx_id(resLogRor.getLogTxtBum());
             output.setData(outputData);
            }
            return output;
        } catch (Exception e) {
            output.setCode("fail");
            output.setCode("888");
            output.setMsg(e.toString());
            return output;
        }
    }


    @ApiOperation(value="创建stock_update",tags={"stock_update"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/stock_update_ao")
    public OutputBean createAO(@RequestBody InputBean b){
        OutputBean output = new OutputBean();
        try {
            //输入验证
            long t = new Date().getTime();
            DbResLogRor logRor= new DbResLogRor(null,b.getOrder_system(),b.getOrder_id(),null,"N",b.getSales_id(),b.getTx_date(),b.getBiz_date(),null);
            logRor.setLogOrderNature(b.getRequest_nature());
            logRor.setLogTxtBum("2020testAO");
            logRor.setRemark(b.getRemarks());
            logRor.setLogType("O");
            logRor.setActive("Y");
            logRor.setUpdateAt(t);
            logRor.setCreateAt(t);
            logRor.setCreateBy(getAccount());
            logRor.setUpdateBy(getAccount());
            List<DbResLogRorDtl> logRorDtls=new ArrayList<>();
            if(Objects.nonNull(b.getItem_details()) && b.getItem_details().size() > 0){
                b.getItem_details().forEach(item -> {
                    DbResLogRorDtl rorDtl=new DbResLogRorDtl(null,item.getSku_id(),item.getItem_id(),item.getRepo_id(),Long.parseLong(item.getQuantity()),item.getCcc(),item.getWo(),item.getDetail_id(),logRor);
                    rorDtl.setActive("Y");
                    rorDtl.setUpdateAt(t);
                    rorDtl.setCreateAt(t);
                    rorDtl.setCreateBy(getAccount());
                    rorDtl.setUpdateBy(getAccount());
                    if(b.getRequest_nature().equals("ARS")){
                        rorDtl.setDtlAction("");
                        rorDtl.setStatus("");
                        rorDtl.setDtlSubin("");
                    }else if(b.getRequest_nature().equals("CARS")){
                        rorDtl.setDtlAction("A");
                        rorDtl.setStatus("FAU");
                        rorDtl.setDtlSubin("Faulty");
                    }else if(b.getRequest_nature().equals("APU")){
                       /* rorDtl.setDtlAction("A");
                        rorDtl.setStatus("FAU");
                        rorDtl.setDtlSubin("Faulty");
                        DbResLogRorDtl secRorDtl=new DbResLogRorDtl();
                        BeanUtils.copyProperties(rorDtl,secRorDtl);
                        secRorDtl.setDtlAction("D");
                        secRorDtl.setStatus("AVL");
                        secRorDtl.setDtlSubin("Good");
                        logRorDtls.add(secRorDtl);*/
                    }
                    logRorDtls.add(rorDtl);
                });
            }
            logRor.setLine(logRorDtls);
            repo.saveAndFlush(logRor);
            //通过输入参数的order_id查询log表数据，构造输出
            DbResLogRor resLogRor =repo.findDbResLogRorByLogOrderId(b.getOrder_id());
            OutputDataBean outputData =new OutputDataBean();
            if(Objects.nonNull(resLogRor)){
                output.setState("success");
                output.setCode("200");
                output.setMsg("stock update successfully");
                //只取一行
                List<DbResLogRorDtl>  resRorDtl= new ArrayList<>();
                if(resLogRor.getLogOrderNature().equals("CARS")){
                    resRorDtl= resLogRor.getLine().stream().filter( ror ->"FAU".equals(ror.getStatus()) ).collect(Collectors.toList());
                }else {
                    resRorDtl=resLogRor.getLine();
                }
                List<OutputItemBean> itemLine=resRorDtl.stream().map(item->{
                    OutputItemBean outputItem= new OutputItemBean(item.getDetailId(),item.getDtlSkuId(),String.valueOf(item.getDtlQty()),
                            item.getDtlItemId(), item.getDtlRepoId(),item.getCcc(),item.getWo());
                    return outputItem;
                }).collect(Collectors.toList());
                outputData.setItem_details(itemLine);
                outputData.setTx_id(resLogRor.getLogTxtBum());
                output.setData(outputData);
            }
            return output;
        } catch (Exception e) {
            output.setCode("fail");
            output.setCode("888");
            output.setMsg(e.toString());
            return output;
        }
    }


}