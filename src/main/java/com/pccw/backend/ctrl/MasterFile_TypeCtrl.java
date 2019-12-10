package com.pccw.backend.ctrl;


import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.masterfile_type.CreateBean;
import com.pccw.backend.bean.masterfile_type.EditBean;
import com.pccw.backend.bean.masterfile_type.SearchBean;
import com.pccw.backend.entity.*;
import com.pccw.backend.repository.*;
import com.pccw.backend.util.Convertor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * AuthRightCtrl
 */

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST, origins = "*", allowCredentials = "false")
@RequestMapping("/masterfile_type")
@Api(value="MasterFile_TypeCtrl",tags={"masterfile_type"})
public class MasterFile_TypeCtrl extends BaseCtrl<DbResType> {

    @Autowired
    ResTypeRepository repo;
    @Autowired
    ResTypeSkuSpecRepository resTypeSkuSpecRepository;
    @Autowired
    ResClassRepository resClassRepository;
    @Autowired
    ResClassTypeRepository resClassTypeRepository;
    @Autowired
    ResSpecRepository resSpecRepository;
    @Autowired
    ResAccountRepository resAccountRepository;

    @ApiOperation(value="搜索type",tags={"masterfile_type"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/search")
    public JsonResult search(@RequestBody SearchBean b) {
        try {
            Specification spec = Convertor.convertSpecification(b);
            Sort sort = new Sort(Sort.Direction.DESC,"id");
            List<DbResType> res =repo.findAll(spec,PageRequest.of(b.getPageIndex(),b.getPageSize(),sort)).getContent();
            ArrayList<SearchBean> dbResTypes = new ArrayList<>();
            if(!Objects.isNull(res) && res.size() > 0){
                for (DbResType type:res){
                    SearchBean searchBean = new SearchBean();
                    List typeSkuList = repo.searchTypeInSku(type.getId());
                    BeanUtils.copyProperties(type, searchBean);
                    searchBean.setTypeSkuList(typeSkuList);
                    if(type.getDbResTypeSkuSpec() != null){
                        searchBean.setSpecId(type.getDbResTypeSkuSpec().getSpecId());
                        DbResSpec sp = repo.findBySpecId(type.getDbResTypeSkuSpec().getSpecId());
                        searchBean.setSpecName(sp.getSpecName());
                        searchBean.setAttrData(specSearch(searchBean.getSpecId()).getData());
                    }
                    if(type.getRelationOfTypeClass() != null && type.getRelationOfTypeClass().size() > 0){
                        List<DbResClassType> relationOfTypeClass = type.getRelationOfTypeClass();
                        String classNames = "";
                        List classIds = new ArrayList<>();
                        for(DbResClassType ct:relationOfTypeClass){
                            classNames += ct.getClasss().getClassName()+",";
                            classIds.add(ct.getClasss().getId());
                        }
                        if(!StringUtils.isEmpty(classNames)){
                            classNames = classNames.substring(0,classNames.length()-1);
                        }
//                        searchBean.setCreateAccountName(CommonCtrl.searchAccountById(type.getCreateBy(),resAccountRepository));
//                        searchBean.setUpdateAccountName(CommonCtrl.searchAccountById(type.getUpdateBy(),resAccountRepository));
                        searchBean.setClassName(classNames);
                        searchBean.setClassId(classIds);
                    }
                    dbResTypes.add(searchBean);
                }
            }
            return JsonResult.success(dbResTypes);
        } catch (Exception e) {
            log.info(e.getMessage());
            return JsonResult.fail(e);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @ApiOperation(value="删除type",tags={"masterfile_type"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids) {
        return this.delete(repo, ids);
    }

    @Transactional(rollbackOn = Exception.class)
    @ApiOperation(value="创建type",tags={"masterfile_type"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    public JsonResult create(@RequestBody CreateBean b) {
        try {
            long t = new Date().getTime();
            b.setCreateAt(t);
            b.setUpdateAt(t);
            b.setActive("Y");
            DbResType dbResType = new DbResType();
            BeanUtils.copyProperties(b, dbResType);
            //保存数据到res_type_class表
            List<Long> classIds = b.getClassId();
            List<DbResClassType> classTypeList = new ArrayList<DbResClassType>();
            if(!Objects.isNull(classIds) && classIds.size() > 0){
                for(Long cid:classIds){
                    Optional<DbResClass> optional = resClassRepository.findById(cid);
                    DbResClass dbResClass = optional.get();
                    DbResClassType dbResClassType = new DbResClassType();
                    dbResClassType.setClasss(dbResClass);
                    dbResClassType.setType(dbResType);
                    dbResClassType.setCreateAt(t);
                    dbResClassType.setUpdateAt(t);
                    dbResClassType.setActive("Y");
                    classTypeList.add(dbResClassType);
                }
            }
            dbResType.setRelationOfTypeClass(classTypeList);
            //保存数据到res_type_sku_spec表
            DbResTypeSkuSpec dbResTypeSkuSpec = new DbResTypeSkuSpec();
            dbResTypeSkuSpec.setCreateAt(t);
            dbResTypeSkuSpec.setUpdateAt(t);
            dbResTypeSkuSpec.setActive("Y");
            dbResTypeSkuSpec.setType(dbResType);
            dbResTypeSkuSpec.setSpecId(b.getSpecId());
            dbResType.setDbResTypeSkuSpec(dbResTypeSkuSpec);
            repo.saveAndFlush(dbResType);
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @ApiOperation(value="编辑type",tags={"masterfile_type"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/edit")
    public JsonResult edit(@RequestBody EditBean b) {
        try {
            //级联更新
            long t = new Date().getTime();
            Optional<DbResType> optional = repo.findById(b.getId());
            DbResType dbResType = optional.get();
            List<DbResClassType> relationOfTypeClass = dbResType.getRelationOfTypeClass();
            relationOfTypeClass.clear();
            dbResType.setUpdateAt(t);
            dbResType.setTypeCode(b.getTypeCode());
            dbResType.setTypeDesc(b.getTypeDesc());
            dbResType.setTypeName(b.getTypeName());
            //更新数据到res_type_class表
            List<Long> classIds = b.getClassId();
            if(!Objects.isNull(classIds) && classIds.size() > 0){
                for(Long id:classIds){
                    Optional<DbResClass> optiona2 = resClassRepository.findById(id);
                    DbResClass dbResClass = optiona2.get();
                    DbResClassType classType = new DbResClassType();
                    classType.setClasss(dbResClass);
                    classType.setType(dbResType);
                    classType.setCreateAt(t);
                    classType.setUpdateAt(t);
                    classType.setActive("Y");
                    relationOfTypeClass.add(classType);
                }
            }
            //更新数据到res_type_sku_spec表
            DbResTypeSkuSpec dbResTypeSkuSpec = dbResType.getDbResTypeSkuSpec();
            if (!Objects.isNull(dbResTypeSkuSpec)){
                dbResTypeSkuSpec.setUpdateAt(t);
                dbResTypeSkuSpec.setSpecId(b.getSpecId());
            }else{
                DbResTypeSkuSpec dts = new DbResTypeSkuSpec();
                dts.setCreateAt(t);
                dts.setUpdateAt(t);
                dts.setActive("Y");
                dts.setSpecId(b.getSpecId());
                dts.setType(dbResType);
                dbResType.setDbResTypeSkuSpec(dts);
            }
            repo.saveAndFlush(dbResType);
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="搜索spec_attr",tags={"masterfile_type"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/specSearch")
        public JsonResult specSearch(@RequestBody long id) {
        try {
            List<Map> list = new ArrayList<>();
            List<Map> attrList= repo.specSearch(id);
            attrList.stream().collect(Collectors.groupingBy(s -> s.get("attrName")))
                    .forEach((k,v)->{
                            HashMap<Object, Object> hm = new HashMap<>();
                            List<String> attrValueList = new ArrayList<>();
                                v.forEach((a)->{
                                attrValueList.add(a.get("attrValue").toString());
                            });
                            hm.put("attrName",k);
                            hm.put("attrValue",attrValueList);
                            list.add(hm);
                    }
            );
            return JsonResult.success(list);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

}