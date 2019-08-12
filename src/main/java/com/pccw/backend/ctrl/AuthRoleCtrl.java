package com.pccw.backend.ctrl;

import java.util.List;

import com.pccw.backend.bean.AuthRole.SearchCondition;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.entity.DbResRole;
import com.pccw.backend.repository.ResRoleRepository;
import com.pccw.backend.util.Convertor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * AuthRightCtrl
 */

@Slf4j
@RestController
@RequestMapping("/auth/role")
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
public class AuthRoleCtrl {

    @Autowired
    ResRoleRepository repo;

    @RequestMapping(method = RequestMethod.POST,path="/search")
    public JsonResult<DbResRole> search(@RequestBody SearchCondition sc) {
        try {
            Specification<DbResRole> spec = Convertor.<DbResRole,SearchCondition>convertSpecification(SearchCondition.class,sc);
            List<DbResRole> res =repo.findAll(spec,PageRequest.of(sc.getPageIndex(),sc.getPageSize())).getContent();
            return JsonResult.success(res);
        } catch (Exception e) {
            return JsonResult.fail();
        }
    }
    
}