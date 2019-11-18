package com.pccw.backend.ctrl;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.stock_adjustment.LogMgtBean;
import com.pccw.backend.bean.stock_adjustment.LogMgtDtlBean;
import com.pccw.backend.entity.DbResLogMgt;
import com.pccw.backend.entity.DbResLogMgtDtl;
import com.pccw.backend.entity.DbResSkuRepo;
import com.pccw.backend.repository.ResLogMgtRepository;
import com.pccw.backend.repository.ResSkuRepoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/stock_adjustment")
@Api(value="Stock_AdjustmentCtrl",tags={"stock_adjustment"})
public class Stock_AdjustmentCtrl extends BaseCtrl<DbResLogMgt> {

    @Autowired
    ResLogMgtRepository logMgtRepository;
    @Autowired
    ResSkuRepoRepository skuRepoRepository;

    @ApiOperation(value="调货",tags={"stock_adjustment"},notes="说明")
    @RequestMapping("/confirm")
    public JsonResult confirm(@RequestBody LogMgtBean bean) {
        try {
            System.out.println("logMgtBean:"+bean);
            DbResLogMgt ent = new DbResLogMgt();
            ent.setLogRepoIn(bean.getLogRepoIn());
            ent.setLogRepoOut(bean.getLogRepoOut());
            ent.setLogTxtBum(bean.getTransactionNumber());
            ent.setLogType(StaticVariable.LOGTYPE_MANAGEMENT);
            ent.setLogOrderNature(StaticVariable.LOGORDERNATURE_STOCK_TAKE_ADJUSTMENT);
            ent.setStatus(StaticVariable.STATUS_WAITING);
            //ent.setRemark(bean.getRemark());
            ent.setAdjustReasonId(bean.getRemark());
            ent.setCreateAt(bean.getCreateDate());
            ent.setUpdateAt(bean.getCreateDate());
            ent.setActive("Y");
//        ent.setCreateBy(1);
//        ent.setUpdateBy(1);

            List<DbResLogMgtDtl> lstMgtDtl = new LinkedList<>();
            for(LogMgtDtlBean dtl:bean.getLine()) {
                DbResLogMgtDtl mgtDtl = new DbResLogMgtDtl();
                BeanUtils.copyProperties(dtl, mgtDtl);
                mgtDtl.setLogTxtBum(bean.getTransactionNumber());
                //mgtDtl.setDtlLogId();
                mgtDtl.setDtlAction(dtl.getDtlQty()>0 ? StaticVariable.DTLACTION_ADD : StaticVariable.DTLACTION_DEDUCT);
                mgtDtl.setLisStatus(StaticVariable.LISSTATUS_WAITING);
                mgtDtl.setCreateAt(bean.getCreateDate());
                mgtDtl.setUpdateAt(bean.getCreateDate());
    //            mgtDtl.setCreateBy(1);
    //            mgtDtl.setUpdateBy(1);
                mgtDtl.setActive("Y");
                mgtDtl.setResLogMgt(ent);
                switch ((int)dtl.getCatalog()) {
                    case 1:
                        mgtDtl.setDtlSubin(StaticVariable.DTLSUBIN_DEMO);
                        mgtDtl.setStatus(StaticVariable.STATUS_DEMO);
                        break;
                    case 2:
                        mgtDtl.setDtlSubin(StaticVariable.DTLSUBIN_FAULTY);
                        mgtDtl.setStatus(StaticVariable.STATUS_FAULTY);
                        break;
                    case 3:
                        mgtDtl.setDtlSubin(StaticVariable.DTLSUBIN_AVAILABLE);
                        mgtDtl.setStatus(StaticVariable.STATUS_AVAILABLE);
                        break;
                    case 4:
                        mgtDtl.setDtlSubin(StaticVariable.DTLSUBIN_RESERVED);
                        mgtDtl.setStatus(StaticVariable.STATUS_RESERVED);
                        break;
                    case 5:
                        mgtDtl.setDtlSubin(StaticVariable.DTLSUBIN_RESERVED_WITH_AO);
                        mgtDtl.setStatus(StaticVariable.STATUS_RESERVED_WITH_AO);
                        break;
                    case 6:
                        mgtDtl.setDtlSubin(StaticVariable.DTLSUBIN_INTRANSIT);
                        mgtDtl.setStatus(StaticVariable.STATUS_INTRANSIT);
                        break;
                }
                lstMgtDtl.add(mgtDtl);
                int res = skuRepoRepository.updateQtyByRepoAndShopAndTypeAndQty(bean.getLogRepoOut(),dtl.getDtlSkuId(),dtl.getCatalog(),dtl.getDtlQty());
                if(res<=0) return JsonResult.fail(new Exception());
            }

            ent.setLine(lstMgtDtl);
            logMgtRepository.saveAndFlush(ent);

            return JsonResult.success(Arrays.asList());
        } catch (BeansException e) {
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="sku库存",tags={"stock_adjustment"},notes="说明")
    @RequestMapping("/balanceSearch")
    public JsonResult balanceSearch(@RequestBody LogMgtDtlBean bean) {
        try {
            DbResSkuRepo skuRepo = skuRepoRepository.findQtyByRepoAndShopAndType(bean.getDtlRepoId(),bean.getDtlSkuId(),bean.getCatalog());
            if(skuRepo==null){
                return JsonResult.fail(Arrays.asList());
            }
            return JsonResult.success(Arrays.asList(skuRepo));
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

}
