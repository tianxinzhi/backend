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

    @ApiOperation(value = "搜索Stock_Movement", tags = {"stock_movement"}, notes = "注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/search")
    public JsonResult search(@RequestBody SearchBean b) {
        log.info(b.toString());
        try {
            List<Map> list = new ArrayList<>();

            //List<DbResProcess> res = processRepo.findAll(PageRequest.of(b.getPageIndex(),b.getPageSize(),sort)).getContent();
            //movemenet根据不同的nature显示不同的详情
//            for (DbResLogRepl dbResLogRepl : logReplRepo.findAll()) {
//
//                Map map = new HashMap();
//                //map = JSON.parseObject(JSON.toJSONString(p), Map.class);
//                map.put("createAccountName", getAccountName(dbResLogRepl.getCreateBy()));
//                map.put("createAt", dbResLogRepl.getCreateAt());
//                map.put("id", dbResLogRepl.getId());
//                map.put("logTxtBum", dbResLogRepl.getLogTxtBum());
//                map.put("logOrderNature", dbResLogRepl.getLogOrderNature());
//                map.put("reason", dbResLogRepl.getRemark());
//                map.put("approval", dbResLogRepl.getCreateAt());
//                map.put("approvalBy", getAccountName(dbResLogRepl.getCreateBy()));
//
//                DbResRepo fromName = repoRepo.findById(dbResLogRepl.getRepoIdFrom()).get();
//                DbResRepo toName = repoRepo.findById(dbResLogRepl.getRepoIdTo()).get();
//                //map.put("repoName","From: "+fromName+" , To: "+toName);
//
//                List<DbResLogReplDtl> line = dbResLogRepl.getLine();
//                for (DbResLogReplDtl dtl : line) {
//                    map.put("fromChannel", fromName.getRepoCode());
//                    map.put("toChannel", toName.getRepoCode());
//                    map.put("repoId", fromName.getId());
//                    map.put("toRepoId", toName.getId());
//                    DbResSku sku = skuRepo.findById(dtl.getDtlSkuId()).get();
//                    map.put("sku", sku.getSkuName());
//                    map.put("skuId", sku.getId());
//                    map.put("skuDesc", sku.getSkuDesc());
//                    map.put("qty", dtl.getDtlQty());
//                    map.put("fromStatus", dtl.getDtlSubin());
//
//                }
//                list.add(map);
//            }
            for (DbResLogMgt dbResLogMgt : logMgtRepo.findAll(PageRequest.of(b.getPageIndex(),b.getPageSize()))) {
                Map map = new HashMap();

                map.put("createAccountName", getAccountName(dbResLogMgt.getCreateBy()));
                map.put("createAt", dbResLogMgt.getCreateAt());
                map.put("id", dbResLogMgt.getId());
                map.put("logTxtBum", dbResLogMgt.getLogTxtBum());
                map.put("logOrderNature", dbResLogMgt.getLogOrderNature());
                map.put("reason", dbResLogMgt.getRemark());
                map.put("approval", dbResLogMgt.getApproval());
                map.put("approvalBy", dbResLogMgt.getApprovalBy());

                map.put("staff", dbResLogMgt.getStaffNumber());
                map.put("remark", dbResLogMgt.getRemark());
                map.put("courier", dbResLogMgt.getCourier());
                map.put("serial", dbResLogMgt.getSerial());
                map.put("iccID", dbResLogMgt.getIccID());
                map.put("imei", dbResLogMgt.getImei());
                map.put("mobileNumber", dbResLogMgt.getMobileNumber());
                map.put("sourceSystem", dbResLogMgt.getSourceSystem());
                map.put("txnHeader", dbResLogMgt.getSourceTxnHeader());
                map.put("txnLine", dbResLogMgt.getSourceTxnLine());

                if ( Objects.nonNull(dbResLogMgt.getLogRepoOut()) && dbResLogMgt.getLogRepoOut() != 0 ) {
                    DbResRepo fromName2 = repoRepo.findById(dbResLogMgt.getLogRepoOut()).get();
                    map.put("fromChannel", fromName2.getRepoCode());
                    map.put("repoId", fromName2.getId());
                }
                if ( Objects.nonNull(dbResLogMgt.getLogRepoIn()) && dbResLogMgt.getLogRepoIn() != 0 ) {
                    DbResRepo toName2 = repoRepo.findById(dbResLogMgt.getLogRepoIn()).get();
                    map.put("toChannel", toName2.getRepoCode());
                    map.put("toRepoId", toName2.getId());
                }
                List<DbResLogMgtDtl> line = dbResLogMgt.getLine();

                List itemList = new ArrayList<>();
                for (int i = 0; i < line.size(); i++) {
                    String skuQtyString = "";
                    DbResSku sku = skuRepo.findById(line.get(i).getDtlSkuId()).get();
                    map.put("sku", sku.getSkuName());
                    map.put("skuId", sku.getId());
                    map.put("skuDesc", sku.getSkuDesc());
                    map.put("qty", line.get(i).getDtlQty());
                    map.put("fromStatus", line.get(i).getDtlSubin());

                    String itemCodes = line.get(i).getItemCode();

                   if ( Objects.nonNull(itemCodes) ) {
                        List<String> items = Arrays.asList(itemCodes.split(","));
                        for (int j = 0; j < items.size(); j = j + 1) {
                            itemList.add(skuQtyString + " , itemCode" + (j + 1) + ": " + items.get(j));
                        }
                    }
                    if ( StaticVariable.LOGORDERNATURE_STOCK_CATEGORY.equals(dbResLogMgt.getLogOrderNature()) ) {
                        map.put("toStatus", line.get(i + 1).getDtlSubin());
                        i++;
                    }
                }
                if ( Objects.nonNull(itemList) && itemList.size() > 0 ) {
                    map.put("itemCode", itemList);
                }
                list.add(map);
            }

            //搜索filter
            if ( b.getCreateAt() != null ) {
                list = list.stream().filter(map -> {
                    long dte = Long.parseLong(map.get("createAt").toString());
                    if ( b.getCreateAt()[0] != null )
                        return dte >= Long.parseLong(b.getCreateAt()[0]);
                    if ( b.getCreateAt()[1] != null )
                        return dte <= Long.parseLong(b.getCreateAt()[1]);
                    else
                        return dte >= Long.parseLong(b.getCreateAt()[0]) && dte <= Long.parseLong(b.getCreateAt()[1]);
                }).collect(Collectors.toList());
            }

            if ( b.getLogOrderNature() != null && !b.getLogOrderNature().equals("") ) {
                list = list.stream().filter(map -> map.get("logOrderNature") != null && map.get("logOrderNature").toString().equals(b.getLogOrderNature())).collect(Collectors.toList());
            }
            if ( b.getSkuNum() != null && b.getSkuNum().size() > 0 ) {
                list = list.stream().filter(map ->{
                    for (String s : b.getSkuNum()) {
                        if(map.get("skuId") != null && s.equals(map.get("skuId").toString())){
                            return   true;
                        }
                    }
                    return false;
                }).collect(Collectors.toList());
            }
            if ( b.getRepoId() != null && b.getRepoId() != 0 ) {
                list = list.stream().filter(map -> map.get("repoId") != null && Long.parseLong(map.get("repoId").toString()) == b.getRepoId()).collect(Collectors.toList());
            }
            if ( b.getToRepoId() != 0 ) {
                list = list.stream().filter(map -> map.get("toRepoId") != null && Long.parseLong(map.get("toRepoId").toString()) == b.getToRepoId()).collect(Collectors.toList());
            }
            Collections.sort(list, (o1, o2) -> {
                if ( o1.get("sku") != null && o2.get("sku") != null )
                    return o2.get("sku").toString().toUpperCase().compareTo(o1.get("sku").toString().toUpperCase());
                        return 0;
                    }
            );
            return JsonResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return JsonResult.fail(e);
        }
    }
}
