package com.pccw.backend.ctrl;

import java.util.Arrays;
import java.util.List;

import com.pccw.backend.bean.authrole.*;
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
@RequestMapping("/common")
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
public class CommonCtrl {


    @RequestMapping(method = RequestMethod.POST,path="/rightModule")
    public JsonResult<String> search(@RequestBody SearchBean sc) {
        try {

            return JsonResult.success(Arrays.asList("role","right","auth"));
        } catch (Exception e) {
            return JsonResult.fail();
        }
    }
    
}