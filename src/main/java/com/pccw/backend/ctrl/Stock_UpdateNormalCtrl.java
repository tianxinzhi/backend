package com.pccw.backend.ctrl;



import com.pccw.backend.bean.stock_update_normal.*;
import com.pccw.backend.entity.*;
import com.pccw.backend.repository.*;
import com.pccw.backend.util.CollectionBuilder;
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
            logRor.setLogTxtBum("2020test");
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
                    OutputItemBean outputItem= new OutputItemBean(item.getDetail_id(),item.getSku_id(),String.valueOf(item.getQuantity()),
                            item.getItem_id(), item.getRepo_id(),item.getCcc(),item.getWo());
                    list.add(outputItem);

                    DbResSku s = skuRepository.findFirst1BySkuCode(item.getSku_id());
                    DbResRepo r = repoRepository.findFirst1ByRepoCode(item.getRepo_id());
                    DbResItem i = itemRepository.findFirst1ByItemCode(item.getItem_id());
                    Long skuId = Objects.isNull(s) ? null : s.getId();
                    Long repoId = Objects.isNull(r) ? null : r.getId();
                    Long itemId = Objects.isNull(i) ? null : r.getId();

                    DbResLogRorDtl rorDtl=new DbResLogRorDtl(null,skuId,itemId,repoId,Long.parseLong(item.getQuantity()),item.getCcc(),item.getWo(),item.getDetail_id(),logRor);
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

            Map outputdata = CollectionBuilder.builder(new HashMap<>()).put("tx_id",logRor.getLogTxtBum()).put("item_details",list).build();
            Map jsonResult = CollectionBuilder.builder(new HashMap<>()).put("state", "success").put("code", "200").put("msg", "stock update successfully").put("data", outputdata).build();
            return jsonResult;
        } catch (Exception e) {
            return CollectionBuilder.builder(new HashMap<>()).put("state", "failed").put("code", "200").put("msg", "stock update failed").put("data", null).build();
        }
    }


    @ApiOperation(value="创建stock_update",tags={"stock_update"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/stock_update_ao")
    public Map createAO(@RequestBody InputBean b){
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
            ArrayList<Object> list = new ArrayList<>();
            if(Objects.nonNull(b.getItem_details()) && b.getItem_details().size() > 0){
                b.getItem_details().forEach(item -> {
                    //Output数据
                    OutputItemBean outputItem= new OutputItemBean(item.getDetail_id(),item.getSku_id(),String.valueOf(item.getQuantity()),
                            item.getItem_id(), item.getRepo_id(),item.getCcc(),item.getWo());
                    list.add(outputItem);

                    DbResSku s = skuRepository.findFirst1BySkuCode(item.getSku_id());
                    DbResRepo r = repoRepository.findFirst1ByRepoCode(item.getRepo_id());
                    DbResItem i = itemRepository.findFirst1ByItemCode(item.getItem_id());
                    Long skuId = Objects.isNull(s) ? null : s.getId();
                    Long repoId = Objects.isNull(r) ? null : r.getId();
                    Long itemId = Objects.isNull(i) ? null : r.getId();
                    DbResLogRorDtl rorDtl=new DbResLogRorDtl(null,skuId,itemId,repoId,Long.parseLong(item.getQuantity()),item.getCcc(),item.getWo(),item.getDetail_id(),logRor);
                    rorDtl.setActive("Y");
                    rorDtl.setUpdateAt(t);
                    rorDtl.setCreateAt(t);
                    rorDtl.setCreateBy(getAccount());
                    rorDtl.setUpdateBy(getAccount());
                    if(b.getRequest_nature().equals("ARS")){
                        rorDtl.setDtlAction("A");
                        rorDtl.setStatus("OST");
                        rorDtl.setDtlSubin("");
                    }else if(b.getRequest_nature().equals("CARS")){
                        //todo?
                        rorDtl.setDtlAction("D");
                        rorDtl.setStatus("OST");
                        rorDtl.setDtlSubin("");
                    }else if(b.getRequest_nature().equals("APU")){
                        rorDtl.setDtlAction("D");
                        rorDtl.setStatus("ARE");
                        rorDtl.setDtlSubin("Good");
//                        DbResLogRorDtl secRorDtl=new DbResLogRorDtl();
//                        BeanUtils.copyProperties(rorDtl,secRorDtl);
//                        secRorDtl.setDtlAction("D");
//                        secRorDtl.setStatus("AVL");
//                        secRorDtl.setDtlSubin("Good");
//                        logRorDtls.add(secRorDtl);
                    }
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
                    OutputItemBean outputItem= new OutputItemBean(d.getDetail_id(),d.getSku_id(),String.valueOf(d.getQuantity()),
                    d.getItem_id(), d.getRepo_id(),d.getCcc(),d.getWo());
                    list.add(outputItem);

                    DbResSku s = skuRepository.findFirst1BySkuCode(d.getSku_id());
                    DbResRepo r = repoRepository.findFirst1ByRepoCode(d.getRepo_id());
                    DbResItem i = itemRepository.findFirst1ByItemCode(d.getItem_id());
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
                    if("NRS".equals(b.getRequest_nature())){
                        dbResLogRorDtl.setDtlAction("A");
                        dbResLogRorDtl.setStatus("NRE");
                    }else if("CNRS".equals(b.getRequest_nature())){
                        dbResLogRorDtl.setDtlAction("A");
                        dbResLogRorDtl.setStatus("AVL");
                    }else {
                        dbResLogRorDtl.setDtlAction("D");
                        dbResLogRorDtl.setStatus("NRE");
                        dbResLogRorDtl.setLisStatus("Good");
                    }
                    logRorDtlList.add(dbResLogRorDtl);
                    if(!"NPU".equals(b.getRequest_nature())){
                        DbResLogRorDtl dbResLogRorDtl1 = new DbResLogRorDtl();
                        BeanUtils.copyProperties(dbResLogRorDtl,dbResLogRorDtl1);
                        if("NRS".equals(b.getRequest_nature())) {
                            dbResLogRorDtl1.setDtlAction("D");
                            dbResLogRorDtl1.setStatus("AVL");
                        }else if("CNRS".equals(b.getRequest_nature())){
                            dbResLogRorDtl1.setDtlAction("D");
                            dbResLogRorDtl1.setStatus("NRE");
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


}