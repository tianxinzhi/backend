package com.pccw.backend.ctrl;

import java.util.*;

import com.pccw.backend.bean.auth_role.*;
import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.entity.DbResRight;
import com.pccw.backend.entity.DbResRole;
import com.pccw.backend.entity.DbResRoleRight;
import com.pccw.backend.repository.ResRightRepository;
import com.pccw.backend.repository.ResRoleRepository;
import com.pccw.backend.util.Convertor;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * AuthRightCtrl
 */

@Slf4j
@RestController
@RequestMapping("/auth_role")
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@Api(value="AuthRoleCtrl",tags={"auth_role"})
public class Auth_RoleCtrl extends BaseCtrl<DbResRole>{

    @Autowired
    ResRoleRepository repo;

    @Autowired
    ResRightRepository repoRight;

    @RequestMapping(method = RequestMethod.POST,path="/search")
    @ApiOperation(value="搜索角色",tags={"auth_role"},notes="注意问题点")
    public JsonResult search(@ApiParam(name="SearchBean",value="搜索条件",required=true) @RequestBody SearchBean b) {
        try {
        Specification spec = Convertor.convertSpecification(b);
        List<DbResRole> roleList = repo.findAll(spec,PageRequest.of(b.getPageIndex(),b.getPageSize())).getContent();
        List<SearchBean> resultBeans = new LinkedList<>();
        for (DbResRole resRole : roleList) {
            List<Map> rightList = new ArrayList<>();
            SearchBean resultBean = new SearchBean();
            BeanUtils.copyProperties(resRole,resultBean);
            String[] rightNames = new String[resRole.getResRoleRightList().size()];
            long[] rightIds  = new long[resRole.getResRoleRightList().size()];
            for(int i=0;i<resRole.getResRoleRightList().size();i++) {
                Optional<DbResRight> optional = repoRight.findById(resRole.getResRoleRightList().get(i).getRightId());
                DbResRight resRight = optional.get();
                rightNames[i] = resRight.getRightName();
                rightIds[i] = resRight.getId();
                //详情

                HashMap<Object, Object> hm = new HashMap<>();
                hm.put("rightName",resRight.getRightName());
                hm.put("rightUrl",resRight.getRightUrl());
                rightList.add(hm);
               }
            resultBean.setId(resRole.getId());
            resultBean.setRightName(rightNames);
            resultBean.setRightId(rightIds);
            resultBean.setRightData(rightList);
            System.out.println("result:" + resultBean);
            resultBeans.add(resultBean);
            }
           return JsonResult.success(resultBeans);
        }catch (Exception e) {
                log.info(e.getMessage());
                return JsonResult.fail(e);
            }

        //return this.search(repo, b);
    }
    
    @RequestMapping(method = RequestMethod.POST,path = "/delete")
    @ApiOperation(value="删除角色",tags={"auth_role"},notes="注意问题点")
    public JsonResult delete(@RequestBody BaseDeleteBean ids){
        return this.delete(repo,ids);
    }
    
    @RequestMapping(method = RequestMethod.POST,path="/create")
    @ApiOperation(value="新增角色",tags={"auth_role"},notes="注意问题点")
    public JsonResult create(@RequestBody CreateBean b){
        DbResRole role =new DbResRole();
        BeanUtils.copyProperties(b,role);
        /*role.setActive("Y");
        role.setCreateAt(System.currentTimeMillis());
        role.setUpdateAt(System.currentTimeMillis());*/
        List<DbResRoleRight> roleRightList = new LinkedList<>();
        for(String value:b.getRightId()){
            DbResRoleRight roleRight = new DbResRoleRight();
            roleRight.setRightId(Long.parseLong(value));
            roleRight.setActive("Y");
            roleRight.setCreateAt(System.currentTimeMillis());
            roleRight.setUpdateAt(System.currentTimeMillis());
            roleRightList.add(roleRight);
        }
        role.setResRoleRightList(roleRightList);
        repo.saveAndFlush(role);
        return JsonResult.success(Arrays.asList());
        //return this.create(repo, DbResRole.class, b);

    }
    @RequestMapping(method = RequestMethod.POST,path="/edit")
    @ApiOperation(value="修改角色",tags={"auth_role"},notes="注意问题点")
    public JsonResult edit(@RequestBody EditBean b){

        DbResRole role = repo.findById(b.getId()).get();
        role.setRoleDesc(b.getRoleDesc());
        role.setRoleName(b.getRoleName());
        role.setUpdateAt(System.currentTimeMillis());
        List<DbResRoleRight> roleRightList = role.getResRoleRightList();
        roleRightList.clear();
        for(String valueId:b.getRightId()){
            RoleRightEditBean roleRight = new RoleRightEditBean();
            roleRight.setRightId(Long.parseLong(valueId));
            roleRight.setRoleId(role.getId());
            roleRight.setActive("Y");
            roleRight.setUpdateAt(System.currentTimeMillis());
            roleRightList.add(roleRight);
        }
        role.setResRoleRightList(roleRightList);
        repo.saveAndFlush(role);
        return JsonResult.success(Arrays.asList());

       // return this.edit(repo, DbResRole.class, b);
    }
    
}