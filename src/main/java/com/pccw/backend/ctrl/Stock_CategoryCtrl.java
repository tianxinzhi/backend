package com.pccw.backend.ctrl;


import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.stock_category.EditBean;
import com.pccw.backend.bean.stock_category.SearchBean;
import com.pccw.backend.entity.DbResSkuRepo;
import com.pccw.backend.entity.DbResStockType;
import com.pccw.backend.repository.ResSkuRepoRepository;
import com.pccw.backend.repository.ResStockTypeRepository;
import com.pccw.backend.util.Convertor;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;


@Slf4j
@RestController
@RequestMapping("/stock_category")
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
public class Stock_CategoryCtrl extends BaseCtrl<DbResSkuRepo> {

    @Autowired
    private ResSkuRepoRepository repo;
    @Autowired
    private ResStockTypeRepository stockTypeRepository;

    @ApiOperation(value = "搜索category",tags = "stock_category",notes = "注意问题点")
    @RequestMapping(method = RequestMethod.POST,path = "/search")
    public JsonResult search(@RequestBody SearchBean b){
        try {
            List<SearchBean> resultList = new ArrayList<>();
            Sort sort = new Sort(Sort.Direction.DESC,"id");
            List<DbResSkuRepo> res =repo.findAll(PageRequest.of(b.getPageIndex(),b.getPageSize(),sort)).getContent();
            for(DbResSkuRepo skuRepo: res){
                SearchBean sb = new SearchBean();
                BeanUtils.copyProperties(skuRepo,sb);
                sb.setRepoCode(skuRepo.getRepo().getRepoCode());
                sb.setSkuCode(skuRepo.getSku().getSkuCode());
                sb.setStockTypeId(skuRepo.getId());
                sb.setSkuDesc(skuRepo.getSku().getSkuDesc());
                sb.setStockTypeName(skuRepo.getStockType().getStockTypeName());
                if(!Objects.isNull(b.getRepoId()) && !Objects.isNull(b.getSkuId())){
                    if(b.getRepoId() == skuRepo.getRepo().getId() && b.getSkuId() == skuRepo.getSku().getId()){
                        resultList.add(sb);
                    }
                }else if(!Objects.isNull(b.getRepoId()) && Objects.isNull(b.getSkuId())){
                    if(b.getRepoId() == skuRepo.getRepo().getId()){
                        resultList.add(sb);
                    }
                }else if(Objects.isNull(b.getRepoId()) && !Objects.isNull(b.getSkuId())){
                    if(b.getSkuId() == skuRepo.getSku().getId()){
                        resultList.add(sb);
                    }
                }else {
                    resultList.add(sb);
                }
            }
            return JsonResult.success(resultList);
        } catch (Exception e) {
            log.info(e.getMessage());
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value = "修改category",tags = "stock_category",notes = "注意问题点")
    @RequestMapping(method = RequestMethod.POST,path = "/edit")
    @Transactional
    public JsonResult edit(@RequestBody EditBean b){
        try{
            long t = new Date().getTime();
            DbResSkuRepo skuRepo = repo.findById(b.getId()).get();
            DbResStockType dbResStockType = stockTypeRepository.findById(b.getStockTypeIdTo()).get();
            skuRepo.setUpdateAt(t);
            DbResSkuRepo dbsr = repo.findQtyByRepoAndShopAndType(skuRepo.getRepo().getId(),skuRepo.getSku().getId(),b.getStockTypeIdTo());
                //修改 From StockCategory
                int qty = skuRepo.getQty() - b.getQty();
                if(qty > 0){
                    skuRepo.setQty(qty);
//                    repo.saveAndFlush(skuRepo);
                }else {
                    repo.deleteById(skuRepo.getId());
                }
                //添加一条 To StockCategory
                if(Objects.isNull(dbsr)){
                    DbResSkuRepo toSkuRepo = new DbResSkuRepo();
                    BeanUtils.copyProperties(skuRepo,toSkuRepo);
                    toSkuRepo.setCreateAt(t);
                    toSkuRepo.setStockType(dbResStockType);
                    toSkuRepo.setId(null);
                    toSkuRepo.setQty(b.getQty());
                    repo.saveAndFlush(toSkuRepo);
                }else {
                    DbResSkuRepo skuRepo1 = repo.findById(dbsr.getId()).get();
                    skuRepo1.setQty(dbsr.getQty()+b.getQty());
                    skuRepo1.setUpdateAt(t);
//                    repo.saveAndFlush(skuRepo1);
                }
            return JsonResult.success(Arrays.asList());
        }catch (Exception e){
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value = "删除category",tags = "stock_category",notes = "注意问题点")
    @RequestMapping(method = RequestMethod.POST,path = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids){
        return this.delete(repo,ids);
    }
}