package com.pccw.backend.ctrl;


import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.stock_balance.SearchBean;
import com.pccw.backend.bean.stock_balance.StockCreateBean;
import com.pccw.backend.bean.stock_balance.StockEditBean;
import com.pccw.backend.cusinterface.ICheck;
import com.pccw.backend.entity.DbResRepo;
import com.pccw.backend.entity.DbResSku;
import com.pccw.backend.entity.DbResSkuRepo;
import com.pccw.backend.entity.DbResStockType;
import com.pccw.backend.repository.*;
import com.pccw.backend.util.Convertor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

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
    ResRepoRepository shopRepository;

    @Autowired
    ResStockTypeRepository typeRepository;

    @Autowired
    ResSkuRepoRepository skuRepoRepository;

    @Autowired
    EntityManager manager;

    @ApiOperation(value="查询shop",tags={"stock_curd"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/search")
    public JsonResult search(@RequestBody SearchBean bean) {
        try {

            StringBuffer baseSql = new StringBuffer("select r.sku_id \"skuId\",r.repo_id \"repoId\", s.sku_code \"sku\" ," +
                    "s.sku_desc \"skuDesc\" ,t1.repo_code \"shop\", \n" +
                    " sum(nvl(decode(r.stock_type_id ,1,r.QTY),0)) \"staging\",\n" +
                    " sum(nvl(decode(r.stock_type_id ,2,r.QTY),0)) \"faulty\",\n" +
                    " sum(nvl(decode(r.stock_type_id ,3,r.QTY),0)) \"fg\",\n" +
                    " sum(nvl(decode(r.stock_type_id ,4,r.QTY),0)) \"reserve\",\n" +
                    " sum(nvl(decode(r.stock_type_id ,5,r.QTY),0)) \"intransit\",\n" +
                    " sum(nvl(decode(r.stock_type_id ,6,r.QTY),0)) \"rao\",\n" +
                    " sum(nvl(decode(r.stock_type_id ,7,r.QTY),0)) \"rro\" from res_sku_repo r\n" +
                    "left join res_sku s on r.sku_id = s.id left join res_repo t1 on r.repo_id = t1.id\n" +
                    " where 1=1 " );

            if (bean.getSkuNum() != null && bean.getSkuNum().size()>0) {
                baseSql.append(" and r.sku_id in(");
                for (String s : bean.getSkuNum()) {
                    baseSql.append( "'"+s+"',");
                }
                baseSql.deleteCharAt(baseSql.length()-1);
                baseSql.append(")");
            }

            if(bean.getSkuDesc()!=null && !bean.getSkuDesc().equals("")){
                baseSql.append(" and s.sku_desc like '%"+bean.getSkuDesc()+"%'");
            }
            if(bean.getShopId()!=0){
                baseSql.append(" and r.repo_id = "+bean.getShopId());
            }
            if(bean.getStockTypeId()!=0){
                baseSql.append(" and r.stock_type_id = "+bean.getStockTypeId());
            }
            baseSql.append(" GROUP BY r.sku_id,r.repo_id,s.sku_code ,s.sku_desc ,t1.repo_code" +
                    " ORDER BY t1.repo_code desc, s.sku_code desc ");

            Query dataQuery = manager.createNativeQuery(baseSql.toString());
            dataQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            return JsonResult.success(dataQuery.getResultList());
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
            DbResRepo shop = new DbResRepo();
            shop.setId(b.getStore());
            resRepo.setRepo(shop);
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
            DbResRepo shop = new DbResRepo();
            shop.setId(b.getStore());
            resRepo.setRepo(shop);
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
