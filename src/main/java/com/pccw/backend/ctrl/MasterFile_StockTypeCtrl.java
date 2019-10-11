package com.pccw.backend.ctrl;


import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.masterfile_stocktype.CreateBean;
import com.pccw.backend.bean.masterfile_stocktype.EditBean;
import com.pccw.backend.bean.masterfile_stocktype.SearchBean;
import com.pccw.backend.entity.DbResStockType;
import com.pccw.backend.repository.ResStockTypeRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * MF_RepoCtrl
 */

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/masterfile_stock_type")
@Api(value="MasterFile_StockTypeCtrl",tags={"masterfile_stock_type"})
public class MasterFile_StockTypeCtrl extends BaseCtrl<DbResStockType> {

    @Autowired
    ResStockTypeRepository repo;

    @ApiOperation(value="查询StockType",tags={"masterfile_stock_type"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/search")
    public JsonResult search(@RequestBody SearchBean b) {
        log.info(b.toString());
        return this.search(repo,  b);
    }

    @ApiOperation(value="删除StockType",tags={"masterfile_stock_type"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids){
        return this.delete(repo,ids);
    }

    @ApiOperation(value="创建StockType",tags={"masterfile_stock_type"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/create")
    public JsonResult create(@RequestBody CreateBean b){
        return this.create(repo, DbResStockType.class, b);
    }
    @ApiOperation(value="编辑StockType",tags={"masterfile_stock_type"},notes="说明")
    @RequestMapping(method = RequestMethod.POST,path="/edit")
    public JsonResult edit(@RequestBody EditBean b){
        log.info(b.toString());
        return this.edit(repo, DbResStockType.class, b);
    }

}