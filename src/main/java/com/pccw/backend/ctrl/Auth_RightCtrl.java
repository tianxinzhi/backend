package com.pccw.backend.ctrl;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.auth_right.*;
import com.pccw.backend.cusinterface.ICheck;
import com.pccw.backend.entity.DbResLogMgt;
import com.pccw.backend.entity.DbResRight;
import com.pccw.backend.entity.DbResRoleRight;
import com.pccw.backend.repository.BaseRepository;
import com.pccw.backend.repository.ResLogMgtRepository;
import com.pccw.backend.repository.ResRightRepository;

import javax.validation.Valid;

import com.pccw.backend.bean.BaseDeleteBean;

import com.pccw.backend.repository.ResRoleRightRepository;
import com.pccw.backend.util.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * AuthRightCtrl
 */

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/auth_right")
@Api(value="Auth_RightCtrl",tags={"auth_right"})
public class Auth_RightCtrl extends BaseCtrl<DbResRight> implements ICheck {

    @Autowired
    ResRightRepository repo;
    @Autowired
    ResRoleRightRepository roleRightRepository;

    @ApiOperation(value="搜索权限",tags={"auth_right"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,path="/search")
    public JsonResult search(@RequestBody @Valid SearchBean b) {
//        b.setPageSize(200);
        try {
            Specification<DbResRight> spec = Convertor.convertSpecification(b);
            List<DbResRight> res = repo.findAll(spec).stream().filter(r-> Objects.nonNull(r.getRightPid())).collect(Collectors.toList());
//        return this.search(repo, b);
            return JsonResult.success(res);
        } catch (IllegalAccessException e) {
            return JsonResult.fail(e);
        }
    }
    
    @RequestMapping(method = RequestMethod.POST,path = "/delete")
    @ApiOperation(value="删除权限",tags={"auth_right"},notes="注意问题点")
    public JsonResult delete(@RequestBody BaseDeleteBean ids){
        return this.delete(repo,ids);
    }
    
    @RequestMapping(method = RequestMethod.POST,path="/create")
    @ApiOperation(value="新增权限",tags={"auth_right"},notes="注意问题点")
    public JsonResult create(@RequestBody CreateBean b){
        return this.create(repo, DbResRight.class, b);
    }
    @RequestMapping(method = RequestMethod.POST,path="/edit")
    @ApiOperation(value="修改权限",tags={"auth_right"},notes="注意问题点")
    public JsonResult edit(@RequestBody EditBean b){
        log.info(b.toString());
        return this.edit(repo, DbResRight.class, b);
    }

    @ApiOperation(value="禁用auth_right",tags={"auth_right"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/disable")
    public JsonResult disable(@RequestBody BaseDeleteBean ids) {
        return this.disable(repo,ids,Auth_RightCtrl.class,roleRightRepository);
    }

    @Override
    public long checkCanDisable(Object obj, BaseRepository... check) {
        ResRoleRightRepository tRepo = (ResRoleRightRepository)check[0];
        BaseDeleteBean bean = (BaseDeleteBean)obj;
        for (Long id : bean.getIds()) {
            List<DbResRoleRight> roleRights = tRepo.getDbResRoleRightsByRightId(id);
            if ( roleRights != null && roleRights.size()>0 ) {
                return id;
            }
        }
        return 0;
    }
}
