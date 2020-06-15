package com.pccw.backend.ctrl;


import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.masterfile_repo.*;
import com.pccw.backend.cusinterface.ICheck;
import com.pccw.backend.entity.DbResRepo;
import com.pccw.backend.entity.DbResSku;
import com.pccw.backend.entity.DbResSkuRepo;
import com.pccw.backend.entity.DbResStockType;
import com.pccw.backend.repository.*;
import com.pccw.backend.util.Convertor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * MF_RepoCtrl
 */

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/stock_curd")
@Api(value="Stock_CURDCtrl",tags={"stock_curd"})
public class Stock_CURDCtrl extends BaseCtrl<DbResSkuRepo> implements ICheck {

    @Autowired
    ResSkuRepository skuRepository;

    @Autowired
    ResStockTypeRepository typeRepository;

    @Autowired
    ResSkuRepoRepository skuRepoRepository;

    @ApiOperation(value="查询shop",tags={"stock_curd"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/search")
    public JsonResult search() {
        try {
            List<Map> valueMap = new LinkedList<>();
            for (DbResSkuRepo skuRepo : skuRepoRepository.findAll()) {
                if(skuRepo.getSku().getId()==null || skuRepo.getStockType().getId() == null) continue;
                Map value = new HashMap();
                Optional<DbResSku> skubyId = skuRepository.findById(skuRepo.getSku().getId());
                value.put("sku",skubyId == null ? "" : skubyId.get().getSkuCode());
                Optional<DbResStockType> typebyId = typeRepository.findById(skuRepo.getStockType().getId());
                value.put("stockType",typebyId == null ? "" : typebyId.get().getStockTypeName());
                value.put("qty",skuRepo.getQty());
                value.put("resQty",skuRepo.getRemark());

                valueMap.add(value);
            }
            return JsonResult.success(valueMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="删除shop",tags={"stock_curd"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids){
        return this.delete(skuRepoRepository,ids);
    }

    @ApiOperation(value="创建shop",tags={"stock_curd"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/create")
    public JsonResult create(@RequestBody StockCreateBean b){
        try {

            long t = new Date().getTime();
            b.setCreateAt(t);
            b.setCreateBy(getAccount());
            b.setUpdateAt(t);
            b.setUpdateBy(getAccount());
            b.setActive("Y");
            DbResSkuRepo resRepo=new DbResSkuRepo();
            BeanUtils.copyProperties(b, resRepo);
            DbResSku skue = new DbResSku();
            skue.setId(b.getSku());
            DbResStockType stockType = new DbResStockType();
            stockType.setId(b.getStockType());
            resRepo.setSku(skue);
            resRepo.setStockType(stockType);
            resRepo.setRemark(b.getResQty().toString());
            skuRepoRepository.saveAndFlush(resRepo);
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="编辑shop",tags={"stock_curd"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/edit")
    public JsonResult edit(@RequestBody StockEditBean b){
        try {
            long t = new Date().getTime();
            b.setUpdateAt(t);
            b.setUpdateBy(getAccount());
            Optional<DbResSkuRepo> optional = skuRepoRepository.findById(b.getId());
            DbResSkuRepo dbResRepo = optional.get();
            b.setActive(dbResRepo.getActive());
            b.setCreateAt(dbResRepo.getCreateAt());
            b.setCreateBy(dbResRepo.getCreateBy());
            DbResSkuRepo resRepo=new DbResSkuRepo();
            BeanUtils.copyProperties(b, resRepo);
            DbResSku skue = new DbResSku();
            skue.setId(b.getSku());
            DbResStockType stockType = new DbResStockType();
            stockType.setId(b.getStockType());
            resRepo.setSku(skue);
            resRepo.setStockType(stockType);
            resRepo.setRemark(b.getResQty().toString());
            skuRepoRepository.saveAndFlush(resRepo);
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
    }

//    @ApiOperation(value="禁用repo",tags={"stock_curd"},notes="注意问题点")
//    @RequestMapping(method = RequestMethod.POST,value = "/disable")
//    public JsonResult disable(@RequestBody BaseDeleteBean ids) {
//        return this.disable(repo,ids, Stock_CURDCtrl.class,skuRepoRepository);
//    }

//    @ApiOperation(value="启用repo",tags={"stock_curd"},notes="注意问题点")
//    @RequestMapping(method = RequestMethod.POST,value = "/enable")
//    public JsonResult enable(@RequestBody BaseDeleteBean ids) {
//        return this.enable(repo,ids);
//    }

    @Override
    public long checkCanDisable(Object obj, BaseRepository... check) {
        ResSkuRepoRepository tRepo = (ResSkuRepoRepository)check[0];
        BaseDeleteBean bean = (BaseDeleteBean)obj;
        for (Long id : bean.getIds()) {
            DbResRepo repo = new DbResRepo();
            repo.setId(id);
            List<DbResSkuRepo> skuRepos = tRepo.getDbResSkuReposByRepo(repo);
            if ( skuRepos != null && skuRepos.size()>0 ) {
                return id;
            }
        }
        return 0;
    }
}
