package com.pccw.backend.ctrl;

import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;

import com.pccw.backend.bean.masterfile_sku.CreateBean;
import com.pccw.backend.bean.masterfile_sku.EditBean;
import com.pccw.backend.bean.masterfile_sku.SearchBean;
import com.pccw.backend.entity.DbResSku;
import com.pccw.backend.repository.ResSkuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("masterfile_sku")
public class MasterFile_SkuCtrl extends BaseCtrl<DbResSku> {

    @Autowired
    ResSkuRepository repo;

    @RequestMapping(method = RequestMethod.POST,value = "/create")
    public JsonResult create(@RequestBody CreateBean bean) {
        return this.create(repo,DbResSku.class,bean);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids) {
        return this.delete(repo,ids);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/edit")
    public JsonResult edit(@RequestBody EditBean b) {
        return this.edit(repo, DbResSku.class, b);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/search")
    public JsonResult search(@RequestBody SearchBean bean) {
        return this.search(repo,bean);
    }
}
