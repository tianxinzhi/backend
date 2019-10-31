package com.pccw.backend.ctrl;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.stock_adjustment.LogMgtBean;
import com.pccw.backend.bean.stock_adjustment.LogMgtDtlBean;
import com.pccw.backend.entity.DbResLogMgt;
import com.pccw.backend.entity.DbResLogMgtDtl;
import com.pccw.backend.repository.ResLogMgtRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value="调货",tags={"stock_adjustment"},notes="说明")
    @RequestMapping("/confirm")
    public JsonResult adjust(@RequestBody LogMgtBean bean) {
        System.out.println("logMgtBean:"+bean);
        DbResLogMgt ent = new DbResLogMgt();
        ent.setLogRepoIn(bean.getLogRepoIn());
        ent.setLogTxtBum(bean.getTransactionNumber());
        ent.setLogType(StaticVariable.LOGTYPE_MANAGEMENT);
        ent.setLogOrderNature(StaticVariable.LOGORDERNATURE_STOCK_TAKE_ADJUSTMENT);
        ent.setStatus(StaticVariable.STATUS_WAITING);
        ent.setRemark(bean.getRemark());
        ent.setCreateAt(bean.getCreateDate());
        ent.setUpdateAt(bean.getCreateDate());
        ent.setActive("Y");
//        ent.setCreateBy(1);
//        ent.setUpdateBy(1);

        List<DbResLogMgtDtl> lstMgtDtl = new LinkedList<>();
        for(LogMgtDtlBean dtl:bean.getLine()) {
            DbResLogMgtDtl mgtDtl = new DbResLogMgtDtl();
//            mgtDtl.setDtlSkuId(dtl.getDtlSkuId());
//            mgtDtl.setDtlItemId(dtl.getItem());
//            mgtDtl.setDtlRepoId(dtl.getRepo());
//            mgtDtl.setDtlQty(dtl.getQty());
            BeanUtils.copyProperties(dtl, mgtDtl);
            mgtDtl.setLogTxtBum(bean.getTransactionNumber());
            //mgtDtl.setDtlLogId();
            mgtDtl.setDtlAction(dtl.getDtlQty()>0 ? StaticVariable.DTLACTION_ADD : StaticVariable.DTLACTION_DEDUCT);
            mgtDtl.setLisStatus(StaticVariable.LISSTATUS_WAITING);
            if(dtl.getCatalog().trim().equalsIgnoreCase("Good")){
                mgtDtl.setDtlSubin(StaticVariable.DTLSUBIN_GOOD);
                mgtDtl.setStatus(StaticVariable.STATUS_AVAILABLE);
            } else if(dtl.getCatalog().trim().equalsIgnoreCase("Faulty")) {
                mgtDtl.setDtlSubin(StaticVariable.DTLSUBIN_FAULTY);
                mgtDtl.setStatus(StaticVariable.STATUS_FAULTY);
            } else if (dtl.getCatalog().trim().equalsIgnoreCase("Intran")) {
                mgtDtl.setDtlSubin(StaticVariable.DTLSUBIN_INTRAN);
                mgtDtl.setStatus(StaticVariable.STATUS_INTRANSIT);
            }
            mgtDtl.setCreateAt(bean.getCreateDate());
            mgtDtl.setUpdateAt(bean.getCreateDate());
//            mgtDtl.setCreateBy(1);
//            mgtDtl.setUpdateBy(1);
            mgtDtl.setActive("Y");
            mgtDtl.setResLogMgt(ent);
            lstMgtDtl.add(mgtDtl);
        }

        ent.setLine(lstMgtDtl);
        //dt1.set
        logMgtRepository.saveAndFlush(ent);
        return JsonResult.success(Arrays.asList());
    }

}
