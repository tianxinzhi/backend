package com.pccw.backend.ctrl;

import java.util.*;
import java.util.stream.Collectors;

import com.pccw.backend.bean.*;
import com.pccw.backend.entity.*;
import com.pccw.backend.repository.*;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

/**
 * AuthRightCtrl
 */

@Slf4j
@RestController
@RequestMapping("/common")
@CrossOrigin(origins = "*", allowCredentials = "false")
public class CommonCtrl  extends GeneralCtrl{

    @Autowired
    ResRightRepository right_repo;
    @Autowired
    ResSkuRepository sku_repo;
    @Autowired
    ResSpecRepository spec_repo;
    @Autowired
    ResTypeRepository type_repo;
    @Autowired
    ResAttrRepository attr_repo;
    @Autowired
    ResAttrValueRepository attr_value_repo;
    @Autowired
    ResClassRepository class_repo;
    @Autowired
    ResRepoRepository repo_repo;
    @Autowired
    ResAttrAttrValueRepository attr_attr_value_repo;
    @Autowired
    ResAdjustReasonRepository adjustReasonRepository;
    @Autowired
    ResRoleRepository roleRepository;
    @Autowired
    ResStockTypeRepository stockTypeRepository;
    @Autowired
    ResAccountRepository accountRepository;

    @ApiOperation(value="获取res_right表的信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/rightModule")
    public JsonResult<TreeNode> search() {
         return this.addRowJsonResultHandle(right_repo,new TreeNode(0L,-1L,"SMP"));
    }

    @ApiOperation(value="获取res_sku表的skuCode和id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/skuModule")
    public JsonResult<LabelAndValue> searchSku() {
       return this.JsonResultHandle(sku_repo,new LabelAndValue());
    }

    @ApiOperation(value="获取res_spec表的SpecName和id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/specModule")
    public JsonResult<LabelAndValue> searchSpec() {
        return this.JsonResultHandle(spec_repo,new LabelAndValue());
    }

    @ApiOperation(value="获取res_type表的TypeName和id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/typeModule")
    public JsonResult<LabelAndValue> searchType() {
        return this.JsonResultHandle(type_repo,new LabelAndValue());
    }

    @ApiOperation(value="获取res_attr表的AttrName和id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/attrModule")
    public JsonResult<LabelAndValue> searchAttr() {
        return this.JsonResultHandle(attr_repo,new LabelAndValue());
    }

    @ApiOperation(value="获取res_attr_value表的AttrValue和id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/attrValueModule")
    public JsonResult<LabelAndValue> searchAttrValue() {
        return this.JsonResultHandle(attr_value_repo,new LabelAndValue());
    }

    @ApiOperation(value="获取res_class表的ClassName和id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/classValueModule")
    public JsonResult<TreeNode> searchClass() {
        return this.JsonResultHandle(class_repo,new TreeNode());
    }

    @ApiOperation(value="获取res_repo表的RepoCode，RepoType，id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/repoModule")
    public JsonResult<LabelAndValue> searchRepo(){
        return this.JsonResultHandle(repo_repo,new LabelAndValue());
    }

    @ApiOperation(value="获取res_attr_attr_value表的AttrId和AttrValueId信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/attratrrvalueModule")
    public JsonResult<LabelAndValue> searchAttrAttrValue(){
        return this.JsonResultHandle(attr_attr_value_repo,new LabelAndValue());
    }

    @ApiOperation(value="获取res_adjust_reason表的adjustReasonName和id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/adjustReasonModule")
    public JsonResult<LabelAndValue> searchAdjustReason(){
        List<DbResAdjustReason> list =  adjustReasonRepository.findAll().stream().filter
                (reason -> !reason.getAdjustReasonName().equals("Other Reason")).collect(Collectors.toList());
        return this.customSearchJsonResultHandle(new LabelAndValue(),list);
    }

    @ApiOperation(value="获取res_role表的roleName和id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/roleModule")
    public JsonResult<LabelAndValue> searchRole(){
        return this.JsonResultHandle(roleRepository,new LabelAndValue());
    }

    @ApiOperation(value="获取res_stock_type表的stockTypeName和id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/stockTypeModule")
    public JsonResult<LabelAndValue> searchStockType(){
        return this.JsonResultHandle(stockTypeRepository,new LabelAndValue());
    }

    /**
     * 根据id获取AccountName
     * @param id
     * @return
     */
    public JsonResult searchAccountById(long id){
        List<Object> objects = new ArrayList<>();
        String accountName = "";
        try {
            if(Objects.nonNull(id)){
                if(id == 0){
                    accountName = "system";
                }else{
                    accountName = accountRepository.findById(id).get().getAccountName();
                }
            }
            objects.add(accountName);
            return JsonResult.success(objects);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
    }

    /**
     * 根据specId查询attr&attrValue
     * 返回指定格式给前端详情展示
     * @param id
     * @return
     */
    @ApiOperation(value="搜索spec_attr&attrValue",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST, path = "/specSearch")
    public JsonResult specSearch(@RequestBody Long id) {
        try {
            List<Map> list = new ArrayList<>();
            List<Map> attrList= type_repo.specSearch(id);
            attrList.stream()
                    .collect(Collectors.groupingBy(s -> s.get("attrName")))
                    .forEach((k,v)->{
                                HashMap<Object, Object> hm = new HashMap<>();
                                List<String> attrValueList = new ArrayList<>();
                                v.forEach((a)->{ attrValueList.add(a.get("attrValue").toString());});
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
