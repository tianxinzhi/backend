package com.pccw.backend.ctrl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.KV;
import com.pccw.backend.bean.TreeSelect;
import com.pccw.backend.bean.auth_right.SearchBean;
import com.pccw.backend.entity.DbResRight;
import com.pccw.backend.repository.ResRightRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

/**
 * AuthRightCtrl
 */

@Slf4j
@RestController
@RequestMapping("/common")
@CrossOrigin(methods = RequestMethod.GET,origins = "*", allowCredentials = "false")
public class CommonCtrl  {

    @Autowired
    ResRightRepository right_repo;


    @RequestMapping(method = RequestMethod.GET,path="/rightModule")
    public JsonResult<TreeSelect> search() {
        try {
           // List<DbResRight> list =  right_repo.findByRightPid(0L);
            List<DbResRight> list =  right_repo.findAll();
            List<TreeSelect> kvList = list.stream().map(r->{
                return new TreeSelect(r.getId(),r.getRightPid(),r.getRightName());
                // return new KV().builder().k(r.getRightName()).v(r.getRightPid()).build();
            }).collect(Collectors.toList());
            return JsonResult.success(kvList);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }


}
