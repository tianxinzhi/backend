package com.pccw.backend.ctrl;

import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.masterfile_sku.CreateBean;
import com.pccw.backend.bean.masterfile_sku.EditBean;
import com.pccw.backend.bean.masterfile_sku.SearchBean;
import com.pccw.backend.bean.masterfile_sku.ResultBean;
import com.pccw.backend.entity.DbResSku;
import com.pccw.backend.entity.DbResSkuAttrValue;
import com.pccw.backend.entity.DbResSkuType;
import com.pccw.backend.repository.ResSkuRepository;
import com.pccw.backend.repository.ResSkuTypeRepository;
import com.pccw.backend.util.Convertor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("masterfile_sku")
@Api(value="MasterFile_SkuCtrl",tags={"masterfile_sku"})
public class MasterFile_SkuCtrl extends BaseCtrl<DbResSku> {

    @Autowired
    ResSkuRepository skuRepo;

    @Autowired
    ResSkuTypeRepository skuTypeRepo;

    @ApiOperation(value="创建sku",tags={"masterfile_sku"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/create")
    public JsonResult create(@RequestBody CreateBean bean) {

        //保存：将attrValue对应信息存入res_sku_attr_Value，res_sku_type
        //{"skuCode":"cd10201","skuDesc":"desc201","type":14,"attrs":[6,7],"attrValueList":[[172,173],[172,174]]}
        System.out.println("bean:" + bean);
        DbResSku sku = new DbResSku();
        sku.setSkuCode(bean.getSkuCode());
        sku.setSkuDesc(bean.getSkuDesc());
        sku.setActive("Y");
        sku.setCreateAt(System.currentTimeMillis());
        sku.setUpdateAt(System.currentTimeMillis());

        List<DbResSkuType> skuTypeList = new LinkedList<>();
        DbResSkuType skuType = new DbResSkuType();
        skuType.setSku(sku);
        skuType.setTypeId(bean.getType());
        skuType.setActive("Y");
        skuType.setCreateAt(System.currentTimeMillis());
        skuType.setUpdateAt(System.currentTimeMillis());
        skuTypeList.add(skuType);

        //DbResTypeSkuSpec typeSkuSpec = typeSkuSpecRepository.findById((int)bean.getType()).get();
        List<DbResSkuAttrValue> skuAttrValueList = new LinkedList<>();

        for(int i=0;i<bean.getAttrs().length;i++){
            for(int value : bean.getAttrValueList().get(i)){
                DbResSkuAttrValue skuAttrValue = new DbResSkuAttrValue();
                skuAttrValue.setAttrId(bean.getAttrs()[i]);
                skuAttrValue.setAttrValueId(value);
                //skuAttrValue.setSpecId(bean.getSpec());
                skuAttrValue.setSku(sku);
                skuAttrValue.setActive("Y");
                skuAttrValue.setCreateAt(System.currentTimeMillis());
                skuAttrValue.setUpdateAt(System.currentTimeMillis());
                skuAttrValueList.add(skuAttrValue);
            }
        }

        sku.setSkuAttrValueList(skuAttrValueList);
        sku.setSkuTypeList(skuTypeList);
        skuRepo.saveAndFlush(sku);

        return JsonResult.success(Arrays.asList());
    }

    @ApiOperation(value="删除sku",tags={"masterfile_sku"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids) {
        return this.delete(skuRepo,ids);
    }

    @ApiOperation(value="修改sku",tags={"masterfile_sku"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/edit")
    public JsonResult edit(@RequestBody EditBean bean) {
        System.out.println("bean:" + bean);
        DbResSku sku = skuRepo.findById(bean.getId()).get();
        sku.setSkuCode(bean.getSkuCode());
        sku.setSkuDesc(bean.getSkuDesc());
        sku.setUpdateAt(System.currentTimeMillis());

        List<DbResSkuAttrValue> skuAttrValueList = sku.getSkuAttrValueList();
        List<DbResSkuType> skuTypeList = sku.getSkuTypeList();

        skuAttrValueList.clear();
        skuTypeList.clear();

        DbResSkuType skuType = new DbResSkuType();
        skuType.setSku(sku);
        skuType.setTypeId(bean.getType());
        System.out.println("sku:" + sku.getId() + "type:" + bean.getType());
        skuType.setActive("Y");
        skuType.setCreateAt(System.currentTimeMillis());
        skuType.setUpdateAt(System.currentTimeMillis());
        skuTypeList.add(skuType);

        for(int i=0;i<bean.getAttrs().length;i++){
            for(int value : bean.getAttrValueList().get(i)){
                DbResSkuAttrValue skuAttrValue = new DbResSkuAttrValue();

                System.out.println("attr:" + bean.getAttrs()[i] + ",attrValue:" + value);
                skuAttrValue.setAttrId(bean.getAttrs()[i]);
                skuAttrValue.setAttrValueId(value);
                //skuAttrValue.setSpecId(bean.getSpec());
                skuAttrValue.setSku(sku);
                skuAttrValue.setActive("Y");
                skuAttrValue.setCreateAt(System.currentTimeMillis());
                skuAttrValue.setUpdateAt(System.currentTimeMillis());
                skuAttrValueList.add(skuAttrValue);
            }
        }

        skuRepo.saveAndFlush(sku);

        return JsonResult.success(Arrays.asList());
    }

    @ApiOperation(value="搜索sku",tags={"masterfile_sku"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/search")
    public JsonResult search(@RequestBody SearchBean bean) throws Exception{
        //System.out.println("skuSearch:"+bean);
        Specification<DbResSku> spec = Convertor.<DbResSku>convertSpecification(bean);
        List<DbResSku> skuList = skuRepo.findAll(spec, PageRequest.of(bean.getPageIndex(),bean.getPageSize())).getContent();
        List<ResultBean> skuResultBeans = new LinkedList<>();
        for (DbResSku sku : skuList) {
            EditBean ebean = new EditBean();
            ebean.setId(sku.getId());
            JsonResult typeResult =  skuSearch(ebean);

            ResultBean resultBean = (ResultBean) typeResult.getData().get(0);
            BeanUtils.copyProperties(sku,resultBean);

            List<Map> attrDataMap = new LinkedList<>();
            for (int i=0;i<resultBean.getAttrNames().length;i++) {
                Map<String,Object> map = new HashMap<>();
                map.put("attrName",resultBean.getAttrNames()[i]);
                map.put("attrValue",resultBean.getAttrValueNames().get(i));

                attrDataMap.add(map);
            }
            resultBean.setAttrData(attrDataMap);
            skuResultBeans.add(resultBean);
        }
        return  JsonResult.success(skuResultBeans);
        //return this.search(skuRepo,bean);
    }

    @ApiOperation(value="type下拉框",tags={"masterfile_sku"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/typeSearch")
    public JsonResult typeSearch(@RequestBody EditBean bean) {
        //初始加载：下拉选type，从restypeSkuSpec找到对应唯一spec，拿spec从resspecAttr中找到对应attrId和attrvalue
        // ,拿attrId和attrValueId去拿到对应的name
        //System.out.println("type:"+type.getId());
        List<Map> specs = skuRepo.getAllSpecsByType(bean.getId());
        List<ResultBean> skuResultBeans = new LinkedList<>();

        long[] attrs = new long[specs.size()];
        String[] attrNames = new String[specs.size()];
        List<String[]> attrValues = new LinkedList<>();
        List<String[]> attrValueNames = new LinkedList<>();
        for (int i = 0; i < specs.size(); i++) {
            attrs[i] = Long.valueOf(specs.get(i).get("attr").toString());
            attrNames[i] = specs.get(i).get("attrName").toString();
            String[] attrValueName = StringUtils.commaDelimitedListToStringArray(specs.get(i).get("attrValueName").toString());
            String[] attrValue = StringUtils.commaDelimitedListToStringArray(specs.get(i).get("attrValue").toString());
            attrValues.add(attrValue);
            attrValueNames.add(attrValueName);
        }
        ResultBean skuResult = new ResultBean();
        skuResult.setSpec(Long.parseLong(specs.get(0).get("spec").toString()));
        skuResult.setSpecName(specs.get(0).get("specName").toString());
        skuResult.setAttrs(attrs);
        skuResult.setAttrNames(attrNames);
        skuResult.setAttrValues(attrValues);
        skuResult.setAttrValueNames(attrValueNames);
        skuResultBeans.add(skuResult);
        return JsonResult.success(skuResultBeans);
    }

    @ApiOperation(value="sku弹出框",tags={"masterfile_sku"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/skuSearch")
    public JsonResult skuSearch(@RequestBody EditBean bean) {
        System.out.println("bean:" + bean);
        List<Map> types = skuRepo.getTypeDtlsBySku(bean.getId());
        List<ResultBean> skuResultBeans = new LinkedList<>();

        long[] attrs = new long[types.size()];
        String[] attrNames = new String[types.size()];
        List<String[]> attrValueNames = new LinkedList<>();
        List<String[]> attrValues = new LinkedList<>();
        for (int i = 0; i < types.size(); i++) {
            attrs[i] = Long.valueOf(types.get(i).get("attr").toString());
            attrNames[i] = types.get(i).get("attrName").toString();
            String[] attrValueName = StringUtils.commaDelimitedListToStringArray(types.get(i).get("attrValueName").toString());
            String[] attrValue = StringUtils.commaDelimitedListToStringArray(types.get(i).get("attrValue").toString());
            attrValues.add(attrValue);
            attrValueNames.add(attrValueName);
        }
        ResultBean skuResult = new ResultBean();
        skuResult.setType(Long.parseLong(types.get(0).get("type").toString()));
        skuResult.setTypeName(types.get(0).get("typeName").toString());
        skuResult.setSpecName(types.get(0).get("specName").toString());
        skuResult.setSpec(Long.parseLong(types.get(0).get("spec").toString()));
        skuResult.setAttrs(attrs);
        skuResult.setAttrNames(attrNames);
        skuResult.setAttrValues(attrValues);
        skuResult.setAttrValueNames(attrValueNames);
        skuResultBeans.add(skuResult);
        return JsonResult.success(skuResultBeans);
    }

    @ApiOperation(value="eidt 搜索",tags={"masterfile_sku"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/editSearch")
    public JsonResult editSearch(@RequestBody SearchBean bean) {

        DbResSku sku = new DbResSku();
        sku.setId(bean.getSku());
        List<DbResSkuType> skuList = new LinkedList<>();
        DbResSkuType skuType = skuTypeRepo.findBySku(sku);
        skuList.add(skuType);

        return JsonResult.success(skuList);
    }
}
