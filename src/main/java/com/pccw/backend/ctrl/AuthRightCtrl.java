package com.pccw.backend.ctrl;


import java.util.Arrays;
import java.util.List;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.authright.CreateBean;
import com.pccw.backend.bean.authright.EditBean;
// import com.pccw.backend.bean.authright.*;
import com.pccw.backend.bean.authright.SearchBean;
import com.pccw.backend.entity.DbResRight;
import com.pccw.backend.repository.ResRightRepository;
import com.pccw.backend.util.Convertor;
import com.pccw.backend.bean.DeleteBean;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import lombok.extern.java.Log;
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
    public JsonResult<DbResRight> search(@RequestBody SearchBean b) {
        try {
            Specification<DbResRight> spec = Convertor.<DbResRight,SearchBean>convertSpecification(SearchBean.class,b);
            List<DbResRight> res =repo.findAll(spec,PageRequest.of(b.getPageIndex(),b.getPageSize())).getContent();
            return JsonResult.success(res);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @RequestMapping(method = RequestMethod.POST,path = "/delete")
    public JsonResult delete(@RequestBody DeleteBean ids){
        try {
            repo.deleteByIdIn(ids.getIds());
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @RequestMapping(method = RequestMethod.POST,path="/create")
    public JsonResult Create(@RequestBody CreateBean b){
        try {
            log.info("=========================");
            log.info(b.toString());
            log.info("=========================");
            DbResRight entity = new DbResRight();
            BeanUtils.copyProperties(b, entity);
            log.info(entity.toString());
            repo.<DbResRight>saveAndFlush(entity);
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }
    @RequestMapping(method = RequestMethod.POST,path="/edit")
    public JsonResult Create(@RequestBody EditBean b){
        try {
            log.info("=========================");
            log.info(b.toString());
            log.info("=========================");
            DbResRight entity = new DbResRight();
            BeanUtils.copyProperties(b, entity);
            log.info(entity.toString());
            repo.<DbResRight>saveAndFlush(entity);
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }
}