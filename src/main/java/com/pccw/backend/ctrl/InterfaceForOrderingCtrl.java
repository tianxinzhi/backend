package com.pccw.backend.ctrl;


import com.pccw.backend.bean.stock_update_normal.InputBean;
import com.pccw.backend.bean.stock_update_normal.InputItemBean;
import com.pccw.backend.bean.stock_update_normal.OutputItemBean;
import com.pccw.backend.bean.stock_update_normal.SearchBean;
import com.pccw.backend.entity.*;
import com.pccw.backend.repository.*;
import com.pccw.backend.util.CollectionBuilder;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
@RestController
@RequestMapping("/api/external/ppos/v1")
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
public class InterfaceForOrderingCtrl extends BaseCtrl<DbResLogRor> {

    @Autowired
    private ResLogRorRepository repo;

    @Autowired
    ResSkuRepoRepository skuRepoRepository;

    @Autowired
    ResRepoRepository repoRepository;

    @Autowired
    Process_ProcessCtrl processProcessCtrl;

    @Autowired
    ResSkuRepository skuRepository;

    @Autowired
    ResItemRepository itemRepository;


    @ApiOperation(value="创建stock_update",tags={"stock_update"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/stock_update")
    public Map create(@RequestBody InputBean b){
        try {
            //输入验证
            long t = new Date().getTime();
            DbResLogRor logRor= new DbResLogRor(null,b.getOrder_system(),b.getOrder_id(),null,"N",b.getSales_id(),b.getTx_date(),b.getBiz_date(),null);
            logRor.setLogOrderNature(b.getRequest_nature());
            logRor.setRemark(b.getRemarks());
            logRor.setLogType("O");
            logRor.setActive("Y");
            logRor.setUpdateAt(t);
            logRor.setCreateAt(t);
            logRor.setCreateBy(getAccount());
            logRor.setUpdateBy(getAccount());
            List<DbResLogRorDtl> logRorDtls=new ArrayList<>();
            ArrayList<Object> list = new ArrayList<>();
            if(Objects.nonNull(b.getItem_details()) && b.getItem_details().size() > 0){
                b.getItem_details().forEach(item -> {
                    //Output数据
                    OutputItemBean outputItem= new OutputItemBean(item.getDetail_id(),item.getSku_code(),String.valueOf(item.getQuantity()),
                            item.getItem_code(), item.getRepo_id(),item.getCcc(),item.getWo());
                    list.add(outputItem);

                    DbResSku s = skuRepository.findFirst1BySkuCode(item.getSku_code());
                    DbResRepo r = repoRepository.findFirst1ByRepoCode(item.getRepo_id());
                    DbResItem i = itemRepository.findFirst1ByItemCode(item.getItem_code());
                    Long skuId = Objects.isNull(s) ? null : s.getId();
                    Long repoId = Objects.isNull(r) ? null : r.getId();
                    Long itemId = Objects.isNull(i) ? null : r.getId();

                    DbResLogRorDtl rorDtl=new DbResLogRorDtl(null,skuId,itemId,repoId,Long.parseLong(item.getQuantity()),item.getCcc(),item.getWo(),item.getDetail_id(),logRor);
                    rorDtl.setActive("Y");
                    rorDtl.setUpdateAt(t);
                    rorDtl.setCreateAt(t);
                    rorDtl.setCreateBy(getAccount());
                    rorDtl.setUpdateBy(getAccount());
                    String dnNum = "";
                    if(b.getRequest_nature().equals("ASG")){
                        rorDtl.setDtlAction("D");
                        rorDtl.setStatus("AVL");
                        rorDtl.setDtlSubin("Good");
                        dnNum = "S";
                    }else if(b.getRequest_nature().equals("RET")){
                        rorDtl.setDtlAction("A");
                        rorDtl.setStatus("FAU");
                        rorDtl.setDtlSubin("Faulty");
                        dnNum = "E";
                    }else if(b.getRequest_nature().equals("EXC")){
                        rorDtl.setDtlAction("A");
                        rorDtl.setStatus("FAU");
                        rorDtl.setDtlSubin("Faulty");
                        dnNum = "X";
                        DbResLogRorDtl secRorDtl=new DbResLogRorDtl();
                        BeanUtils.copyProperties(rorDtl,secRorDtl);
                        secRorDtl.setDtlAction("D");
                        secRorDtl.setStatus("AVL");
                        secRorDtl.setDtlSubin("Good");
                        String txtNum = new SimpleDateFormat("yyMMdd hhmmss").format(new Date()).replace(" ", dnNum+rorDtl.getDtlRepoId());
                        secRorDtl.setLogTxtBum(txtNum);
                        logRorDtls.add(secRorDtl);
                    }
                    String txtNum = new SimpleDateFormat("yyMMdd hhmmss").format(new Date()).replace(" ", dnNum+rorDtl.getDtlRepoId());
                    rorDtl.setLogTxtBum(txtNum);
                    logRor.setLogTxtBum(txtNum);
                    logRorDtls.add(rorDtl);
                });
            }
            logRor.setLine(logRorDtls);
            repo.saveAndFlush(logRor);

            Map outputdata = CollectionBuilder.builder(new HashMap<>()).put("item_details",list).put("tx_id",logRor.getLogTxtBum()).build();
            Map jsonResult = CollectionBuilder.builder(new HashMap<>()).put("state", "success").put("code", "200").put("msg", "stock update successfully").put("data", outputdata).build();
            return jsonResult;
        } catch (Exception e) {
            return CollectionBuilder.builder(new HashMap<>()).put("state", "failed").put("code", "200").put("msg", e.getMessage()).put("data", null).build();
        }
    }


    @ApiOperation(value="创建stock_update",tags={"stock_update"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/stock_update_ao")
    public Map createAO(@RequestBody InputBean b){
        try {
            //输入验证
            long t = new Date().getTime();
            DbResLogRor logRor= new DbResLogRor(null,b.getOrder_system(),b.getOrder_id(),null,"N",b.getSales_id(),b.getTx_date(),b.getBiz_date(),null);
            logRor.setLogOrderNature(b.getRequest_nature());
            logRor.setRemark(b.getRemarks());
            logRor.setLogType("O");
            logRor.setActive("Y");
            logRor.setUpdateAt(t);
            logRor.setCreateAt(t);
            logRor.setCreateBy(getAccount());
            logRor.setUpdateBy(getAccount());
            List<DbResLogRorDtl> logRorDtls=new ArrayList<>();
            ArrayList<Object> list = new ArrayList<>();
            if(Objects.nonNull(b.getItem_details()) && b.getItem_details().size() > 0){
                b.getItem_details().forEach(item -> {
                    //Output数据
                    OutputItemBean outputItem= new OutputItemBean(item.getDetail_id(),item.getSku_code(),String.valueOf(item.getQuantity()),
                            item.getItem_code(), item.getRepo_id(),item.getCcc(),item.getWo());
                    list.add(outputItem);

                    DbResSku s = skuRepository.findFirst1BySkuCode(item.getSku_code());
                    DbResRepo r = repoRepository.findFirst1ByRepoCode(item.getRepo_id());
                    DbResItem i = itemRepository.findFirst1ByItemCode(item.getItem_code());
                    Long skuId = Objects.isNull(s) ? null : s.getId();
                    Long repoId = Objects.isNull(r) ? null : r.getId();
                    Long itemId = Objects.isNull(i) ? null : r.getId();
                    DbResLogRorDtl rorDtl=new DbResLogRorDtl(null,skuId,itemId,repoId,Long.parseLong(item.getQuantity()),item.getCcc(),item.getWo(),item.getDetail_id(),logRor);
                    rorDtl.setActive("Y");
                    rorDtl.setUpdateAt(t);
                    rorDtl.setCreateAt(t);
                    rorDtl.setCreateBy(getAccount());
                    rorDtl.setUpdateBy(getAccount());
                    String dnNum = "";
                    if(b.getRequest_nature().equals("ARS")){
                        rorDtl.setDtlAction("A");
                        rorDtl.setStatus("OST");
                        rorDtl.setDtlSubin("");
                        dnNum = "T";
                    }else if(b.getRequest_nature().equals("CARS")){
                        rorDtl.setDtlAction("D");
                        rorDtl.setStatus("OST");
                        rorDtl.setDtlSubin("");
                        dnNum = "W";
                    }else if(b.getRequest_nature().equals("CARSW")){
                        rorDtl.setDtlAction("A");
                        rorDtl.setStatus("AVL");
                        rorDtl.setDtlSubin("");
                        dnNum = "Y";
                        DbResLogRorDtl secRorDtl=new DbResLogRorDtl();
                        BeanUtils.copyProperties(rorDtl,secRorDtl);
                        secRorDtl.setDtlAction("D");
                        secRorDtl.setStatus("ARE");
                        String txtNum = new SimpleDateFormat("yyMMdd hhmmss").format(new Date()).replace(" ", dnNum+rorDtl.getDtlRepoId());
                        secRorDtl.setLogTxtBum(txtNum);
                        logRorDtls.add(secRorDtl);

                    }else if(b.getRequest_nature().equals("APU")){
                        rorDtl.setDtlAction("D");
                        rorDtl.setStatus("ARE");
                        rorDtl.setDtlSubin("Good");
                        dnNum = "U";
                    }
                    String txtNum = new SimpleDateFormat("yyMMdd hhmmss").format(new Date()).replace(" ", dnNum+rorDtl.getDtlRepoId());
                    rorDtl.setLogTxtBum(txtNum);
                    logRor.setLogTxtBum(txtNum);
                    logRorDtls.add(rorDtl);
                });
            }
            logRor.setLine(logRorDtls);
            repo.saveAndFlush(logRor);

            Map outputdata = CollectionBuilder.builder(new HashMap<>()).put("tx_id",logRor.getLogTxtBum()).put("item_details",list).build();
            Map jsonResult = CollectionBuilder.builder(new HashMap<>()).put("state", "success").put("code", "200").put("msg", "stock update successfully").put("data", outputdata).build();
            return jsonResult;
        } catch (Exception e) {
            return CollectionBuilder.builder(new HashMap<>()).put("state", "failed").put("code", "200").put("msg", "stock update failed").put("data", null).build();
        }
    }

    @ApiOperation(value="创建Normal_Reserve",tags={"Normal_Reserve"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/stock_update_nr")
    public Map createNR(@RequestBody InputBean b){
        try {
            ArrayList<Object> list = new ArrayList<>();

            //处理头表数据
            long time = new Date().getTime();
            DbResLogRor dbResLogRor = new DbResLogRor(null,b.getOrder_system(),b.getOrder_id(),null,"N",b.getSales_id(),b.getTx_date(),b.getBiz_date(),null);
            dbResLogRor.setLogTxtBum("NR000");
            dbResLogRor.setActive("Y");
            dbResLogRor.setUpdateAt(time);
            dbResLogRor.setCreateAt(time);
            dbResLogRor.setCreateBy(getAccount());
            dbResLogRor.setUpdateBy(getAccount());
//            dbResLogRor.setCcc();
//            dbResLogRor.setWo();
            dbResLogRor.setLogOrderNature(b.getRequest_nature());
            dbResLogRor.setLogType("o");
//            dbResLogRor.setStatus();
            dbResLogRor.setRemark(b.getRemarks());
            //处理行表数据
            List<DbResLogRorDtl> logRorDtlList = new ArrayList<>();
            List<InputItemBean> item_details = b.getItem_details();
            if(Objects.nonNull(item_details) && item_details.size() > 0){
                item_details.forEach(d->{
                    //Output数据
                    OutputItemBean outputItem= new OutputItemBean(d.getDetail_id(),d.getSku_code(),String.valueOf(d.getQuantity()),
                    d.getItem_code(), d.getRepo_id(),d.getCcc(),d.getWo());
                    list.add(outputItem);

                    DbResSku s = skuRepository.findFirst1BySkuCode(d.getSku_code());
                    DbResRepo r = repoRepository.findFirst1ByRepoCode(d.getRepo_id());
                    DbResItem i = itemRepository.findFirst1ByItemCode(d.getItem_code());
                    Long skuId = Objects.isNull(s) ? null : s.getId();
                    Long repoId = Objects.isNull(r) ? null : r.getId();
                    Long itemId = Objects.isNull(i) ? null : r.getId();
                    DbResLogRorDtl dbResLogRorDtl = new DbResLogRorDtl(null,
                            skuId,itemId,repoId,Long.parseLong(d.getQuantity()),d.getCcc(),d.getWo(),d.getDetail_id(),dbResLogRor);
                    dbResLogRorDtl.setActive("Y");
                    dbResLogRorDtl.setCreateAt(time);
                    dbResLogRorDtl.setCreateBy(getAccount());
                    dbResLogRorDtl.setUpdateAt(time);
                    dbResLogRorDtl.setUpdateBy(getAccount());
                    String dnNum = "";
                    if("NRS".equals(b.getRequest_nature())){
                        dbResLogRorDtl.setDtlAction("A");
                        dbResLogRorDtl.setStatus("NRE");
                        dnNum = "N";
                    }else if("CNRS".equals(b.getRequest_nature())){
                        dbResLogRorDtl.setDtlAction("A");
                        dbResLogRorDtl.setStatus("AVL");
                        dnNum = "F";
                    }else {
                        dbResLogRorDtl.setDtlAction("D");
                        dbResLogRorDtl.setStatus("NRE");
                        dbResLogRorDtl.setLisStatus("Good");
                        dnNum = "K";
                    }
                    String txtNum = new SimpleDateFormat("yyMMdd hhmmss").format(new Date()).replace(" ", dnNum+dbResLogRorDtl.getDtlRepoId());
                    dbResLogRorDtl.setLogTxtBum(txtNum);
                    dbResLogRor.setLogTxtBum(txtNum);
                    logRorDtlList.add(dbResLogRorDtl);
                    if(!"NPU".equals(b.getRequest_nature())){
                        DbResLogRorDtl dbResLogRorDtl1 = new DbResLogRorDtl();
                        BeanUtils.copyProperties(dbResLogRorDtl,dbResLogRorDtl1);
                        if("NRS".equals(b.getRequest_nature())) {
                            dbResLogRorDtl1.setDtlAction("D");
                            dbResLogRorDtl1.setStatus("AVL");
                            dbResLogRorDtl1.setLogTxtBum(txtNum);
                        }else if("CNRS".equals(b.getRequest_nature())){
                            dbResLogRorDtl1.setDtlAction("D");
                            dbResLogRorDtl1.setStatus("NRE");
                            dbResLogRorDtl1.setLogTxtBum(txtNum);
                        }
                        logRorDtlList.add(dbResLogRorDtl1);
                    }
                });
            }
            dbResLogRor.setLine(logRorDtlList);
            repo.saveAndFlush(dbResLogRor);

            Map outputdata = CollectionBuilder.builder(new HashMap<>()).put("tx_id",dbResLogRor.getLogTxtBum()).put("item_details",list).build();
            Map jsonResult = CollectionBuilder.builder(new HashMap<>()).put("state", "success").put("code", "200").put("msg", "stock update successfully").put("data", outputdata).build();
            return jsonResult;
        } catch (Exception e) {
            return CollectionBuilder.builder(new HashMap<>()).put("state", "failed").put("code", "200").put("msg", "stock update failed").put("data", null).build();
        }
    }

    @ApiOperation(value="查询Level_Enquiry",tags={"Level_Enquiry"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/stock_level")
    public Map levelEnquiry(@RequestBody SearchBean b){
        try {
            DbResSku s = skuRepository.findFirst1BySkuCode(b.getSku_code());
            DbResRepo r = repoRepository.findFirst1ByRepoCode(b.getRepo_id());
            DbResItem i = itemRepository.findFirst1ByItemCode(b.getItem_code());
            Long skuId = Objects.isNull(s) ? null : s.getId();
            Long repoId = Objects.isNull(r) ? null : r.getId();
            Long itemId = Objects.isNull(i) ? null : r.getId();
            Long qty = 0l;
            if(Objects.isNull(itemId)){
                qty = skuRepoRepository.findQtyByRepoAndSkuAndType(repoId, skuId, 3l);
            }else {
                qty = skuRepoRepository.findQtyByRepoAndSkuAndItemAndType(repoId, skuId, itemId, 3l);
            }
            Map outputdata = CollectionBuilder.builder(new HashMap<>()).put("tx_id","").put("repo_id",b.getRepo_id()).put("quantity",qty).put("sku_code",b.getSku_code()).put("item_code",b.getItem_code()).build();
            Map jsonResult = CollectionBuilder.builder(new HashMap<>()).put("state", "success").put("code", "200").put("msg", "stock level enquiry successfully").put("data", outputdata).build();
            return jsonResult;
        } catch (Exception e) {
            return CollectionBuilder.builder(new HashMap<>()).put("state", "failed").put("code", "200").put("msg", "stock level enquiry failed").put("data", null).build();
        }
    }
}