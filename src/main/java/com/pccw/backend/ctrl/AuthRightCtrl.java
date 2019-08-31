package com.pccw.backend.ctrl;


import java.util.Arrays;
import java.util.List;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.authright.CreateBean;
import com.pccw.backend.bean.authright.EditBean;
// import com.pccw.backend.bean.authright.*;
import com.pccw.backend.bean.authright.SearchBean;
import com.pccw.backend.entity.DbResRight;
import com.pccw.backend.repository.BaseRepository;
import com.pccw.backend.repository.ResRightRepository;
import com.pccw.backend.util.Convertor;
import com.pccw.backend.bean.BaseSearchBean;
import com.pccw.backend.bean.DeleteBean;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class AuthRightCtrl extends BaseCtrl<DbResRight>{

    @Autowired
    ResRightRepository repo;

    @RequestMapping(method = RequestMethod.POST,path="/search")
    public JsonResult search(@RequestBody SearchBean b) {
        log.info(b.toString());
        return this.search(repo, SearchBean.class, b);
        // return JsonResult.fail();
    }

    @RequestMapping(method = RequestMethod.POST,path = "/delete")
    public JsonResult delete(@RequestBody DeleteBean ids){
        return this.delete(repo,ids);
    }

    @RequestMapping(method = RequestMethod.POST,path="/create")
    public JsonResult create(@RequestBody CreateBean b){
        return this.create(repo, DbResRight.class, b);
    }
    @RequestMapping(method = RequestMethod.POST,path="/edit")
    public JsonResult edit(@RequestBody EditBean b){
        return this.edit(repo, DbResRight.class, b);
    }
}