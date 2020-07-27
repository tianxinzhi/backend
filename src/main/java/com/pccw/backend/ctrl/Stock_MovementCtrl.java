package com.pccw.backend.ctrl;


import com.alibaba.fastjson.JSON;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.stock_movement.SearchBean;
import com.pccw.backend.entity.*;
import com.pccw.backend.repository.*;
import com.pccw.backend.util.CollectionBuilder;
import com.pccw.backend.util.Convertor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * AuthRightCtrl
 */

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST, origins = "*", allowCredentials = "false")
@RequestMapping("/stock_movement")
@Api(value="Stock_MovementCtrl",tags={"stock_movement"})
public class Stock_MovementCtrl extends BaseCtrl<DbResProcess> {

    @Autowired
    ResProcessRepository processRepo;
    @Autowired
    ResRepoRepository repoRepo;
    @Autowired
    ResLogReplRepository logReplRepo;
    @Autowired
    ResLogMgtRepository logMgtRepo;
    @Autowired
    ResSkuRepository skuRepo;

    @ApiOperation(value="搜索Stock_Movement",tags={"stock_movement"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/search")
    public JsonResult search(@RequestBody SearchBean b) {
        log.info(b.toString());
        try {
            //默认查询nature为approved的
            b.setStatus(StaticVariable.PROCESS_APPROVED_STATUS);
            //准备between需要的日期范围条件
            String[] createAt = b.getCreateAt();
            if(Objects.nonNull(createAt)){
                if(createAt.length == 2){
                    List<Object> objects = new ArrayList<>();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        long time = df.parse(createAt[0]).getTime();
                        objects.add(String.valueOf(time));
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(df.parse(createAt[1]));
                        cal.add(Calendar.DATE,1);
                        objects.add(String.valueOf(cal.getTime().getTime()));
                        String[] a = new String[objects.size()];
                        b.setCreateAt(objects.toArray(a));
                }else {
                    b.setCreateAt(null);
                }
            }
            Specification spec = Convertor.convertSpecification(b);
            Sort sort = new Sort(Sort.Direction.DESC,"id");
            List<Map> list = new ArrayList<>();

            //orderAPI
            if(StaticVariable.LOGORDERNATURE_ASSIGN.equals(b.getLogOrderNature()) ||
                    StaticVariable.LOGORDERNATURE_RETURN.equals(b.getLogOrderNature()) ||
                    StaticVariable.LOGORDERNATURE_EXCHANGE.equals(b.getLogOrderNature()) ||
                    StaticVariable.LOGORDERNATURE_ADVANCED_RESERVE.equals(b.getLogOrderNature()) ||
                    StaticVariable.LOGORDERNATURE_CANCEL_ADVANCE_RESERVE.equals(b.getLogOrderNature()) ||
                    StaticVariable.LOGORDERNATURE_ADVANCE_PICK_UP.equals(b.getLogOrderNature()) ||
                    StaticVariable.LOGORDERNATURE_STOCK_RESERVE.equals(b.getLogOrderNature()) ||
                    StaticVariable.LOGORDERNATURE_STOCK_CANCEL_RESERVE.equals(b.getLogOrderNature()) ||
                    StaticVariable.LOGORDERNATURE_TRANSFER_TO_WAREHOUSE.equals(b.getLogOrderNature())){
                Random random = new Random();
                List arrayList = new ArrayList<>();
                arrayList.add("SkuName:  iphone-x  , Qty: "+random.nextInt(10));
                Map m = CollectionBuilder.builder(new HashMap<>())
                        .put("repoName","Shop00"+random.nextInt(3))
                        .put("sku",arrayList)
                        .put("active","Y").put("createAccountName","admin")
                        .put("createAt","1579072220833").put("id",200+random.nextInt(100))
                        .put("logOrderNature",b.getLogOrderNature())
                        .put("logTxtBum","200112ISH00309471"+random.nextInt(10))
                        .put("updateAt","1579072220833").put("updateBy","1")
                        .build();
                list.add(m);
            }else{
                List<DbResProcess> res = processRepo.findAll(PageRequest.of(b.getPageIndex(),b.getPageSize(),sort)).getContent();
                for(DbResProcess p:res){
                        Map map = new HashMap();
                        //map = JSON.parseObject(JSON.toJSONString(p), Map.class);
                        map.put("createAccountName",getAccountName(p.getCreateBy()));
                        map.put("createAt",p.getCreateAt());
                        map.put("id",p.getId());
                        map.put("logTxtBum",p.getLogTxtBum());
                        map.put("logOrderNature",p.getLogOrderNature());
                        map.put("reason",p.getRemark());
                        map.put("approval",p.getProcessStatus());
                        map.put("approvalBy",getAccountName(p.getCreateBy()));
                        //movemenet根据不同的nature显示不同的详情
                        if(StaticVariable.LOGORDERNATURE_REPLENISHMENT_REQUEST.equals(p.getLogOrderNature())){
                            DbResLogRepl dbResLogRepl = logReplRepo.findDbResLogReplByLogTxtBum(p.getLogTxtBum());
                            DbResRepo fromName = repoRepo.findById(dbResLogRepl.getRepoIdFrom()).get();
                            DbResRepo toName = repoRepo.findById(dbResLogRepl.getRepoIdTo()).get();
                            //map.put("repoName","From: "+fromName+" , To: "+toName);

                            List<DbResLogReplDtl> line = dbResLogRepl.getLine();
                            for(DbResLogReplDtl dtl:line){
                                map.put("fromChannel",fromName.getRepoCode());
                                map.put("toChannel",toName.getRepoCode());
                                map.put("repoId",fromName.getId());
                                map.put("toRepoId",toName.getId());
                                DbResSku sku = skuRepo.findById(dtl.getDtlSkuId()).get();
                                map.put("sku",sku.getSkuName());
                                map.put("skuDesc",sku.getSkuDesc());
                                map.put("qty",dtl.getDtlQty());
                                map.put("fromStatus",dtl.getDtlSubin());

                            }
                            //map.put("sku",skuQtyList);
                        }else {
                            DbResLogMgt dbResLogMgt = logMgtRepo.findDbResLogMgtsByLogTxtBum(p.getLogTxtBum()).get(0);
                            map.put("staff",dbResLogMgt.getStaffNumber());
                            map.put("remark",dbResLogMgt.getRemark());
                            map.put("courier",dbResLogMgt.getCourier());
                            map.put("serial",dbResLogMgt.getSerial());
                            map.put("iccID",dbResLogMgt.getIccID());
                            map.put("imei",dbResLogMgt.getImei());
                            map.put("mobileNumber",dbResLogMgt.getMobileNumber());

                            String fromName = "";
                            if(Objects.nonNull(dbResLogMgt.getLogRepoOut()) && dbResLogMgt.getLogRepoOut() != 0){
                                DbResRepo fromName2 = repoRepo.findById(dbResLogMgt.getLogRepoOut()).get();
                                map.put("fromChannel",fromName2.getRepoCode());
                                map.put("repoId",fromName2.getId());
                            }
                            String toName = "";
                            if(Objects.nonNull(dbResLogMgt.getLogRepoIn()) && dbResLogMgt.getLogRepoIn() != 0){
                                DbResRepo toName2 = repoRepo.findById(dbResLogMgt.getLogRepoIn()).get();
                                map.put("toChannel",toName2.getRepoCode());
                                map.put("toRepoId",toName2.getId());
                            }
                            List<DbResLogMgtDtl> line = dbResLogMgt.getLine();

                            List itemList = new ArrayList<>();
                            for (int i = 0; i <line.size(); i++) {
                                StringBuilder sb = new StringBuilder();
                                String skuQtyString = "";
                                DbResSku sku = skuRepo.findById(line.get(i).getDtlSkuId()).get();
//                                    skuQtyString = sb.append("SkuName: ").append(skuName).append(" , Qty: ").append(line.get(i).getDtlQty()).append(" , From Status: ").append(line.get(i).getDtlSubin())
//                                            .append(" , To Status: ")
//                                            .append(line.get(i+1).getDtlSubin()).toString();
//                                    skuQtyList.add(skuQtyString);
                                map.put("sku",sku.getSkuName());
                                map.put("skuDesc",sku.getSkuDesc());
                                map.put("qty",line.get(i).getDtlQty());
                                map.put("fromStatus",line.get(i).getDtlSubin());

                                String itemCodes = line.get(i).getItemCode();
                                String itemCode = "";
                                if(Objects.nonNull(itemCodes)){
                                    List<String> items = Arrays.asList(itemCodes.split(","));
                                    for (int j = 0;j< items.size();j=j+1){
                                        itemList.add(skuQtyString+" , itemCode"+(j+1)+": "+items.get(j));
                                    }
                                }
                                if(StaticVariable.LOGORDERNATURE_STOCK_CATEGORY.equals(p.getLogOrderNature())) {
                                    map.put("toStatus",line.get(i+1).getDtlSubin());
                                    i++;
                                }
                            }
                            if(Objects.nonNull(itemList) && itemList.size()>0){
                                map.put("itemCode",itemList);
                            }
                        }
                        list.add(map);
                    System.out.println("res:"+map.toString());
                };
                }
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(b.getCreateAt()!=null){
                list = list.stream().filter(map -> {
                        long dte = Long.parseLong(map.get("createAt").toString());
                        if(b.getCreateAt()[0]!=null)
                            return dte >= Long.parseLong(b.getCreateAt()[0]);
                        if(b.getCreateAt()[1]!=null)
                            return dte <= Long.parseLong(b.getCreateAt()[1]);
                        else
                            return dte >= Long.parseLong(b.getCreateAt()[0]) &&  dte <= Long.parseLong(b.getCreateAt()[1]);
                }).collect(Collectors.toList());
            }

            if(b.getLogOrderNature()!=null && !b.getLogOrderNature().equals("")){
                list = list.stream().filter(map -> map.get("logOrderNature") != null && map.get("logOrderNature").toString().equals(b.getLogOrderNature())).collect(Collectors.toList());
            }
            if(b.getSkuNum()!=null && !b.getSkuNum().equals("")){
                list = list.stream().filter(map -> map.get("sku") != null && map.get("sku").toString().contains(b.getSkuNum())).collect(Collectors.toList());
            }
            if(b.getRepoId()!= null && b.getRepoId()!=0){
                list = list.stream().filter(map -> map.get("repoId") != null && Long.parseLong(map.get("repoId").toString())==b.getRepoId()).collect(Collectors.toList());
            }
            if(b.getToRepoId()!=0){
                list = list.stream().filter(map -> map.get("toRepoId")!=null && Long.parseLong(map.get("toRepoId").toString())==b.getToRepoId()).collect(Collectors.toList());
            }
            return JsonResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return JsonResult.fail(e);
        }
    }
}
