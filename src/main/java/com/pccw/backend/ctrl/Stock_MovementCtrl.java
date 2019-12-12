package com.pccw.backend.ctrl;


import com.alibaba.fastjson.JSON;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.stock_movement.SearchBean;
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

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * AuthRightCtrl
 */

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST, origins = "*", allowCredentials = "false")
@RequestMapping("/stock_movement")
@Api(value="Stock_MovementCtrl",tags={"stock_movement"})
public class Stock_MovementCtrl extends BaseCtrl<DbResProcess> {

    @Autowired
    ResProcessRepository processRepo;
    @Autowired
    ResAccountRepository accountRepo;
    @Autowired
    ResRepoRepository repoRepo;
    @Autowired
    CommonCtrl commonCtrl;

    @ApiOperation(value="搜索Stock_Movement",tags={"stock_movement"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/search")
    public JsonResult search(@RequestBody SearchBean b) {
        log.info(b.toString());
        try {
            //默认查询nature为approved的
            b.setStatus(StaticVariable.PROCESS_APPROVED_STATUS);
            //准备between需要的日期范围条件
            String[] createAt = b.getCreateAt();
            if(Objects.nonNull(createAt)){
                if(createAt.length == 2){
                    List<Object> objects = new ArrayList<>();
                    for (String s : createAt) {
                        long time = new SimpleDateFormat("yyyy-MM-dd").parse(s).getTime();
                        objects.add(String.valueOf(time));
                    }
                        String [] a = new String[objects.size()];
                        b.setCreateAt(objects.toArray(a));
                }else {
                    b.setCreateAt(null);
                }
            }
            Specification spec = Convertor.convertSpecification(b);
            Sort sort = new Sort(Sort.Direction.DESC,"id");
            ArrayList<Map> list = new ArrayList<>();
            List<DbResProcess> res =processRepo.findAll(spec, PageRequest.of(b.getPageIndex(),b.getPageSize(),sort)).getContent();
            res.forEach(p-> {
                Map map = JSON.parseObject(JSON.toJSONString(p), Map.class);
                map.put("repoName",repoRepo.findById(p.getRepoId()).get().getRepoName());
                String createAccountName = p.getCreateBy() == 0 ? "system":accountRepo.findById(p.getCreateBy()).get().getAccountName();
                map.put("createAccountName",createAccountName);
                list.add(map);
            });
            return JsonResult.success(list);
        } catch (Exception e) {
            log.info(e.getMessage());
            return JsonResult.fail(e);
        }
    }
}
