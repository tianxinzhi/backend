package com.pccw.backend.ctrl;

import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.stock_reservation.CreateBean;
import com.pccw.backend.bean.stock_reservation.EditBean;
import com.pccw.backend.bean.stock_reservation.SearchBean;
import com.pccw.backend.entity.*;
import com.pccw.backend.repository.*;
import com.pccw.backend.util.Convertor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/stock_reservation")
@Api(value="Stock_ReservationCtrl",tags={"stock_reservation"})
public class Stock_ReservationCtrl extends BaseCtrl<DbResReservation> {

    @Autowired
    ResLogMgtRepository logMgtRepository;
    @Autowired
    ResSkuRepoRepository skuRepoRepository;
    @Autowired
    ResReservationRepository reservationRepository;
    @Autowired
    ResSkuRepository skuRepository;
    @Autowired
    ResRepoRepository repoRepository;
    @Autowired
    ResStockTakeRepository stockTakeRepository;

    @Autowired
    HttpServletResponse response;

    private String inPath =  "excel/stock_take.xlsx";
    private String outPath = System.getProperty("user.dir")+"/data/stock_take.xls";

    @ApiOperation(value="预留",tags={"stock_reservation"},notes="查询")
    @RequestMapping("/search")
    public JsonResult search(@RequestBody SearchBean bean) {
        try {
            System.out.println(bean.toString());
            Specification<DbResReservation> spec = Convertor.<DbResReservation>convertSpecification(bean);
            List<DbResReservation> list = reservationRepository.findAll();
            list = list.stream().filter(b -> b.getActive().equals("Y")).collect(Collectors.toList());
            if(bean.getCustomerType()!=null&&!bean.getCustomerType().equals("")){
                list = list.stream().filter(b -> bean.getCustomerType().contains(b.getCustomerType())).collect(Collectors.toList());
            }
            if(bean.getPaymentStatus()!=null&&!bean.getPaymentStatus().equals("")){
                list = list.stream().filter(b -> b.getPaymentStatus().contains(bean.getPaymentStatus())).collect(Collectors.toList());
            }
            if(bean.getRepoId()!=null){
                list = list.stream().filter(b -> b.getRepoId().toString().contains(bean.getRepoId()+"")).collect(Collectors.toList());
            }
            if(bean.getSku()!=null){
                list = list.stream().filter(b -> bean.getSku().toString().contains(b.getSkuId()+"")).collect(Collectors.toList());
            }
            return JsonResult.success(list);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="预留",tags={"stock_reservation"},notes="新增")
    @RequestMapping("/create")
    public JsonResult create(@RequestBody CreateBean bean) {
        try {
            //保存 reservation
            bean.setLogTxtBum(genTranNum(new Date(),"RV",repoRepository.findById(bean.getRepoId()).get().getRepoCode()));
            this.create(reservationRepository,DbResReservation.class,bean);
            DbResSku sku = new DbResSku();sku.setId(bean.getSkuId());
            DbResRepo repo = new DbResRepo();repo.setId(bean.getRepoId());
            DbResStockType stockType = new DbResStockType();stockType.setId(3L);
            DbResSkuRepo skuRepo = skuRepoRepository.findDbResSkuRepoByRepoAndSkuAndStockType(repo, sku, stockType);
            long time = System.currentTimeMillis();
            //扣减available qty加到reservered qty
            if(skuRepo != null){
                DbResStockType stockType2 = new DbResStockType();stockType2.setId(4L);
                DbResSkuRepo skuRepo2 = skuRepoRepository.findDbResSkuRepoByRepoAndSkuAndStockType(repo, sku, stockType2);
                //存在reserved，修改qty
                if(skuRepo2 != null){
                    skuRepo2.setQty(skuRepo2.getQty()+bean.getQty());
                    skuRepoRepository.saveAndFlush(skuRepo2);
                } else {
                    //不存在reserved，新增一条
                    DbResSkuRepo value = new DbResSkuRepo();
                    value.setRepo(repo);
                    value.setSku(sku);
                    value.setStockType(stockType2);
                    value.setQty(bean.getQty());
                    value.setRemark(bean.getRemark());
                    value.setCreateAt(time);
                    value.setUpdateAt(time);
                    value.setCreateBy(getAccount());
                    value.setUpdateBy(getAccount());
                    value.setActive("Y");
                    skuRepoRepository.saveAndFlush(value);
                }
                //扣除对应available的qty
                skuRepo.setQty(skuRepo.getQty()-bean.getQty());
                skuRepoRepository.saveAndFlush(skuRepo);
            }
            //插入日志
            DbResLogMgt logMgt = new DbResLogMgt();
            logMgt.setLogRepoOut(bean.getRepoId());
            logMgt.setLogTxtBum(bean.getLogTxtBum());
            logMgt.setLogType(StaticVariable.LOGTYPE_MANAGEMENT);
            logMgt.setLogOrderNature(StaticVariable.LOGORDERNATURE_STOCK_RESERVE);
            logMgt.setStatus(StaticVariable.STATUS_WAITING);
            logMgt.setStaffNumber(bean.getStaffId());
            logMgt.setReservationId(reservationRepository.findAll().stream().max(Comparator.comparing(DbResReservation::getId)).get().getId());
            logMgt.setRemark(bean.getRemark());
            logMgt.setCreateAt(time);
            logMgt.setUpdateAt(time);
            logMgt.setCreateBy(getAccount());
            logMgt.setUpdateBy(getAccount());
            logMgt.setActive("Y");

            List<DbResLogMgtDtl> line = new LinkedList<>();
            DbResLogMgtDtl dtl = new DbResLogMgtDtl();
            dtl.setDtlRepoId(bean.getRepoId());
            dtl.setDtlSkuId(bean.getSkuId());
            dtl.setDtlQty(bean.getQty());
            dtl.setLogTxtBum(bean.getLogTxtBum());
            dtl.setStatus(StaticVariable.STATUS_RESERVED);
            dtl.setLisStatus(StaticVariable.LISSTATUS_WAITING);
            dtl.setDtlAction(StaticVariable.DTLACTION_ADD);
            dtl.setDtlSubin(StaticVariable.DTLSUBIN_RESERVED);
            dtl.setCreateAt(time);
            dtl.setUpdateAt(time);
            dtl.setCreateBy(getAccount());
            dtl.setUpdateBy(getAccount());
            dtl.setActive("Y");

            DbResLogMgtDtl dtl2 = new DbResLogMgtDtl();
            dtl2.setDtlRepoId(bean.getRepoId());
            dtl2.setDtlSkuId(bean.getSkuId());
            dtl2.setDtlQty(bean.getQty());
            dtl2.setLogTxtBum(bean.getLogTxtBum());
            dtl2.setStatus(StaticVariable.STATUS_AVAILABLE);
            dtl2.setLisStatus(StaticVariable.LISSTATUS_WAITING);
            dtl2.setDtlAction(StaticVariable.DTLACTION_DEDUCT);
            dtl2.setDtlSubin(StaticVariable.DTLSUBIN_AVAILABLE);
            dtl2.setCreateAt(time);
            dtl2.setUpdateAt(time);
            dtl2.setCreateBy(getAccount());
            dtl2.setUpdateBy(getAccount());
            dtl2.setActive("Y");

            line.add(dtl);
            line.add(dtl2);
            logMgt.setLine(line);
            logMgtRepository.saveAndFlush(logMgt);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
        return JsonResult.success(Arrays.asList());
    }

    @ApiOperation(value="删除",tags={"stock_reservation"},notes="")
    @RequestMapping(method = RequestMethod.POST,value = "/delete")
    public JsonResult delete(@RequestBody EditBean bean) {
        try {
            System.out.println(bean);
            DbResReservation dbResReservation = reservationRepository.findById(bean.getId()).get();
            dbResReservation.setActive("N");
            reservationRepository.saveAndFlush(dbResReservation);

            DbResSku sku = new DbResSku();sku.setId(bean.getSkuId());
            DbResRepo repo = new DbResRepo();repo.setId(bean.getRepoId());
            DbResStockType stockType = new DbResStockType();stockType.setId(3L);
            DbResSkuRepo skuRepo = skuRepoRepository.findDbResSkuRepoByRepoAndSkuAndStockType(repo, sku, stockType);
            //删除时，扣减之前的reserved qty 加到available qty
            if(skuRepo != null){
                skuRepo.setQty(skuRepo.getQty()+dbResReservation.getQty());
                skuRepoRepository.saveAndFlush(skuRepo);
            }
            DbResStockType stockType2 = new DbResStockType();stockType2.setId(4L);
            DbResSkuRepo skuRepo2 = skuRepoRepository.findDbResSkuRepoByRepoAndSkuAndStockType(repo, sku, stockType2);
            //扣减之前reserved的qty
            if(skuRepo2 != null){
                skuRepo2.setQty(skuRepo2.getQty()-dbResReservation.getQty());
                skuRepoRepository.saveAndFlush(skuRepo2);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
        return JsonResult.success(Arrays.asList());
    }

    @ApiOperation(value="修改",tags={"stock_reservation"},notes="")
    @RequestMapping(method = RequestMethod.POST,value = "/edit")
    public JsonResult edit(@RequestBody EditBean bean) {
        try {
            System.out.println(bean.toString());
            DbResReservation dbResReservation = reservationRepository.findById(bean.getId()).get();
            //没有修改repo和sku，即扣减之前reserved的qty，添加新的reserved qty
            if(bean.getRepoId() == dbResReservation.getRepoId() &&
                bean.getSkuId() == dbResReservation.getSkuId()){
                DbResSku sku = new DbResSku();sku.setId(bean.getSkuId());
                DbResRepo repo = new DbResRepo();repo.setId(bean.getRepoId());
                DbResStockType stockType = new DbResStockType();stockType.setId(3L);

                DbResSkuRepo skuRepo = skuRepoRepository.findDbResSkuRepoByRepoAndSkuAndStockType(repo, sku, stockType);
                //avalible的加上之前reserved的qty，扣减新的reserved qty
                if(skuRepo!=null){
                    skuRepo.setQty(skuRepo.getQty()+dbResReservation.getQty()-bean.getQty());
                    skuRepoRepository.saveAndFlush(skuRepo);
                }
                //reserved的减去之前的qty加上现在的qty
                DbResStockType stockType2 = new DbResStockType();stockType2.setId(4L);
                DbResSkuRepo skuRepo2 = skuRepoRepository.findDbResSkuRepoByRepoAndSkuAndStockType(repo, sku, stockType2);
                if(skuRepo2 != null){
                    skuRepo2.setQty(skuRepo2.getQty()-dbResReservation.getQty()+bean.getQty());
                    skuRepoRepository.saveAndFlush(skuRepo2);
                }
            } else {
            //修改了repo或者sku
                DbResSku sku2 = new DbResSku();sku2.setId(dbResReservation.getSkuId());
                DbResRepo repo2 = new DbResRepo();repo2.setId(dbResReservation.getRepoId());
                DbResStockType stockType2 = new DbResStockType();stockType2.setId(3L);
                DbResSkuRepo value = skuRepoRepository.findDbResSkuRepoByRepoAndSkuAndStockType(repo2, sku2, stockType2);
                //找到之前的available加上之前的resereved qty
                if(value != null) {
                    value.setQty(value.getQty()+dbResReservation.getQty());
                    skuRepoRepository.saveAndFlush(value);

                    DbResSku sku3 = new DbResSku();sku3.setId(bean.getSkuId());
                    DbResRepo repo3 = new DbResRepo();repo3.setId(bean.getRepoId());
                    DbResSkuRepo value2 = skuRepoRepository.findDbResSkuRepoByRepoAndSkuAndStockType(repo3, sku3, stockType2);
                    //找到现在的available，减去现在的qty
                    if(value2!=null){
                        value2.setQty(value2.getQty()-bean.getQty());
                        skuRepoRepository.saveAndFlush(value2);

                        DbResStockType stockType3 = new DbResStockType();stockType3.setId(4L);
                        //找到之前的reserved减去之前qty
                        DbResSkuRepo resereved = skuRepoRepository.findDbResSkuRepoByRepoAndSkuAndStockType(repo2, sku2, stockType3);
                        if(resereved!=null){
                            resereved.setQty(resereved.getQty()-dbResReservation.getQty());
                            skuRepoRepository.saveAndFlush(resereved);

                            //找到现在的reserved加上现在的qty
                            DbResSkuRepo resereved1 = skuRepoRepository.findDbResSkuRepoByRepoAndSkuAndStockType(repo3, sku3, stockType3);
                            if(resereved1 != null){
                                resereved1.setQty(resereved1.getQty()+bean.getQty());
                                skuRepoRepository.saveAndFlush(value2);
                            } else {
                                long time = System.currentTimeMillis();
                                DbResSkuRepo newReserved = new DbResSkuRepo();
                                newReserved.setRepo(repo3);
                                newReserved.setSku(sku3);
                                newReserved.setStockType(stockType3);
                                newReserved.setQty(bean.getQty());
                                newReserved.setRemark(bean.getRemark());
                                newReserved.setCreateAt(time);
                                newReserved.setUpdateAt(time);
                                newReserved.setCreateBy(getAccount());
                                newReserved.setUpdateBy(getAccount());
                                newReserved.setActive("Y");
                                skuRepoRepository.saveAndFlush(value);
                            }
                        }
                    }
                }
            }
            this.edit(reservationRepository, DbResReservation.class, bean);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
        return JsonResult.success(Arrays.asList());
    }

    public String genTranNum(Date date, String tpye, String storeNameFrom) {

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HHmmss");
        String strDate = format.format(date);
        System.out.println(strDate);
        String prefix = strDate.substring(2, 8);
        String suffix = strDate.substring(9);
        String transationNumber = prefix + tpye + storeNameFrom  + suffix;
        return transationNumber;
    }

    @RequestMapping(method = RequestMethod.POST,path = "/print")
    public JsonResult print(@RequestBody EditBean b){
        try {
            DbResStockTake stockTake = stockTakeRepository.findById(b.getId()).get();
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map map = new HashMap<>();
            map.put("number",stockTake.getStockTakeNumber());
            map.put("channel",repoRepository.findById(stockTake.getChannelId()).get().getRepoCode());
            map.put("time",stockTake.getCompleteTime() == null ?"":sdf.format(stockTake.getCompleteTime()));
            String skuCode = "";
            List<Map> dtls = new LinkedList<>();
            for (DbResStockTakeDtl dtl : stockTake.getLine()) {
                Map kMap = new HashMap();
                DbResSku sku = skuRepository.findById(dtl.getSkuId()).get();
                skuCode += sku.getSkuCode()+",";
                kMap.put("sku",sku.getSkuCode()==null?"":sku.getSkuCode());
                kMap.put("one",dtl.getStockTakeOne()==null?"":dtl.getStockTakeOne());
                kMap.put("two",dtl.getStockTakeTwo()==null?"":dtl.getStockTakeTwo());
                kMap.put("three",dtl.getStockTakeThree()==null?"":dtl.getStockTakeThree());
                kMap.put("balance",dtl.getStockTakeBalance()==null?"":dtl.getStockTakeBalance());
                dtls.add(kMap);
            }
            map.put("sku",skuCode);
            map.put("list",dtls);

            System.out.println("导出前查询list:=====================");
            System.out.println(map.toString());

            InputStream in = new ClassPathResource(inPath).getInputStream();
            String filename = URLEncoder.encode("stock_take"+sdf.format(new Date())+".xlsx", "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="+filename);
            /*response.setContentType("application/ms-excel;charset=UTF-8");*/
            response.setContentType("application/x-xls;charset=UTF-8");
            OutputStream out = response.getOutputStream();
            Context context = new Context();
            context.putVar("test",map);

//        JxlsHelper jxlsHelper = JxlsHelper.getInstance();
//        Transformer transformer = jxlsHelper.createTransformer(in, out);
//        jxlsHelper.processTemplate(context, transformer);
            JxlsHelper.getInstance().processTemplate(in, out, context);
            System.out.println("导出完毕！");
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
    }

}
