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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;


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

    @ApiOperation(value="搜索type",tags={"masterfile_type"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/search")
    public JsonResult search(@RequestBody SearchBean b) {
        try {
            Specification spec = Convertor.convertSpecification(b);
            List<DbResType> res =repo.findAll(spec,PageRequest.of(b.getPageIndex(),b.getPageSize())).getContent();
            ArrayList<SearchBean> dbResTypes = new ArrayList<>();
            if(res != null && res.size() > 0){
                for (DbResType type:res){
                    SearchBean searchBean = new SearchBean();
                    BeanUtils.copyProperties(type, searchBean);
                    if(type.getDbResTypeSkuSpec() != null){
                        searchBean.setSpecId(type.getDbResTypeSkuSpec().getSpecId());
                        Optional<DbResSpec> spec1 = resSpecRepository.findById(type.getDbResTypeSkuSpec().getSpecId());
                        searchBean.setSpecName(spec1.get().getSpecName());
                        searchBean.setAttrData(specSearch(searchBean.getSpecId()).getData());
                    }
                    if(type.getRelationOfTypeClass() != null && type.getRelationOfTypeClass().size() > 0){
                        searchBean.setClassName(type.getRelationOfTypeClass().get(0).getClasss().getClassName());
                        searchBean.setClassId(type.getRelationOfTypeClass().get(0).getClasss().getId());
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

    @ApiOperation(value="删除type",tags={"masterfile_type"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids) {
        return this.delete(repo, ids);
    }

    @ApiOperation(value="创建type",tags={"masterfile_type"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    public JsonResult create(@RequestBody CreateBean b) {
        try {
            //级联添加
//            long[] classIds = b.getClassId();
//            if(classIds != null && classIds.length > 0){
//                for(long cid:classIds){
//                    DbResClass dbResClass = repo.findByClassID(Long.parseLong(cid));
//                    classList.add(dbResClass);
//                }
//            }
            List<DbResClass> classList = new ArrayList<DbResClass>();
            Optional<DbResClass> optional = resClassRepository.findById(b.getClassId());
            DbResClass dbResClass = optional.get();
            classList.add(dbResClass);
            long t = new Date().getTime();
            b.setCreateAt(t);
            b.setUpdateAt(t);
            b.setActive("Y");
            DbResType dbResType = new DbResType();
            BeanUtils.copyProperties(b, dbResType);
            //保存数据到res_type_class表
            List<DbResClassType> classTypeList = new ArrayList<DbResClassType>();
            DbResClassType dbResClassType = new DbResClassType();
            dbResClassType.setClasss(dbResClass);
            dbResClassType.setType(dbResType);
            classTypeList.add(dbResClassType);
            dbResClassType.setCreateAt(t);
            dbResClassType.setUpdateAt(t);
            dbResClassType.setActive("Y");
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
            Optional<DbResClass> optiona2 = resClassRepository.findById(b.getClassId());
            DbResClass dbResClass = optiona2.get();
            DbResClassType dbResClassType = new DbResClassType();
            dbResClassType.setClasss(dbResClass);
            dbResClassType.setType(dbResType);
            dbResClassType.setCreateAt(t);
            dbResClassType.setUpdateAt(t);
            dbResClassType.setActive("Y");
            relationOfTypeClass.add(dbResClassType);
            //更新数据到res_type_sku_spec表
            DbResTypeSkuSpec dbResTypeSkuSpec = dbResType.getDbResTypeSkuSpec();
            if (dbResTypeSkuSpec != null){
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
                for(Map m:attrList){
                    if(m.get("attrValue") != null){
                        String attrValue = m.get("attrValue").toString();
                        List attrValueList = new ArrayList();
                        if(attrValue.contains(",")){
                            attrValueList = Arrays.asList(attrValue.split(","));
                        }else {
                            attrValueList.add(m.get("attrValue"));
                        }
                        HashMap<Object, Object> hm = new HashMap<>();
                        hm.put("attrName",m.get("attrName"));
                        hm.put("attrValue",attrValueList);
                        list.add(hm);
                    }
                }
            return JsonResult.success(list);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }


}