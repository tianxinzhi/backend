package com.pccw.backend.ctrl;


import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.stock_replenishment.CreateReplBean;
import com.pccw.backend.bean.stock_replenishment.CreateReplDtlBean;
import com.pccw.backend.entity.DbResLogRepl;
import com.pccw.backend.entity.DbResLogReplDtl;
import com.pccw.backend.repository.ResLogReplRepository;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/stock_replenishment")
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
public class Stock_ReplenishmentCtrl extends BaseCtrl<DbResLogRepl> {

    @Autowired
    ResLogReplRepository repo;

    /**
     * 收货
     * @param b
     * @return
     */
    @ApiOperation(value="创建replenishment",tags={"stock_replenishment"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    public JsonResult create(@RequestBody CreateReplBean b) {
        try {
            long t = new Date().getTime();
            b.setCreateAt(t);
            b.setActive("Y");
            if(StaticVariable.LOGORDERNATURE_REPLENISHMENT_RECEIVEN.equals(b.getReplType())){
                b.setRepoIdFrom(b.getLogRepoOut());
                b.setRepoIdTo(b.getLogRepoIn());
                b.setLogOrderNature(StaticVariable.LOGORDERNATURE_REPLENISHMENT_RECEIVEN);
                b.setStatus(StaticVariable.STATUS_WAITING);
            }else{
                b.setLogOrderNature(StaticVariable.LOGORDERNATURE_REPLENISHMENT_REQUEST);
                b.setStatus(StaticVariable.STATUS_WAITING);
            }
            b.setLogType(StaticVariable.LOGTYPE_REPL);

            DbResLogRepl repl = new DbResLogRepl();
            BeanUtils.copyProperties(b, repl);
            List<DbResLogReplDtl> dtlss = new ArrayList<DbResLogReplDtl>();
            repl.setLine(dtlss);
            List<CreateReplDtlBean> dtls = b.getLine();
            for(CreateReplDtlBean dt:dtls){
                dt.setCreateAt(t);
                dt.setLogTxtBum(b.getLogTxtBum());
                DbResLogReplDtl dtl = new DbResLogReplDtl();
                BeanUtils.copyProperties(dt, dtl);
                if(StaticVariable.LOGORDERNATURE_REPLENISHMENT_RECEIVEN.equals(b.getReplType())){
                    dtl.setDtlAction(StaticVariable.DTLACTION_ADD);
                    dtl.setDtlSubin(StaticVariable.DTLSUBIN_GOOD);
                    dtl.setLisStatus(StaticVariable.LISSTATUS_WAITING);
                    dtl.setStatus(StaticVariable.STATUS_AVAILABLE);
                }else{
                    dtl.setLisStatus(StaticVariable.LISSTATUS_WAITING);
                }
                dtl.setDbResLogRepl(repl);
                repl.getLine().add(dtl);
            }
            repo.saveAndFlush(repl);
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            System.out.println(e);
            return JsonResult.fail(e);
        }
    }

}
