package com.pccw.backend.ctrl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.pccw.backend.bean.CommonBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.LabelAndValue;
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
@CrossOrigin(methods = RequestMethod.GET,origins = "*", allowCredentials = "false")
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
    ResSkuRepoRepository skuRepoRepository;

    @ApiOperation(value="获取res_right表的信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/rightModule")
    public JsonResult<CommonBean> search() {
        try {
            List<DbResRight> list =  right_repo.findAll();
            List<CommonBean> res = list.stream().map(r->{
                return new CommonBean(r.getId(),r.getRightPid(),r.getRightName());
            }).collect(Collectors.toList());
            res.add(0, new CommonBean(0L, -1L, "SMP"));
            // res.add(0, new CommonBean(1L, 0L, "Left"));
            // res.add(0, new CommonBean(2L, 0L, "Right"));
            return JsonResult.success(res);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="获取res_sku表的skuCode和id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/skuModule")
    public JsonResult<LabelAndValue> searchSku() {
        try {
            List<DbResSku> list =  sku_repo.findAll();
            List<LabelAndValue> res = list.stream().map(r->{
                return new LabelAndValue(r.getId(),r.getSkuCode(),null);
            }).collect(Collectors.toList());
            return JsonResult.success(res);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="获取res_spec表的SpecName和id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/specModule")
    public JsonResult<LabelAndValue> searchSpec() {
        try {
            List<DbResSpec> list =  spec_repo.findAll();
            List<LabelAndValue> res = list.stream().map(r->{
                return new LabelAndValue(r.getId(),r.getSpecName(),null);
            }).collect(Collectors.toList());
            return JsonResult.success(res);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="获取res_type表的TypeName和id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/typeModule")
    public JsonResult<LabelAndValue> searchType() {
        try {
            List<DbResType> list =  type_repo.findAll();
            List<LabelAndValue> res = list.stream().map(r->{
                return new LabelAndValue(r.getId(),r.getTypeName(),null);
            }).collect(Collectors.toList());
            return JsonResult.success(res);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="获取res_attr表的AttrName和id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/attrModule")
    public JsonResult<LabelAndValue> searchAttr() {
        try {
            List<DbResAttr> list =  attr_repo.findAll();
            List<LabelAndValue> res = list.stream().map(r->{
                return new LabelAndValue(r.getId(),r.getAttrName(),null);
            }).collect(Collectors.toList());
            return JsonResult.success(res);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="获取res_attr_value表的AttrValue和id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/attrValueModule")
    public JsonResult<LabelAndValue> searchAttrValue() {
        try {
            List<DbResAttrValue> list =  attr_value_repo.findAll();
            List<LabelAndValue> res = list.stream().map(r->{
                return new LabelAndValue(r.getId(),r.getAttrValue(),null);
            }).collect(Collectors.toList());
            return JsonResult.success(res);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="获取res_class表的ClassName和id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/classValueModule")
    public JsonResult<CommonBean> searchClass() {
        try {
            List<DbResClass> list =  class_repo.findAll();
            List<CommonBean> res = list.stream().map(r->{
                return new CommonBean(r.getId(),Long.parseLong(r.getParentClassId()),r.getClassName());
            }).collect(Collectors.toList());
//            res.add(0,new CommonBean(0L,-1L,"class"));
            return JsonResult.success(res);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="获取res_repo表的RepoCode，RepoType，id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/repoModule")
    public JsonResult<LabelAndValue> searchRepo(){
        try {
            List<DbResRepo> list =  repo_repo.findAll();
            List<LabelAndValue> res = list.stream().map(r->{
                return new LabelAndValue(r.getId(),r.getRepoCode(),r.getRepoType());
            }).collect(Collectors.toList());
            return JsonResult.success(res);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="获取res_attr_attr_value表的AttrId和AttrValueId信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/attratrrvalueModule")
    public JsonResult<LabelAndValue> searchAttrAttrValue(){
        try {
            List<Map> list =  attr_attr_value_repo.searchAll();
            List<LabelAndValue> res = list.stream().map(r->{
                return new LabelAndValue(r.get("ATTR_ID"),r.get("ATTR_VALUE_ID"),null);
            }).collect(Collectors.toList());
            return JsonResult.success(res);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="获取res_adjust_reason表的adjustReasonName和id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/adjustReasonModule")
    public JsonResult<LabelAndValue> searchAdjustReason(){
        try {
            List<DbResAdjustReason> list =  adjustReasonRepository.findAll();
            List<LabelAndValue> res = list.stream().map(r->{
                return new LabelAndValue(r.getId(),r.getAdjustReasonName(),null);
            }).collect(Collectors.toList());
            return JsonResult.success(res);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="获取res_role表的roleName和id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/roleModule")
    public JsonResult<LabelAndValue> searchRole(){
        try {
            List<DbResRole> list =  roleRepository.findAll();
            List<LabelAndValue> res = list.stream().map(r->{
                return new LabelAndValue(r.getId(),r.getRoleName(),null);
            }).collect(Collectors.toList());
            return JsonResult.success(res);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="获取res_stock_type表的stockTypeName和id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/stockTypeModule")
    public JsonResult<LabelAndValue> searchStockType(){
        try {
            List<DbResStockType> list =  stockTypeRepository.findAll();
            List<LabelAndValue> res = list.stream().map(r->{
                return new LabelAndValue(r.getId(),r.getStockTypeName(),null);
            }).collect(Collectors.toList());
            return JsonResult.success(res);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @RequestMapping(method = RequestMethod.GET,path="/skuModule1")
    public JsonResult<LabelAndValue> searchSku1() {
        return this.JsonResultHandle(sku_repo,new LabelAndValue());
    }

    @RequestMapping(method = RequestMethod.GET,path="/stockTypeModule1")
    public JsonResult<LabelAndValue> searchStockType1(){
        return this.JsonResultHandle(skuRepoRepository,new LabelAndValue());
    }

    @RequestMapping(method = RequestMethod.GET,path="/rightModule1")
    public JsonResult<CommonBean> search1() {
        return this.JsonResultHandle(right_repo,new CommonBean());
    }
}
