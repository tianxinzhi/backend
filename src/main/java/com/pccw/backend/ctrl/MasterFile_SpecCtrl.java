package com.pccw.backend.ctrl;


import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.masterfile_spec.CreateBean;
import com.pccw.backend.bean.masterfile_spec.EditBean;
import com.pccw.backend.bean.masterfile_spec.SearchBean;
import com.pccw.backend.entity.DbResSpec;
import com.pccw.backend.repository.ResSpecRepository;
import com.pccw.backend.util.Convertor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.*;

/**
 * MF_RepoCtrl
 */

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/masterfile_spec")
@Api(value="MasterFile_SpecCtrl",tags={"masterfile_spec"})
public class MasterFile_SpecCtrl extends BaseCtrl<DbResSpec> {

    @Autowired
    ResSpecRepository repo;

    @ApiOperation(value="查询spec",tags={"masterfile_spec"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/search")
    public JsonResult search(@RequestBody SearchBean b) {

        try {
            Specification specification = Convertor.convertSpecification(b);
            List<DbResSpec> res =repo.findAll(specification, PageRequest.of(b.getPageIndex(),b.getPageSize())).getContent();
            ArrayList<com.pccw.backend.bean.masterfile_spec.SearchBean> dbResSpec = new ArrayList<>();
            if(res != null && res.size() > 0){
                for (DbResSpec spec:res){
                    SearchBean searchBean = new SearchBean();
                    BeanUtils.copyProperties(spec, searchBean);
                    if(spec.getResSpecAttrList() != null){
                        searchBean.setAttrData(specSearch(spec.getId()).getData());
                    }
                    dbResSpec.add(searchBean);
                }
            }
            return JsonResult.success(dbResSpec);
        } catch (Exception e) {
            log.info(e.getMessage());
            return JsonResult.fail(e);
        }

    }

    @ApiOperation(value="删除spec",tags={"masterfile_spec"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids){
        return this.delete(repo,ids);
    }

    @ApiOperation(value="创建spec",tags={"masterfile_spec"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/create")
    public JsonResult create(@RequestBody CreateBean b){
       try{
           long t = new Date().getTime();
           b.setUpdateAt(t);
           b.setCreateAt(t);
           for(int i=0;i<b.getResSpecAttrList().size();i++) {
               b.getResSpecAttrList().get(i).setUpdateAt(t);
               b.getResSpecAttrList().get(i).setCreateAt(t);
               b.getResSpecAttrList().get(i).setActive("Y");
           }
           return this.create(repo, DbResSpec.class, b);
        } catch (Exception e) {
            log.info(e.getMessage());
            return JsonResult.fail(e);
       }

    }


    @ApiOperation(value="查询spec",tags={"masterfile_spec"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/edit")
    public JsonResult edit(@RequestBody EditBean b){
        try{
            log.info(b.toString());
            long t = new Date().getTime();
            Optional<DbResSpec> optional = repo.findById(b.getId());
            DbResSpec dbResSpec = optional.get();
            b.setUpdateAt(t);
            b.setCreateAt(dbResSpec.getCreateAt());
            System.out.println(b.getResSpecAttrList());
            for(int i=0;i<b.getResSpecAttrList().size();i++) {
                for(int j=0;j<dbResSpec.getResSpecAttrList().size();j++) {
                    if (dbResSpec.getResSpecAttrList().get(j).getId()==b.getResSpecAttrList().get(i).getId()) {
                        b.getResSpecAttrList().get(i).setUpdateAt(t);
                        b.getResSpecAttrList().get(i).setCreateAt(dbResSpec.getResSpecAttrList().get(j).getCreateAt());
                        b.getResSpecAttrList().get(i).setActive("Y");
                    }else if(b.getResSpecAttrList().get(i).getId()==null){
                        b.getResSpecAttrList().get(i).setUpdateAt(t);
                        b.getResSpecAttrList().get(i).setCreateAt(t);
                        b.getResSpecAttrList().get(i).setActive("Y");
                    }
                }
            }
            return this.edit(repo, DbResSpec.class, b);
            } catch (Exception e) {
                log.info(e.getMessage());
                return JsonResult.fail(e);
            }


    }


    @ApiOperation(value="查询spec",tags={"masterfile_spec"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/editById")
    public JsonResult editById(@RequestBody SearchBean b){
        log.info(b.toString());
        try {
            return JsonResult.success(repo.findById(1L));
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    public JsonResult specSearch(@RequestBody long id) {
        try {
            List<Map> list = new ArrayList<>();
            List<Map> attrList= repo.attrSearch(id);
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