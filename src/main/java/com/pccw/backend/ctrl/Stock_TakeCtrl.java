package com.pccw.backend.ctrl;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.ResultRecode;
import com.pccw.backend.bean.stock_take.*;
import com.pccw.backend.entity.DbResRepo;
import com.pccw.backend.entity.DbResStockTake;
import com.pccw.backend.repository.ResRepoRepository;
import com.pccw.backend.repository.ResSkuRepoRepository;
import com.pccw.backend.repository.ResSkuRepository;
import com.pccw.backend.repository.ResStockTakeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: ChenShuCheng
 * @create: 2020-07-30 10:54
 **/
@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/stock_take")
public class Stock_TakeCtrl extends BaseCtrl<DbResStockTake>{

    @Autowired
    ResStockTakeRepository stockTakeRepository;

    @Autowired
    ResSkuRepoRepository skuRepoRepository;

    @Autowired
    ResRepoRepository repoRepository;

    @RequestMapping(method = RequestMethod.POST,path = "/search")
    public JsonResult search(@RequestBody SearchBean b){

        try {
            log.info(b.toString());
            JsonResult<DbResStockTake> jsonResult = this.search(stockTakeRepository, b);
            List<SearchVO> result = jsonResult.getData().stream().map(entity -> {
                SearchVO searchVO = new SearchVO();
                BeanUtils.copyProperties(entity, searchVO);
                DbResRepo repo = repoRepository.findById(entity.getChannelId()).get();
                searchVO.setChannelCode(repo.getRepoCode());
                return searchVO;
            }).collect(Collectors.toList());
            return JsonResult.success(result);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @RequestMapping(method = RequestMethod.POST,path = "/searchSkuQty")
    public JsonResult searchSkuQty(@Validated @RequestBody SearchQtyBean b){

        try {
            List<Map<String,Object>> currentQty = skuRepoRepository.findCurrentQty(b.getChannelId(), b.getSkuIds());
            List<Map<String, Object>> result = ResultRecode.returnHumpNameForList(currentQty);
            return JsonResult.success(result);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }


    @RequestMapping(method = RequestMethod.POST,path = "/create")
    public JsonResult create(@RequestBody CreateBean b){

        try {
           return this.create(stockTakeRepository,DbResStockTake.class,b);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @RequestMapping(method = RequestMethod.POST,path = "/edit")
    public JsonResult edit(@RequestBody EditBean b){

        try {
            return this.edit(stockTakeRepository,DbResStockTake.class,b);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

}
