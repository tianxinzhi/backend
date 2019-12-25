package com.pccw.backend.ctrl;


import com.alibaba.fastjson.JSON;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.stock_movement.SearchBean;
import com.pccw.backend.entity.*;
import com.pccw.backend.repository.*;
import com.pccw.backend.util.Convertor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

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
            ArrayList<Map> list = new ArrayList<>();
            List<DbResProcess> res = processRepo.findAll(spec, PageRequest.of(b.getPageIndex(),b.getPageSize(),sort)).getContent();
            res.forEach(p-> {
                Map map = JSON.parseObject(JSON.toJSONString(p), Map.class);
                map.put("createAccountName",getAccountName(p.getCreateBy()));
                //movemenet根据不同的nature显示不同的详情
                if(StaticVariable.LOGORDERNATURE_REPLENISHMENT_REQUEST.equals(p.getLogOrderNature())){
                    DbResLogRepl dbResLogRepl = logReplRepo.findDbResLogReplByLogTxtBum(p.getLogTxtBum());
                    String fromName = repoRepo.findById(dbResLogRepl.getRepoIdFrom()).get().getRepoName();
                    String toName = repoRepo.findById(dbResLogRepl.getRepoIdTo()).get().getRepoName();
                    map.put("repoName","From: "+fromName+" , To: "+toName);
                    List<DbResLogReplDtl> line = dbResLogRepl.getLine();
                    ArrayList<Object> skuQtyList = new ArrayList<>();
                    for(DbResLogReplDtl dtl:line){
                        String skuName = skuRepo.findById(dtl.getDtlSkuId()).get().getSkuName();
                        String skuQtyString = "SkuName"+": "+skuName+" , Qty"+": "+dtl.getDtlQty();
                        skuQtyList.add(skuQtyString);
                    }
                    map.put("sku",skuQtyList);
                }else {
                    DbResLogMgt dbResLogMgt = logMgtRepo.findDbResLogMgtByLogTxtBum(p.getLogTxtBum());
                    String fromName = repoRepo.findById(dbResLogMgt.getLogRepoOut()).get().getRepoName();
                    if(StaticVariable.LOGORDERNATURE_STOCK_THRESHOLD.equals(p.getLogOrderNature())
                            || StaticVariable.LOGORDERNATURE_STOCK_CATEGORY.equals(p.getLogOrderNature())
                            || StaticVariable.LOGORDERNATURE_STOCK_TAKE_ADJUSTMENT.equals(p.getLogOrderNature())){
                        map.put("repoName",fromName);
                    }else{
                        String toName = repoRepo.findById(dbResLogMgt.getLogRepoIn()).get().getRepoName();
                        map.put("repoName","From: "+fromName+" , To: "+toName);
                    }
                    List<DbResLogMgtDtl> line = dbResLogMgt.getLine();
                    ArrayList<Object> skuQtyList = new ArrayList<>();
                    if(!StaticVariable.LOGORDERNATURE_STOCK_CATEGORY.equals(p.getLogOrderNature())){
                        for(DbResLogMgtDtl dtl:line){
                            String skuName = skuRepo.findById(dtl.getDtlSkuId()).get().getSkuName();
                            String skuQtyString = "";
                            if(StaticVariable.LOGORDERNATURE_STOCK_OUT_STS.equals(p.getLogOrderNature())
                                    ||StaticVariable.LOGORDERNATURE_STOCK_OUT_STW.equals(p.getLogOrderNature())){
                                if(StaticVariable.DTLSUBIN_GOOD.equals(dtl.getDtlSubin())){
                                    skuQtyString = "SkuName"+": "+skuName+" , Qty"+": "+dtl.getDtlQty();
                                }
                            }else{
                                skuQtyString = "SkuName"+": "+skuName+" , Qty"+": "+dtl.getDtlQty();
                            }
                            if(!"".equals(skuQtyString)){
                                skuQtyList.add(skuQtyString);
                            }
                        }
                    }else{
                        for (int i = 0; i <line.size(); i=i+2) {
                            String skuQtyString = "";
                            String skuName = skuRepo.findById(line.get(i).getDtlSkuId()).get().getSkuName();
                            skuQtyString = "SkuName"+": "+skuName+" , Qty"+": "+line.get(i).getDtlQty()
                                    +" , From StockCategory: "+line.get(i).getDtlSubin()+" , To StockCategory: "+line.get(i+1).getDtlSubin();
                            skuQtyList.add(skuQtyString);
                        }
                    }
                    map.put("sku",skuQtyList);
                }

                list.add(map);
            });
            return JsonResult.success(list);
        } catch (Exception e) {
            log.info(e.getMessage());
            return JsonResult.fail(e);
        }
    }
}
