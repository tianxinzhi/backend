package com.pccw.backend.ctrl;


import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.masterfile_repo.CreateBean;
import com.pccw.backend.bean.masterfile_repo.EditBean;
import com.pccw.backend.bean.masterfile_repo.SearchBean;
import com.pccw.backend.cusinterface.ICheck;
import com.pccw.backend.entity.DbResRepo;
import com.pccw.backend.repository.BaseRepository;
import com.pccw.backend.repository.ResRepoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * MF_RepoCtrl
 */

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/masterfile_repo")
@Api(value="MasterFile_RepoCtrl",tags={"masterfile_repo"})
public class MasterFile_RepoCtrl extends BaseCtrl<DbResRepo> implements ICheck {

    @Autowired
    ResRepoRepository repo;

    @ApiOperation(value="查询shop",tags={"masterfile_repo"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/search")
    public JsonResult search(@RequestBody SearchBean b) {
        log.info(b.toString());
        return this.search(repo,  b);
    }

    @ApiOperation(value="删除shop",tags={"masterfile_repo"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids){
        return this.delete(repo,ids);
    }

    @ApiOperation(value="创建shop",tags={"masterfile_repo"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/create")
    public JsonResult create(@RequestBody CreateBean b){
        return this.create(repo, DbResRepo.class, b);
    }

    @ApiOperation(value="编辑shop",tags={"masterfile_repo"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/edit")
    public JsonResult edit(@RequestBody EditBean b){
        log.info(b.toString());
        return this.edit(repo, DbResRepo.class, b);
    }

    @ApiOperation(value="禁用repo",tags={"masterfile_repo"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/disable")
    public JsonResult disable(@RequestBody BaseDeleteBean ids) {
        return this.disable(repo,ids,MasterFile_RepoCtrl.class);
    }

    @Override
    public long checkCanDisable(Object obj, BaseRepository... check) {
        return 0;
    }
}
