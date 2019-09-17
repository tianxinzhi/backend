package com.pccw.backend.ctrl;

import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.masterfile_attr_value.CreateBean;
import com.pccw.backend.bean.masterfile_attr_value.EditBean;
import com.pccw.backend.bean.masterfile_attr_value.SearchBean;
import com.pccw.backend.entity.DbResAttrValue;
import com.pccw.backend.repository.ResAttrValueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("masterfile_attr_value")
public class MasterFile_AttrValueCtrl extends BaseCtrl<DbResAttrValue> {

    @Autowired
    ResAttrValueRepository repo;

    @RequestMapping(method = RequestMethod.POST,value = "/create")
    public JsonResult create(@RequestBody CreateBean bean) {
        System.out.println("attrValue:"+bean);
        return this.create(repo,DbResAttrValue.class,bean);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids) {
        return this.delete(repo,ids);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/edit")
    public JsonResult edit(@RequestBody EditBean b) {
        return this.edit(repo, DbResAttrValue.class, b);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/search")
    public JsonResult search(@RequestBody SearchBean bean) {
        return this.search(repo,bean);
    }
}
