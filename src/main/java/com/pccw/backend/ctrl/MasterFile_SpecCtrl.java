package com.pccw.backend.ctrl;


import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.masterfile_spec.CreateBean;
import com.pccw.backend.bean.masterfile_spec.EditBean;
import com.pccw.backend.bean.masterfile_spec.SearchBean;
import com.pccw.backend.entity.DbResSpec;
import com.pccw.backend.repository.ResSpecRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * MF_RepoCtrl
 */

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/masterfile_spec")
public class MasterFile_SpecCtrl extends BaseCtrl<DbResSpec> {

    @Autowired
    ResSpecRepository repo;

    @RequestMapping(method = RequestMethod.POST,path="/search")
    public JsonResult search(@RequestBody SearchBean b) {
        log.info(b.toString());
        return this.search(repo,  b);
    }

    @RequestMapping(method = RequestMethod.POST,path = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids){
        return this.delete(repo,ids);
    }

    @RequestMapping(method = RequestMethod.POST,path="/create")
    public JsonResult create(@RequestBody CreateBean b){
        return this.create(repo, DbResSpec.class, b);
    }
    @RequestMapping(method = RequestMethod.POST,path="/edit")
    public JsonResult edit(@RequestBody EditBean b){
        log.info(b.toString());
        return this.edit(repo, DbResSpec.class, b);
    }

 /*   @RequestMapping(method = RequestMethod.POST,path="/test")
    public JsonResult test(@RequestBody EditBean b){
        log.info(b.toString());
        try {
            return JsonResult.success(repo.findTest());
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }*/

}