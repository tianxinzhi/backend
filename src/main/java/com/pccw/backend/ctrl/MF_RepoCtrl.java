package com.pccw.backend.ctrl;


import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.mf_repo.CreateBean;
import com.pccw.backend.bean.mf_repo.EditBean;
import com.pccw.backend.bean.mf_repo.SearchBean;
import com.pccw.backend.entity.DbResRepo;
import com.pccw.backend.repository.ResRepoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * MF_RepoCtrl
 */

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/mf_repo")
public class MF_RepoCtrl extends BaseCtrl<DbResRepo> {

    @Autowired
    ResRepoRepository repo;

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
        return this.create(repo, DbResRepo.class, b);
    }
    @RequestMapping(method = RequestMethod.POST,path="/edit")
    public JsonResult edit(@RequestBody EditBean b){
        log.info(b.toString());
        return this.edit(repo, DbResRepo.class, b);
    }
}