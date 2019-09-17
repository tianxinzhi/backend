package com.pccw.backend.ctrl;

import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.masterfile_attr_value.CreateBean;
import com.pccw.backend.bean.masterfile_attr_value.EditBean;
import com.pccw.backend.bean.masterfile_attr_value.SearchBean;
import com.pccw.backend.entity.DbResAttrValue;
import com.pccw.backend.repository.ResAttrValueRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("masterfile_attr_value")
@Api(value = "MasterFile_AttrValueCtrl" ,tags = {"masterfile_attr_value"})
public class MasterFile_AttrValueCtrl extends BaseCtrl<DbResAttrValue> {

    @Autowired
    ResAttrValueRepository repo;

    @ApiOperation(value="创建attr_value",tags={"masterfile_attr_value"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/create")
    public JsonResult create(@RequestBody CreateBean bean) {
        System.out.println("attrValue:"+bean);
        return this.create(repo,DbResAttrValue.class,bean);
    }

    @ApiOperation(value="删除attr_value",tags={"masterfile_attr_value"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids) {
        return this.delete(repo,ids);
    }

    @ApiOperation(value="修改attr_value",tags={"masterfile_attr_value"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/edit")
    public JsonResult edit(@RequestBody EditBean b) {
        return this.edit(repo, DbResAttrValue.class, b);
    }

    @ApiOperation(value="搜索attr_value",tags={"masterfile_attr_value"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/search")
    public JsonResult search(@RequestBody SearchBean bean) {
        return this.search(repo,bean);
    }
}
