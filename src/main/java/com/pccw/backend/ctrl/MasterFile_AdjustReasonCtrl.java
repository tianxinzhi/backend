package com.pccw.backend.ctrl;

import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.masterfile_adjust_reason.CreateBean;
import com.pccw.backend.bean.masterfile_adjust_reason.EditBean;
import com.pccw.backend.bean.masterfile_adjust_reason.SearchBean;
import com.pccw.backend.cusinterface.ICheck;
import com.pccw.backend.entity.DbResAdjustReason;
import com.pccw.backend.repository.BaseRepository;
import com.pccw.backend.repository.ResAdjustReasonRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("masterfile_adjust_reason")
@Api(value="MasterFile_AdjustReasonCtrl",tags={"masterfile_adjust_reason"})
public class MasterFile_AdjustReasonCtrl extends BaseCtrl<DbResAdjustReason> implements ICheck {

    @Autowired
    ResAdjustReasonRepository repo;

    @ApiOperation(value="创建adjust_reason",tags={"masterfile_adjust_reason"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/create")
    public JsonResult create(@RequestBody CreateBean bean) {
        System.out.println("bean:"+bean);
        return this.create(repo,DbResAdjustReason.class,bean);
    }

    @ApiOperation(value="删除adjust_reason",tags={"masterfile_adjust_reason"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids) {
        return this.delete(repo,ids);
    }

    @ApiOperation(value="禁用adjust_reason",tags={"masterfile_adjust_reason"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/disable")
    public JsonResult disable(@RequestBody BaseDeleteBean ids) {
        return this.disable(repo,ids,MasterFile_AdjustReasonCtrl.class);
    }

    @ApiOperation(value="修改adjust_reason",tags={"masterfile_adjust_reason"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/edit")
    public JsonResult edit(@RequestBody EditBean b) {
        return this.edit(repo, DbResAdjustReason.class, b);
    }

    @ApiOperation(value="搜索adjust_reason",tags={"masterfile_adjust_reason"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/search")
    public JsonResult search(@RequestBody SearchBean bean) {
        return this.search(repo,bean);
    }

    @Override
    public long checkCanDisable(Object obj, BaseRepository... check) {
        return 0;
    }
}
