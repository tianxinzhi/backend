package com.pccw.backend.ctrl;


import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.stock_movement.SearchBean;
import com.pccw.backend.entity.*;
import com.pccw.backend.repository.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
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

    private EntityManager entityManager;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public JsonResult getHistoryInfo(String adjustReasonId,
                                         String startDate,
                                         String remark,
                                         Pageable pageable) {

        StringBuffer dataBuffer = new StringBuffer(
                "select * from res_log_mgt WHERE 1 = 1");
        StringBuffer countBuffer = new StringBuffer(
                "select count(*) from res_log_mgt WHERE 1 = 1");

        StringBuffer paramBuffer = new StringBuffer();

        if (adjustReasonId != null) {
            paramBuffer.append(" AND adjust_reason_id = '" + adjustReasonId + "'");
        }
        if(startDate != null) {
            paramBuffer
                    .append(" AND CREATE_at like '%" + startDate + "%'");
        }
        if (remark != null && !remark.equals("")) {
            paramBuffer.append(" AND remark like '% "+remark+"%'");
        }

        StringBuffer orderBuffer = new StringBuffer(
                " order by CREATE_at desc");

        String dataSql = (dataBuffer.append(paramBuffer).append(orderBuffer))
                .toString();
        String countSql = (countBuffer.append(paramBuffer)).toString();

        System.out.println("{} dataSql= " + dataSql);
        System.out.println("{} countSql= " + countSql);

        Query dataQuery = entityManager.createNativeQuery(dataSql);
        Query countQuery = entityManager.createNativeQuery(countSql);

        dataQuery.setFirstResult((int) pageable.getOffset());
        dataQuery.setMaxResults(pageable.getPageSize());
        BigDecimal count = (BigDecimal) countQuery.getSingleResult();
        Long total = count.longValue();
        List<Object[]> content = total > pageable.getOffset() ? dataQuery
                .getResultList() : Collections.emptyList();
        return JsonResult.success(new PageImpl<>(content, pageable, total).getContent());
        // return null;
    }

    @ApiOperation(value = "搜索Stock_Movement", tags = {"stock_movement"}, notes = "注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/search")
    public JsonResult search(@RequestBody SearchBean b) {
        log.info(b.toString());
        try {
            List<Map> list = new ArrayList<>();
            for (DbResLogMgt dbResLogMgt : logMgtRepo.findAll()) {
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

            long[] count = {logMgtRepo.count()};
            //搜索filter
            if ( b.getCreateAt() != null ) {
                list = list.stream().filter(map -> {
                    long dte = Long.parseLong(map.get("createAt").toString());
                    if(dte >= Long.parseLong(b.getCreateAt()[0]) && dte <= Long.parseLong(b.getCreateAt()[1]))
                        return true;
                    else {
                        count[0] -= 1;
                        return false;
                    }
                }).collect(Collectors.toList());
            }

            if ( b.getLogOrderNature() != null && !b.getLogOrderNature().equals("") ) {
                list = list.stream().filter(map -> {
                    if(map.get("logOrderNature") != null && map.get("logOrderNature").toString().equals(b.getLogOrderNature())){
                        return true;
                    } else {
                        count[0] --;
                        return false;
                    }
                }).collect(Collectors.toList());
            }
            if ( b.getSkuNum() != null && b.getSkuNum().size() > 0 ) {
                list = list.stream().filter(map ->{
                    for (String s : b.getSkuNum()) {
                        if(map.get("skuId") != null && s.equals(map.get("skuId").toString())){
                            return true;
                        }
                    }
                    count[0] --;
                    return false;
                }).collect(Collectors.toList());
            }
            if ( b.getRepoId() != null && b.getRepoId() != 0 ) {
                list = list.stream().filter(map -> {
                    if(map.get("repoId") != null && Long.parseLong(map.get("repoId").toString()) == b.getRepoId()){
                        return true;
                    } else {
                        count[0] --;
                        return false;
                    }
                }).collect(Collectors.toList());
            }
            if ( b.getToRepoId() != 0 ) {
                list = list.stream().filter(map -> {
                    if(map.get("toRepoId") != null && Long.parseLong(map.get("toRepoId").toString()) == b.getToRepoId()){
                        return true;
                    } else {
                        count[0] --;
                        return false;
                    }
                } ).collect(Collectors.toList());
            }
            Collections.sort(list, (o1, o2) -> {
                if ( o1.get("sku") != null && o2.get("sku") != null )
                    return o2.get("sku").toString().toUpperCase().compareTo(o1.get("sku").toString().toUpperCase());
                        return 0;
                    }
            );
//            (0,10) (1,10)
 //           b.getPageIndex()*b.getPageSize(),(b.getPageIndex()+1)*b.getPageSize();
            int begin = b.getPageIndex()*b.getPageSize();
            int end = (b.getPageIndex()+1)*b.getPageSize()>list.size()? list.size() : (b.getPageIndex()+1)*b.getPageSize();
            for (int i=list.size()-1;i>=0;i--) {
                Map map = list.get(i);
                if(list.indexOf(map)<begin||
                    list.indexOf(map)>=end){
                    list.remove(map);
                }
            }
            return JsonResult.success(list,count[0]);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return JsonResult.fail(e);
        }
    }

}
