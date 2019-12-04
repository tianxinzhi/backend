package com.pccw.backend.ctrl;


import com.alibaba.fastjson.JSON;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.process.SearchBean;
import com.pccw.backend.entity.DbResProcess;
import com.pccw.backend.repository.ResAccountRepository;
import com.pccw.backend.repository.ResProcessRepository;
import com.pccw.backend.repository.ResRepoRepository;
import com.pccw.backend.util.Convertor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * AuthRightCtrl
 */

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST, origins = "*", allowCredentials = "false")
@RequestMapping("/stock_movement")
@Api(value="Stock_MovementCtrl",tags={"stock_movement"})
public class Stock_Movement extends BaseCtrl<DbResProcess> {

    @Autowired
    ResProcessRepository processRepo;
    @Autowired
    ResAccountRepository accountRepo;
    @Autowired
    ResRepoRepository repoRepo;

    @ApiOperation(value="搜索Stock_Movement",tags={"stock_movement"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/search")
    public JsonResult search(@RequestBody SearchBean b) {
        log.info(b.toString());
        try {
            //默认查询nature为approved的
            b.setStatus(StaticVariable.PROCESS_APPROVED_STATUS);
            Specification spec = Convertor.convertSpecification(b);
            Sort sort = new Sort(Sort.Direction.DESC,"id");
            ArrayList<Map> list = new ArrayList<>();
            List<DbResProcess> res =processRepo.findAll(spec, PageRequest.of(b.getPageIndex(),b.getPageSize(),sort)).getContent();
            res.forEach(p-> {
                Map map = JSON.parseObject(JSON.toJSONString(p), Map.class);
                map.put("repoName",repoRepo.findById(p.getRepoId()).get().getRepoName());
                map.put("createAccountName", CommonCtrl.searchAccountById(p.getCreateBy(), accountRepo));
                list.add(map);
            });
            return JsonResult.success(list);
        } catch (Exception e) {
            log.info(e.getMessage());
            return JsonResult.fail(e);
        }
    }
}
