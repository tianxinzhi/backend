package com.pccw.backend.ctrl;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.pccw.backend.bean.*;
import com.pccw.backend.bean.masterfile_attr_value.SearchBean;
import com.pccw.backend.entity.*;
import com.pccw.backend.repository.*;

import com.pccw.backend.util.Convertor;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
    ResFlowRepository  flowRepository;
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
            Sort sort = new Sort(Sort.Direction.DESC, "id");
            Specification<DbResAttrValue> spec = new Specification<DbResAttrValue>() {
                @Override
                public Predicate toPredicate(Root<DbResAttrValue> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Predicate predicate = criteriaBuilder.equal(root.get("active").as(String.class), "Y");
                    return predicate;
                }
            };
            List<DbResAttrValue> list = attr_value_repo.findAll(spec,sort);
            List<LabelAndValue> res = list.stream().map(item -> {
                return  Objects.nonNull(item.getAttrValue()) ? new LabelAndValue(item.getId(), item.getAttrValue(), null) : new LabelAndValue(item.getId(), item.getValueFrom()+"~"+item.getValueTo(),null );
            }).collect(Collectors.toList());

            return JsonResult.success(res);
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
            Sort sort = new Sort(Sort.Direction.DESC, "id");
            Specification<DbResAdjustReason> spec = new Specification<DbResAdjustReason>() {
             @Override
             public Predicate toPredicate(Root<DbResAdjustReason> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                   Predicate predicate = criteriaBuilder.equal(root.get("active").as(String.class), "Y");
                  return predicate;
                }
            };
            List<DbResAdjustReason> list =  adjustReasonRepository.findAll(spec,sort).stream().filter
                    (reason -> !reason.getAdjustReasonName().equals("Other Reason")).collect(Collectors.toList());
            return this.customSearchJsonResultHandle(new LabelAndValue(),list);
    }

    @ApiOperation(value="获取res_role表的roleName和id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/roleModule")
    public JsonResult<LabelAndValue> searchRole(){
        return this.JsonResultHandle(roleRepository,new LabelAndValue());
    }

    @ApiOperation(value="获取res_flow表的Nature和id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/flowModule")
    public JsonResult searchFlowNature(){
        List<String> natureList = Stream.of("ASG","STHR","RET","EXC","ARS","CARS","APU","RREQ","SOTS","SIFS","SOTW","SIFW","SIWPO","ST","STA","SCC").collect(Collectors.toList());
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Specification<DbResFlow> spec = new Specification<DbResFlow>() {
            @Override
            public Predicate toPredicate(Root<DbResFlow> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("active").as(String.class), "Y");
                return predicate;
            }
        };
        List<String> flowNatureList =  flowRepository.findAll(spec,sort).stream().map(flow -> {
            return flow.getFlowNature();
        }).collect(Collectors.toList());
        List<LabelAndValue> resultlist = natureList.stream().filter(item -> !flowNatureList.contains(item)).map(r -> {
            return new LabelAndValue(r, r, null);
        }).collect(Collectors.toList());

        return JsonResult.success(resultlist);
    }

    @ApiOperation(value="获取res_stock_type表的stockTypeName和id信息",tags={"common"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.GET,path="/stockTypeModule")
    public JsonResult<LabelAndValue> searchStockType(){
        return this.JsonResultHandle(stockTypeRepository,new LabelAndValue());
    }

}
