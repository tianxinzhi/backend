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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
            b.setCreateAt(t);
            b.setActive("Y");
            DbResLogRor logRor=new DbResLogRor();
            logRor.setLogSys(b.getOrder_system());
            logRor.setLogOrderId(b.getOrder_id());
            logRor.setLogOrderNature(b.getRequest_nature());
            logRor.setLogOrderType("N");
            logRor.setSalesId(b.getSales_id());
            List<DbResLogRorDtl> logRorDtls=new ArrayList<>();
            for(int i=0;i<b.getItem_details().size();i++) {
                DbResLogRorDtl rorDtl=new DbResLogRorDtl();
                rorDtl.setDtlSkuId(Long.parseLong(b.getItem_details().get(i).getSku_id()) );
                rorDtl.setDtlQty(Long.parseLong(b.getItem_details().get(i).getQuantity()) );
                rorDtl.setDtlItemId(Long.parseLong(b.getItem_details().get(i).getItem_id()) );
                rorDtl.setDtlRepoId(Long.parseLong(b.getItem_details().get(i).getRepo_id()) );
                rorDtl.setCcc(b.getItem_details().get(i).getCcc());
                rorDtl.setWo(b.getItem_details().get(i).getWo());
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
            }
            logRor.setLine(logRorDtls);
            logRor.setTxDate(b.getTx_date());
            logRor.setBizDate(b.getBiz_date());
            repo.saveAndFlush(logRor);
            //通过输入参数的order_id查询log表的logOrderId
            DbResLogRor resLogRor =repo.findDbResLogRorByLogOrderId(b.getOrder_id());
            OutputDataBean date =new OutputDataBean();
            if(resLogRor!=null){
             output.setState("success");
             output.setCode("200");
             output.setMsg("stock update successfully");
             List<OutputItemBean> itemLine=resLogRor.getLine().stream().map(item->{
                 return new OutputItemBean(item.getId().toString() ,String.valueOf(item.getDtlSkuId()),String.valueOf(item.getDtlQty()),
                         String.valueOf(item.getDtlItemId()), String.valueOf(item.getDtlRepoId()),item.getCcc(),item.getWo());
             }).collect(Collectors.toList());
             date.setItem_details(itemLine);
             date.setTx_id(resLogRor.getLogTxtBum());
             output.setData(date);
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
            long t = new Date().getTime();
            b.setCreateAt(t);
            b.setActive("Y");
            DbResLogRor logRor=new DbResLogRor();
            logRor.setLogSys(b.getOrder_system());
            logRor.setLogOrderId(b.getOrder_id());
            logRor.setLogOrderNature(b.getRequest_nature());
            logRor.setSalesId(b.getSales_id());
            List<DbResLogRorDtl> logRorDtls=new ArrayList<>();
            for(int i=0;i<b.getItem_details().size();i++) {
                DbResLogRorDtl rorDtl=new DbResLogRorDtl();
                rorDtl.setDtlAction(b.getItem_details().get(i).getItem_action());
                rorDtl.setStatus(b.getItem_details().get(i).getAction_status());
                rorDtl.setDtlSkuId(Long.parseLong(b.getItem_details().get(i).getSku_id()) );
                rorDtl.setDtlQty(Long.parseLong(b.getItem_details().get(i).getQuantity()) );
                rorDtl.setDtlItemId(Long.parseLong(b.getItem_details().get(i).getItem_id()) );
                rorDtl.setDtlRepoId(Long.parseLong(b.getItem_details().get(i).getRepo_id()) );
                rorDtl.setCcc(b.getItem_details().get(i).getCcc());
                rorDtl.setWo(b.getItem_details().get(i).getWo());
                logRorDtls.add(rorDtl);
            }
            logRor.setLine(logRorDtls);
            logRor.setTxDate(b.getTx_date());
            logRor.setBizDate(b.getBiz_date());
            repo.saveAndFlush(logRor);
            //通过输入参数的order_id查询log表的logOrderId
            DbResLogRor resLogRor =repo.findDbResLogRorByLogOrderId(b.getOrder_id());
            OutputDataBean date =new OutputDataBean();
            if(resLogRor!=null){
                output.setState("success");
                output.setCode("200");
                output.setMsg("stock update successfully");
                List<OutputItemBean> itemLine=resLogRor.getLine().stream().map(item->{
                    return new OutputItemBean(item.getId().toString() ,String.valueOf(item.getDtlSkuId()),String.valueOf(item.getDtlQty()),
                            String.valueOf(item.getDtlItemId()), String.valueOf(item.getDtlRepoId()),item.getCcc(),item.getWo());
                }).collect(Collectors.toList());
                date.setItem_details(itemLine);
                date.setTx_id(resLogRor.getLogTxtBum());
                output.setData(date);
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