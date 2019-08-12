package com.pccw.backend.ctrl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.annotation.HttpConstraint;
import javax.xml.ws.ResponseWrapper;

import com.pccw.backend.bean.DeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.AuthRight.SearchCondition;
import com.pccw.backend.entity.DbResRight;

import com.pccw.backend.repository.ResRightRepository;
import com.pccw.backend.util.Convertor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * AuthRightCtrl
 */

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/auth/right")
public class AuthRightCtrl {

    @Autowired
    ResRightRepository repo;

    @RequestMapping(method = RequestMethod.POST,path="/search")
    public JsonResult<DbResRight> search(@RequestBody SearchCondition sc) {
        try {
            Specification<DbResRight> spec = Convertor.<DbResRight,SearchCondition>convertSpecification(SearchCondition.class,sc);
            List<DbResRight> res =repo.findAll(spec,PageRequest.of(sc.getPageIndex(),sc.getPageSize())).getContent();
            return JsonResult.success(res);
        } catch (Exception e) {
            return JsonResult.fail();
        }
    }

    @RequestMapping(method = RequestMethod.POST,path = "/delete")
    public JsonResult delete(@RequestBody DeleteBean ids){
        try {
            repo.deleteByIdIn(ids.getIds());
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            return JsonResult.fail();
        }
    }
    
}