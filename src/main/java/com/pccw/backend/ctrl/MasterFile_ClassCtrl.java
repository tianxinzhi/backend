package com.pccw.backend.ctrl;


import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.masterfile_class.CreateBean;
import com.pccw.backend.bean.masterfile_class.EditBean;
import com.pccw.backend.bean.masterfile_class.SearchBean;
import com.pccw.backend.entity.DbResClass;
import com.pccw.backend.repository.ResClassRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * AuthRightCtrl
 */

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST, origins = "*", allowCredentials = "false")
@RequestMapping("/masterfile_class")
@Api(value="MasterFile_ClassCtrl",tags={"masterfile_class"})
public class MasterFile_ClassCtrl extends BaseCtrl<DbResClass> {

    @Autowired
    ResClassRepository repo;

    @ApiOperation(value="搜索class",tags={"masterfile_class"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/search")
    public JsonResult search(@RequestBody SearchBean b) {
        log.info(b.toString());
        return this.search(repo, b);
    }

    @ApiOperation(value="删除class",tags={"masterfile_class"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids) {
        return this.delete(repo, ids);
    }

    @ApiOperation(value="创建class",tags={"masterfile_class"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    public JsonResult create(@RequestBody CreateBean b) {
        return this.create(repo, DbResClass.class, b);
    }

    @ApiOperation(value="编辑class",tags={"masterfile_class"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/edit")
    public JsonResult edit(@RequestBody EditBean b) {
        try {
            Optional<DbResClass> opt = repo.findById(b.getId());
            DbResClass dbResClass = opt.get();
            b.setUpdateAt(new Date().getTime());
            BeanUtils.copyProperties(b, dbResClass);
            repo.saveAndFlush(dbResClass);
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }
}
