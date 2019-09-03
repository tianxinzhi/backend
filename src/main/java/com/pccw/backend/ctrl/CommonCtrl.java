package com.pccw.backend.ctrl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.KV;
import com.pccw.backend.entity.DbResRight;
import com.pccw.backend.repository.ResRightRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(methods = RequestMethod.GET,origins = "*", allowCredentials = "false")
public class CommonCtrl {

    @Autowired
    ResRightRepository right_repo;

    @RequestMapping(method = RequestMethod.GET,path="/rightModule")
    public JsonResult<KV> search() {
        try {
            List<DbResRight> list =  right_repo.findByRightPid(0L);
            List<KV> kvList = list.stream().map(r->{
                return new KV(r.getRightName(),r.getId());
                // return new KV().builder().k(r.getRightName()).v(r.getRightPid()).build();
            }).collect(Collectors.toList());
            return JsonResult.success(kvList);
        } catch (Exception e) {
            return JsonResult.fail();
        }
    }
    
}
