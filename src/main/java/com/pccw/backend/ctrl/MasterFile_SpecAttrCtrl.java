package com.pccw.backend.ctrl;

import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.masterfile_spec_attr.CreateBean;
import com.pccw.backend.bean.masterfile_spec_attr.EditBean;
import com.pccw.backend.bean.masterfile_spec_attr.SearchBean;
import com.pccw.backend.entity.DbResSpecAttr;
import com.pccw.backend.repository.ResSpecAttrRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("masterfile_spec_attr")
public class MasterFile_SpecAttrCtrl extends BaseCtrl<DbResSpecAttr> {

    @Autowired
    ResSpecAttrRepository repo;

    @RequestMapping(method = RequestMethod.POST,value = "/create")
    public JsonResult create(@RequestBody CreateBean bean) {
        return this.create(repo,DbResSpecAttr.class,bean);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids) {
        return this.delete(repo,ids);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/edit")
    public JsonResult edit(@RequestBody EditBean b) {
        return this.edit(repo, DbResSpecAttr.class, b);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/search")
    public JsonResult search(@RequestBody SearchBean bean) {
        return this.search(repo,bean);
    }
}
