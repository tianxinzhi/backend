package com.pccw.backend.ctrl;


import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.stock_out.CreateBean;
import com.pccw.backend.entity.DbResLogMgt;
import com.pccw.backend.repository.ResLogMgtRepository;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/stock_out")
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
public class Stock_OutCtrl  extends BaseCtrl<DbResLogMgt> {

    @Autowired
    private ResLogMgtRepository repo;

    @ApiOperation(value="创建stock_out",tags={"stock_out"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/create")
    public JsonResult create(@RequestBody CreateBean b){
        return this.create(repo, DbResLogMgt.class, b);
    }


   /* @ApiOperation(value="查询spec",tags={"masterfile_spec"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/search")
    public JsonResult search(@RequestBody SearchBean b) {
        log.info(b.toString());
        return this.search(repo,  b);
    }

    @ApiOperation(value="查询spec",tags={"masterfile_spec"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/edit")
    public JsonResult edit(@RequestBody EditBean b){
        log.info(b.toString());
        return this.edit(repo, DbResLogMgt.class, b);
    }*/

}