package com.pccw.backend.ctrl;

import com.alibaba.fastjson.JSONObject;
import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.LabelAndValue;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.masterfile_sku.CreateBean;
import com.pccw.backend.bean.masterfile_sku.EditBean;
import com.pccw.backend.bean.masterfile_sku.ResultBean;
import com.pccw.backend.bean.masterfile_sku.SearchBean;
import com.pccw.backend.cusinterface.ICheck;
import com.pccw.backend.entity.*;
import com.pccw.backend.repository.*;
import com.pccw.backend.util.Convertor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author xiaozhi
 */
@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("masterfile_sku")
@Api(value="MasterFile_SkuCtrl",tags={"masterfile_sku"})
public class MasterFile_SkuCtrl extends BaseCtrl<DbResSku> implements ICheck {

    @Autowired
    ResSkuRepository skuRepo;

    @Autowired
    ResSkuTypeRepository skuTypeRepo;

    @Autowired
    ResAttrRepository attrRepo;

    @Autowired
    ResStockTypeRepository stockTypeRepository;

    @Autowired
    ResSkuRepoRepository skuRepoRepository;

    @Autowired
    ResSkuLisRepository skuLisRepository;

    @Autowired
    ResTypeSkuSpecRepository typeSkuSpecRepository;

    @Autowired
    ResSpecRepository specRepository;

    @ApiOperation(value="创建sku",tags={"masterfile_sku"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/create")
    public JsonResult create(@RequestBody CreateBean bean) {
        try {
            System.out.println(JSONObject.toJSONString(bean));
            long time = System.currentTimeMillis();
            DbResSku sku = new DbResSku();
            sku.setSkuCode(bean.getSkuCode());
            sku.setSkuDesc(bean.getSkuDesc());
            sku.setSkuName(bean.getSkuName());
            sku.setActive("Y");
            sku.setCreateAt(time);
            sku.setUpdateAt(time);
            sku.setCreateBy(getAccount());
            sku.setUpdateBy(getAccount());
            sku.setSkuOrigin(StaticVariable.SKU_ORIGIN_FROM_WITHPO);


            sku.setStartDateActive(bean.getStartDate() == null ? 0L : bean.getStartDate());
            sku.setEndDateActive(bean.getEndDate() == null ? 0L : bean.getEndDate());

            sku.setReturnableFlag(bean.getReturnable()?"Y":"N");
            sku.setInventoryAssetFlag(bean.getInventoryAssetFlag()?"I":"");
            sku.setMaxReserveDays(bean.getMaxReserveDays()==null?"0":bean.getMaxReserveDays());
            if (bean.getTangible()) {
                sku.setTangibleItem("Y");
                sku.setIntangibleItem("N");
            }else {
                sku.setTangibleItem("N");
                sku.setIntangibleItem("Y");
            }

            DbResType type = new DbResType();
            type.setId(bean.getType());
            List<DbResTypeSkuSpec> typeSkuSpec = typeSkuSpecRepository.getDbResTypeSkuSpecsByType(type);
            if(typeSkuSpec!=null && typeSkuSpec.size()>0){
                DbResSpec spec = specRepository.findById(typeSkuSpec.get(0).getSpecId()).get(0);
                DbResSpec newSpec = new DbResSpec();
                newSpec.setSpecName(spec.getSpecName());
                newSpec.setSpecDesc(spec.getSpecDesc());
                newSpec.setVerId(spec.getVerId());
                newSpec.setCreateAt(time);
                newSpec.setUpdateBy(getAccount());
                newSpec.setUpdateAt(time);
                newSpec.setUpdateBy(getAccount());
                newSpec.setActive("Y");

                List<DbResSpecAttr> specAttrList = new LinkedList<>();
                for(int i=0;i<bean.getAttrs().length;i++){
                    for(int value : bean.getAttrValueList().get(i)){
                        DbResSpecAttr specAttr = new DbResSpecAttr();
                        specAttr.setAttrId(String.valueOf(bean.getAttrs()[i]));
                        specAttr.setAttrValueId(String.valueOf(value));
                        specAttr.setActive("Y");
                        specAttr.setCreateAt(time);
                        specAttr.setUpdateAt(time);
                        specAttr.setCreateBy(getAccount());
                        specAttr.setUpdateBy(getAccount());
                        specAttrList.add(specAttr);
                    }
                }
                newSpec.setResSpecAttrList(specAttrList);
                specRepository.saveAndFlush(newSpec);

                List<DbResSpec> dbResSpecsBySpecName = specRepository.getDbResSpecsBySpecName(spec.getSpecName());
                DbResSpec maxSpec = dbResSpecsBySpecName.stream().max(Comparator.comparing(DbResSpec::getId)).get();
                List<Long> specIds = Arrays.asList(bean.getSpecChecked());
                List<Long> specIdList = new ArrayList(specIds);
                specIdList.add(maxSpec.getId());
                List<DbResTypeSkuSpec> typeSkuSpecs = new ArrayList<>();
                for (Long id : specIdList) {

                    DbResTypeSkuSpec newTypeSpec = new DbResTypeSkuSpec();
                    //BeanUtils.copyProperties(typeSkuSpec,newTypeSpec);
//                    newTypeSpec.setSpecId(maxSpec.getId());
                    newTypeSpec.setSpecId(id);
                    newTypeSpec.setType(typeSkuSpec.get(0).getType());
                    newTypeSpec.setSku(sku);
                    newTypeSpec.setIsType("N");
                    newTypeSpec.setCreateAt(time);
                    newTypeSpec.setUpdateBy(getAccount());
                    newTypeSpec.setUpdateAt(time);
                    newTypeSpec.setUpdateBy(getAccount());
                    newTypeSpec.setActive("Y");
                    typeSkuSpecs.add(newTypeSpec);
                }
//                sku.setDbResTypeSkuSpec(newTypeSpec);
                sku.setDbResTypeSkuSpec(typeSkuSpecs);

                skuRepo.saveAndFlush(sku);
            }
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="删除sku",tags={"masterfile_sku"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids) {
        for (Long id : ids.getIds()) {
            DbResSku sku = new DbResSku();
            sku.setId(id);
            DbResSkuLis skuLisBySkuId = skuLisRepository.getDbResSkuLisBySkuId(sku);
            if(skuLisBySkuId != null) skuLisRepository.delete(skuLisBySkuId);
            //删除此sku的spec
            DbResTypeSkuSpec dbResTypeSkuSpecBySku = typeSkuSpecRepository.getDbResTypeSkuSpecBySku(sku);
            DbResSpec spec = specRepository.getOne(dbResTypeSkuSpecBySku.getSpecId());
            if(spec!=null) specRepository.delete(spec);
        }
        return this.delete(skuRepo,ids);
    }

    @ApiOperation(value="修改sku",tags={"masterfile_sku"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/edit")
    public JsonResult edit(@RequestBody EditBean bean) {
        try {
            System.out.println("bean:" + bean);
            long time = System.currentTimeMillis();
            DbResSku sku = skuRepo.findById(bean.getId()).get();
            sku.setSkuCode(bean.getSkuCode());
            sku.setSkuName(bean.getSkuName());
            sku.setSkuDesc(bean.getSkuDesc());
            sku.setUpdateAt(time);
            sku.setUpdateBy(getAccount());
            sku.setSkuOrigin(StaticVariable.SKU_ORIGIN_FROM_WITHPO);
            DbResType type = new DbResType();
            type.setId(bean.getType());
            DbResTypeSkuSpec typeSkuSpec = typeSkuSpecRepository.getDbResTypeSkuSpecsBySkuAndType(sku,type);
            if(typeSkuSpec!=null){
                //未修改type，只是修改了spec的attr
                DbResSpec spec = specRepository.findById(typeSkuSpec.getSpecId()).get(0);
                List<DbResSpecAttr> resSpecAttrList = spec.getResSpecAttrList();
                resSpecAttrList.clear();
                for(int i=0;i<bean.getAttrs().length;i++){
                    for(int value : bean.getAttrValueList().get(i)){
                        DbResSpecAttr specAttr = new DbResSpecAttr();
                        specAttr.setAttrId(String.valueOf(bean.getAttrs()[i]));
                        specAttr.setAttrValueId(String.valueOf(value));
                        specAttr.setActive("Y");
                        specAttr.setCreateAt(time);
                        specAttr.setUpdateAt(time);
                        specAttr.setCreateBy(getAccount());
                        specAttr.setUpdateBy(getAccount());
                        resSpecAttrList.add(specAttr);
                    }
                }
                specRepository.saveAndFlush(spec);
            } else {
                //选了新的type，创建新的spec,删除之前的spec
                List<DbResTypeSkuSpec> typeSkuSpecs = typeSkuSpecRepository.getDbResTypeSkuSpecsByType(type);
                if(typeSkuSpecs!=null && typeSkuSpecs.size()>0){
                    DbResSpec oldSpec = specRepository.getOne(typeSkuSpecs.get(0).getSpecId());
                    DbResSpec newSpec = new DbResSpec();
                    newSpec.setSpecName(oldSpec.getSpecName());
                    newSpec.setSpecDesc(oldSpec.getSpecDesc());
                    newSpec.setVerId(oldSpec.getVerId());
                    newSpec.setCreateAt(time);
                    newSpec.setUpdateBy(getAccount());
                    newSpec.setUpdateAt(time);
                    newSpec.setUpdateBy(getAccount());
                    newSpec.setActive("Y");

                    List<DbResSpecAttr> specAttrList = new LinkedList<>();
                    for(int i=0;i<bean.getAttrs().length;i++){
                        for(int value : bean.getAttrValueList().get(i)){
                            DbResSpecAttr specAttr = new DbResSpecAttr();
                            specAttr.setAttrId(String.valueOf(bean.getAttrs()[i]));
                            specAttr.setAttrValueId(String.valueOf(value));
                            specAttr.setActive("Y");
                            specAttr.setCreateAt(time);
                            specAttr.setUpdateAt(time);
                            specAttr.setCreateBy(getAccount());
                            specAttr.setUpdateBy(getAccount());
                            specAttrList.add(specAttr);
                        }
                    }
                    newSpec.setResSpecAttrList(specAttrList);

                    DbResTypeSkuSpec dbResTypeSkuSpecBySku = typeSkuSpecRepository.getDbResTypeSkuSpecBySku(sku);
                    typeSkuSpecRepository.delete(dbResTypeSkuSpecBySku);
                    specRepository.delete(oldSpec);
                    specRepository.saveAndFlush(newSpec);

                    List<DbResSpec> dbResSpecsBySpecName = specRepository.getDbResSpecsBySpecName(oldSpec.getSpecName());
                    DbResSpec maxSpec = dbResSpecsBySpecName.stream().max(Comparator.comparing(DbResSpec::getId)).get();
                    DbResTypeSkuSpec newTypeSpec = new DbResTypeSkuSpec();
                    //BeanUtils.copyProperties(typeSkuSpec,newTypeSpec);
                    newTypeSpec.setSpecId(maxSpec.getId());
                    newTypeSpec.setType(typeSkuSpecs.get(0).getType());
                    newTypeSpec.setSku(sku);
                    newTypeSpec.setIsType("N");
                    newTypeSpec.setCreateAt(time);
                    newTypeSpec.setUpdateBy(getAccount());
                    newTypeSpec.setUpdateAt(time);
                    newTypeSpec.setUpdateBy(getAccount());
                    newTypeSpec.setActive("Y");
//                    sku.setDbResTypeSkuSpec(newTypeSpec);
                }
            }

            skuRepo.saveAndFlush(sku);

            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="搜索sku",tags={"masterfile_sku"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/search")
    public JsonResult search(@RequestBody SearchBean bean){
        try {
            Specification<DbResSku> spec = Convertor.<DbResSku>convertSpecification(bean);
            List<Sort.Order> orders = new ArrayList<>();
            orders.add(new Sort.Order(Sort.Direction.DESC,"createAt"));
            List<DbResSku> skuList = skuRepo.findAll(spec, PageRequest.of(bean.getPageIndex(),bean.getPageSize(),Sort.by(orders))).getContent();
            List<ResultBean> skuResultBeans = new LinkedList<>();
            for (DbResSku sku : skuList) {

                EditBean ebean = new EditBean();
                ebean.setId(sku.getId());
                JsonResult typeResult =  skuSearch(ebean);
                //前端详情展示attr
                List<Map> attrDataMap = new LinkedList<>();
                //sku组件数据结构
                List<Map> tableDatas = new LinkedList<>();
                if(typeResult.getData()!=null && typeResult.getData().size()>0) {
                    ResultBean resultBean = (ResultBean) typeResult.getData().get(0);
                    BeanUtils.copyProperties(sku,resultBean);
                    resultBean.setCreateAccountName(getAccountName(sku.getCreateBy()));
                    resultBean.setUpdateAccountName(getAccountName(sku.getUpdateBy()));
                    for (int i=0;i<resultBean.getAttrNames().length;i++) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("attrName",resultBean.getAttrNames()[i]);
                        map.put("attrValue",resultBean.getAttrValueNames().get(i));

                        attrDataMap.add(map);

                        DbResAttr attr = resultBean.getAttrs()[i] == 0? null:attrRepo.findById(resultBean.getAttrs()[i]).get();
                        //获取attr的attrvalue，加入sku组件可选attr列表
                        List<LabelAndValue> lbs = new LinkedList<>();
                        if(attr!=null) {
                            for(int k=0;k<attr.getAttrAttrValueList().size();k++) {
                                DbResAttrValue value = attr.getAttrAttrValueList().get(k).getAttrValue();
                                LabelAndValue lb = new LabelAndValue(value.getId(),value.getAttrValue()!=null?value.getAttrValue():value.getValueFrom()+"~"+value.getValueTo(),null);
                                lbs.add(lb);
                            }
                        }
                        //构造sku组件数据结构
                        Map tableData = new HashMap();
                        tableData.put("value",resultBean.getAttrValues().get(i));
                        tableData.put("title",resultBean.getAttrNames()[i]);
                        tableData.put("name",resultBean.getAttrNames()[i]);
                        tableData.put("linkValue",resultBean.getAttrs()[i]);
                        tableData.put("selectMode","tags");
                        tableData.put("type",10);
                        tableData.put("options",lbs);

                        tableDatas.add(tableData);
                    }
                    Map tableMutiData = new HashMap();
                    tableMutiData.put("titleName",resultBean.getSpecName());
                    tableMutiData.put("headName",new String[]{"Attr","AttrValue"});
                    tableMutiData.put("tableData",tableDatas);

                    resultBean.setTableMutiData(tableMutiData);
                    resultBean.setAttrData(attrDataMap);
                    skuResultBeans.add(resultBean);
                }
            }
            return  JsonResult.success(skuResultBeans,skuRepo.count(spec));
        } catch (IllegalArgumentException | IllegalAccessException | BeansException e) {
            e.printStackTrace();
            return  JsonResult.fail(e);
        }
    }

    @ApiOperation(value="type下拉框",tags={"masterfile_sku"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/typeSearch")
    public JsonResult typeSearch(@RequestBody EditBean bean) {

        try {
            List<Map> specs = skuRepo.getAllSpecsByType(bean.getId());
            List<ResultBean> skuResultBeans = new LinkedList<>();

            long[] attrs = new long[specs.size()];
            String[] attrNames = new String[specs.size()];
            List<String[]> attrValues = new LinkedList<>();
            List<String[]> attrValueNames = new LinkedList<>();
            List<Map> tableDatas = new LinkedList<>();
            if(specs!=null && specs.size()>0){
                for (int i = 0; i < specs.size(); i++) {
                    attrs[i] = specs.get(i).get("attr") == null ? 0:Long.valueOf(specs.get(i).get("attr").toString());
                    attrNames[i] = specs.get(i).get("attrName") == null ? "":specs.get(i).get("attrName").toString();
                    String[] attrValueName = specs.get(i).get("attrValueName") == null ? null:StringUtils.commaDelimitedListToStringArray(specs.get(i).get("attrValueName").toString());
                    String[] attrValue = specs.get(i).get("attrValue") == null ? null:StringUtils.commaDelimitedListToStringArray(specs.get(i).get("attrValue").toString());
                    attrValues.add(attrValue);
                    attrValueNames.add(attrValueName);

                    //获取attr相关的attrValue
                    DbResAttr attr = attrs[i] == 0 ? null:attrRepo.findById(attrs[i]).get();
                    List<LabelAndValue> lbs = new LinkedList<>();
                    if(attr!=null) {
                        for(int k=0;k<attr.getAttrAttrValueList().size();k++) {
                            DbResAttrValue value = attr.getAttrAttrValueList().get(k).getAttrValue();
                            LabelAndValue lb = new LabelAndValue(value.getId(),value.getAttrValue()!=null?value.getAttrValue():value.getValueFrom()+"~"+value.getValueTo(),null);
                            lbs.add(lb);
                        }
                    }
                    //构造sku组件数据结构
                    Map tableData = new HashMap();
                    tableData.put("value",attrValue);
                    tableData.put("title",attrNames[i]);
                    tableData.put("name",attrNames[i]);
                    tableData.put("linkValue",attrs[i]);
                    tableData.put("selectMode","tags");
                    tableData.put("type",10);
                    tableData.put("options",lbs);
                    tableData.put("disable",true);
                    tableDatas.add(tableData);
                }

                Map tableMutiData = new HashMap();
                tableMutiData.put("titleName",specs.get(0).get("specName") == null ? "":specs.get(0).get("specName").toString());
                tableMutiData.put("headName",new String[]{"Attr","AttrValue"});
                tableMutiData.put("tableData",tableDatas);

                ResultBean skuResult = new ResultBean();
                skuResult.setType(specs.get(0).get("type") == null ? 0:Long.parseLong(specs.get(0).get("type").toString()));
                skuResult.setTypeName(specs.get(0).get("typeName") == null ? "":specs.get(0).get("typeName").toString());
                skuResult.setSpecName(specs.get(0).get("specName") == null ? "":specs.get(0).get("specName").toString());
                skuResult.setSpec(specs.get(0).get("spec") == null ? 0:Long.parseLong(specs.get(0).get("spec").toString()));
                skuResult.setAttrs(attrs);
                skuResult.setAttrNames(attrNames);
                skuResult.setAttrValues(attrValues);
                skuResult.setAttrValueNames(attrValueNames);
                skuResult.setTableMutiData(tableMutiData);

                skuResultBeans.add(skuResult);
            }
            return JsonResult.success(skuResultBeans);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="sku弹出框",tags={"masterfile_sku"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/skuSearch")
    public JsonResult skuSearch(@RequestBody EditBean bean) {
        try {
            System.out.println("bean:" + bean);
            List<Map> types = skuRepo.getTypeDtlsBySku(bean.getId());
            List<ResultBean> skuResultBeans = new LinkedList<>();

            long[] attrs = new long[types.size()];
            String[] attrNames = new String[types.size()];
            List<String[]> attrValueNames = new LinkedList<>();
            List<String[]> attrValues = new LinkedList<>();
            if(types!=null&&types.size()>0){
                for (int i = 0; i < types.size(); i++) {

                    attrs[i] = types.get(i).get("attr") == null ? 0:Long.valueOf(types.get(i).get("attr").toString());
                    attrNames[i] = types.get(i).get("attrName") == null ? "":types.get(i).get("attrName").toString();
                    String[] attrValueName = types.get(i).get("attrValueName") == null ? null:StringUtils.commaDelimitedListToStringArray(types.get(i).get("attrValueName").toString());
                    String[] attrValue = types.get(i).get("attrValue") == null ? null:StringUtils.commaDelimitedListToStringArray(types.get(i).get("attrValue").toString());

                    attrValues.add(attrValue);
                    attrValueNames.add(attrValueName);
                }

                ResultBean skuResult = new ResultBean();
                skuResult.setType(types.get(0).get("type") == null ? 0:Long.parseLong(types.get(0).get("type").toString()));
                skuResult.setTypeName(types.get(0).get("typeName") == null ? "":types.get(0).get("typeName").toString());
                skuResult.setSpecName(types.get(0).get("specName") == null ? "":types.get(0).get("specName").toString());
                skuResult.setSpec(types.get(0).get("spec") == null ? 0:Long.parseLong(types.get(0).get("spec").toString()));
//                skuResult.setStores(types.get(0).get("store")==null ? 0:Long.parseLong(types.get(0).get("store").toString()));
//                skuResult.setStoreCodes(types.get(0).get("storeCode")==null ? "":types.get(0).get("storeCode").toString());
//                skuResult.setQty(types.get(0).get("qty") == null ? 0:Long.parseLong(types.get(0).get("qty").toString()));
                skuResult.setAttrs(attrs);
                skuResult.setAttrNames(attrNames);
                skuResult.setAttrValues(attrValues);
                skuResult.setAttrValueNames(attrValueNames);
                skuResultBeans.add(skuResult);
            }
            return JsonResult.success(skuResultBeans);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="禁用sku",tags={"masterfile_sku"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/disable")
    public JsonResult disable(@RequestBody BaseDeleteBean ids) {
        return this.disable(skuRepo,ids, MasterFile_SkuCtrl.class,skuRepoRepository);
    }

    @ApiOperation(value="启用sku",tags={"masterfile_sku"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/enable")
    public JsonResult enable(@RequestBody BaseDeleteBean ids) {
        return this.enable(skuRepo,ids);
    }

    @Override
    public long checkCanDisable(Object obj, BaseRepository... check) {
        ResSkuRepoRepository tRepo = (ResSkuRepoRepository)check[0];
        BaseDeleteBean bean = (BaseDeleteBean)obj;
        for (Long id : bean.getIds()) {
            DbResSku sku = new DbResSku();
            sku.setId(id);
            List<DbResSkuRepo> skuRepos = tRepo.getDbResSkuReposBySku(sku);
            if ( skuRepos != null && skuRepos.size()>0 ) {
                return id;
            }
        }
        return 0;
    }
}
