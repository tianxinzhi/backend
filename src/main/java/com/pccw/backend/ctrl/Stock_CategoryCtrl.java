package com.pccw.backend.ctrl;


import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.stock_category.EditBean;
import com.pccw.backend.bean.stock_category.SearchBean;
import com.pccw.backend.entity.DbResSkuRepo;
import com.pccw.backend.repository.ResSkuRepoRepository;
import com.pccw.backend.util.Convertor;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/stock_category")
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
public class Stock_CategoryCtrl extends BaseCtrl<DbResSkuRepo> {

    @Autowired
    private ResSkuRepoRepository repo;

    @ApiOperation(value = "搜索category",tags = "stock_category",notes = "注意问题点")
    @RequestMapping(method = RequestMethod.POST,path = "/search")
    public JsonResult search(@RequestBody SearchBean b){
        try {
            Specification spec = Convertor.convertSpecification(b);
            Sort sort = new Sort(Sort.Direction.DESC,"id");
            List<DbResSkuRepo> res =repo.findAll(spec, PageRequest.of(b.getPageIndex(),b.getPageSize(),sort)).getContent();
            return JsonResult.success(res);
        } catch (Exception e) {
            log.info(e.getMessage());
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value = "修改category",tags = "stock_category",notes = "注意问题点")
    @RequestMapping(method = RequestMethod.POST,path = "/edit")
    public JsonResult edit(@RequestBody EditBean b){
        return this.edit(repo, DbResSkuRepo.class,b);
    }

    @ApiOperation(value = "删除category",tags = "stock_category",notes = "注意问题点")
    @RequestMapping(method = RequestMethod.POST,path = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids){
        return this.delete(repo,ids);
    }


}