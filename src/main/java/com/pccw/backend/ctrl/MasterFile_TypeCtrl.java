package com.pccw.backend.ctrl;


import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.masterfile_type.CreateBean;
import com.pccw.backend.bean.masterfile_type.EditBean;
import com.pccw.backend.bean.masterfile_type.SearchBean;
import com.pccw.backend.entity.DbResClass;
import com.pccw.backend.entity.DbResClassType;
import com.pccw.backend.entity.DbResType;
import com.pccw.backend.entity.DbResTypeSkuSpec;
import com.pccw.backend.repository.ResClassRepository;
import com.pccw.backend.repository.ResClassTypeRepository;
import com.pccw.backend.repository.ResTypeRepository;
import com.pccw.backend.repository.ResTypeSkuSpecRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @ApiOperation(value="搜索type",tags={"masterfile_type"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/search")
    public JsonResult search(@RequestBody SearchBean b) {
        log.info(b.toString());
        return this.search(repo, b);
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
            List<DbResClass> classList = new ArrayList<DbResClass>();
//            选择多个classId
//            long[] classIds = b.getClassId();
//            if(classIds != null && classIds.length > 0){
//                for(long cid:classIds){
//                    DbResClass dbResClass = repo.findByClassID(Long.parseLong(cid));
//                    classList.add(dbResClass);
//                }
//            }
            Optional<DbResClass> optional = resClassRepository.findById(b.getClassId());
            DbResClass dbResClass = optional.get();
            classList.add(dbResClass);
            long t = new Date().getTime();
            b.setCreateAt(t);
            b.setUpdateAt(t);
            b.setActive("Y");
            DbResType dbResType = new DbResType();
            BeanUtils.copyProperties(b, dbResType);

            List<DbResClassType> classTypeList = new ArrayList<DbResClassType>();
            DbResClassType dbResClassType = new DbResClassType();
            dbResClassType.setClasss(dbResClass);
            dbResClassType.setType(dbResType);
            classTypeList.add(dbResClassType);
            dbResClassType.setCreateAt(t);
            dbResClassType.setUpdateAt(t);
            dbResClassType.setActive("Y");
            dbResType.setRelationOfTypeClass(classTypeList);
            DbResType dbrt = repo.saveAndFlush(dbResType);
            //保存数据到res_type_sku_spec表
            DbResTypeSkuSpec dbResTypeSkuSpec = new DbResTypeSkuSpec();
            dbResTypeSkuSpec.setCreateAt(t);
            dbResTypeSkuSpec.setUpdateAt(t);
            dbResTypeSkuSpec.setActive("Y");
            dbResTypeSkuSpec.setTypeId(dbrt.getId());
            dbResTypeSkuSpec.setSpecId(b.getSpecId());
            resTypeSkuSpecRepository.saveAndFlush(dbResTypeSkuSpec);
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

            Optional<DbResClass> optiona2 = resClassRepository.findById(b.getClassId());
            DbResClass dbResClass = optiona2.get();
            DbResClassType dbResClassType = new DbResClassType();
            dbResClassType.setClasss(dbResClass);
            dbResClassType.setType(dbResType);
            dbResClassType.setCreateAt(t);
            dbResClassType.setUpdateAt(t);
            dbResClassType.setActive("Y");
            relationOfTypeClass.add(dbResClassType);
            DbResType dbrt = repo.saveAndFlush(dbResType);

            //更新数据到res_type_sku_spec表
            DbResTypeSkuSpec dbResTypeSkuSpec = repo.findTssByTypeId(dbrt.getId());
            dbResTypeSkuSpec.setUpdateAt(t);
            dbResTypeSkuSpec.setSpecId(b.getSpecId());
            resTypeSkuSpecRepository.saveAndFlush(dbResTypeSkuSpec);
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
                            attrValueList = new ArrayList<>();
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