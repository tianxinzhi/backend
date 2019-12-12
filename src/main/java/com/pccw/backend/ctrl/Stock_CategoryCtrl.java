package com.pccw.backend.ctrl;


import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.stock_category.CreateBean;
import com.pccw.backend.bean.stock_category.CreateBeandtl;
import com.pccw.backend.bean.stock_category.EditBean;
import com.pccw.backend.bean.stock_category.SearchBean;
import com.pccw.backend.entity.DbResRepo;
import com.pccw.backend.entity.DbResSku;
import com.pccw.backend.entity.DbResSkuRepo;
import com.pccw.backend.entity.DbResStockType;
import com.pccw.backend.repository.*;
import com.pccw.backend.util.Convertor;
import com.pccw.backend.util.Session;
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
    @Autowired
    private ResAccountRepository accountRepo;
    @Autowired
    private ResSkuRepository skuRepo;
    @Autowired
    private ResRepoRepository repoRepository;
    @Autowired
    private ResSkuRepoRepository skuRepoRepository;
    @Autowired
    private Session session;

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
                sb.setCreateAccountName(skuRepo.getCreateBy() == 0 ? "system":accountRepo.findById(skuRepo.getCreateBy()).get().getAccountName());
                sb.setUpdateAccountName(skuRepo.getUpdateBy() == 0 ? "system":accountRepo.findById(skuRepo.getUpdateBy()).get().getAccountName());
                if(!Objects.isNull(skuRepo.getStockType())){
                    sb.setStockTypeName(skuRepo.getStockType().getStockTypeName());
                }
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
    public JsonResult edit(@RequestBody CreateBean cb){
        try{
            List<CreateBeandtl> line = cb.getLine();
            line.forEach(l->{
                long t = new Date().getTime();
                DbResSkuRepo sk = skuRepoRepository.findQtyByRepoAndShopAndType(cb.getRepoId(), l.getSkuId(), l.getFromStockTypeId());
                DbResSkuRepo skuRepo = repo.findById(sk.getId()).get();
                DbResStockType dbResStockType = stockTypeRepository.findById(l.getToStockTypeId()).get();
                skuRepo.setUpdateAt(t);
                DbResSkuRepo dbsr = repo.findQtyByRepoAndShopAndType(skuRepo.getRepo().getId(),skuRepo.getSku().getId(),l.getToStockTypeId());
                //修改 From StockCategory
                int qty = skuRepo.getQty() - l.getQty();
                if(qty > 0){
                    skuRepo.setQty(qty);
//                    repo.saveAndFlush(skuRepo);
                }else {
                    repo.deleteById(skuRepo.getId());
                }
                //如果不存在 添加一条 如果存在 直接做QTY加减 To StockCategory
                if(Objects.isNull(dbsr)){
                    DbResSkuRepo toSkuRepo = new DbResSkuRepo();
                    BeanUtils.copyProperties(skuRepo,toSkuRepo);
                    toSkuRepo.setCreateAt(t);
                    toSkuRepo.setStockType(dbResStockType);
                    toSkuRepo.setId(null);
                    toSkuRepo.setQty(l.getQty());
                    repo.saveAndFlush(toSkuRepo);
                }else {
                    DbResSkuRepo skuRepo1 = repo.findById(dbsr.getId()).get();
                    skuRepo1.setQty(dbsr.getQty()+l.getQty());
                    skuRepo1.setUpdateAt(t);
//                    repo.saveAndFlush(skuRepo1);
                }
            });

            return JsonResult.success(Arrays.asList());
        }catch (Exception e){
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value = "根据repoId和skuId搜索stockType",tags = "stock_category",notes = "注意问题点")
    @RequestMapping(method = RequestMethod.POST,path = "/skuStockTypeSearch")
    public JsonResult skuStockTypeSearch(@RequestBody SearchBean sb){
        try {
            ArrayList<Object> returnList = new ArrayList<>();
            DbResSku dbResSku = skuRepo.findById(sb.getSkuId()).get();
            DbResRepo dbResRepo = repoRepository.findById(sb.getRepoId()).get();
            List<DbResSkuRepo> list = repo.findDbResSkuRepoByRepoAndSku(dbResRepo,dbResSku);
            list.forEach(data->{
                HashMap<Object, Object> hashMap = new HashMap<>();
                DbResStockType stockType = data.getStockType();
                hashMap.put("value",stockType.getId());
                hashMap.put("label",stockType.getStockTypeName());
                returnList.add(hashMap);
            });
            return JsonResult.success(returnList);
        } catch (Exception e) {
            log.info(e.getMessage());
            return JsonResult.fail(e);
        }
    }
}