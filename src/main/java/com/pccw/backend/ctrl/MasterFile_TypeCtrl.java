package com.pccw.backend.ctrl;


import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.masterfile_type.CreateBean;
import com.pccw.backend.bean.masterfile_type.EditBean;
import com.pccw.backend.bean.masterfile_type.SearchBean;
import com.pccw.backend.entity.DbResClass;
import com.pccw.backend.entity.DbResType;
import com.pccw.backend.repository.ResClassRepository;
import com.pccw.backend.repository.ResTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * AuthRightCtrl
 */

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST, origins = "*", allowCredentials = "false")
@RequestMapping("/masterfile_type")
public class MasterFile_TypeCtrl extends BaseCtrl<DbResType> {

    @Autowired
    ResTypeRepository repo;
    @Autowired
    ResClassRepository resClassRepository;

    @RequestMapping(method = RequestMethod.POST, path = "/search")
    public JsonResult search(@RequestBody SearchBean b) {
        log.info(b.toString());
        return this.search(repo, b);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids) {
        return this.delete(repo, ids);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/create")
    public JsonResult create(@RequestBody CreateBean b) {
        Optional<DbResClass> byId = resClassRepository.findById(b.getClassId().intValue());
        DbResClass dbResClass = byId.get();
        List<DbResClass> classList = new ArrayList<DbResClass>();
        classList.add(dbResClass);
        b.setClassList(classList);
        return this.create(repo, DbResType.class, b);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/edit")
    public JsonResult edit(@RequestBody EditBean b) {
        return this.edit(repo, DbResType.class, b);
    }
}