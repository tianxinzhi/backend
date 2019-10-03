package com.pccw.backend.ctrl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.pccw.backend.bean.CommonBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.KV;
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


    //?
    @RequestMapping(method = RequestMethod.GET,path="/rightModule")
    public JsonResult<CommonBean> search() {
        try {
            List<DbResRight> list =  right_repo.findAll();
            List<CommonBean> res = list.stream().map(r->{
                return new CommonBean(r.getId(),r.getRightPid(),r.getRightName());
            }).collect(Collectors.toList());
            res.add(0, new CommonBean(0L, -1L, "Top"));
            res.add(0, new CommonBean(1L, 0L, "Left"));
            res.add(0, new CommonBean(2L, 0L, "Right"));
            return JsonResult.success(res);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }


}
