package com.pccw.backend.ctrl;

import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.masterfile_item.*;
import com.pccw.backend.entity.DbResItem;
import com.pccw.backend.repository.ResItemRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/masterfile_item")
@Api(value = "MasterFile_ItemCtrl",tags = "masterfile_item")
public class MasterFile_ItemCtrl extends BaseCtrl<DbResItem> {
    @Autowired
    ResItemRepository resItemRepository;
  @ApiOperation(value = "搜索item",tags = "masterfile_item",notes = "注意问题点")
  @RequestMapping(method = RequestMethod.POST,path = "/search")
  public JsonResult search(@RequestBody SearchBean b){
      log.info(b.toString());
    return this.search(resItemRepository,b);
  }

    @ApiOperation(value = "创建item",tags = "masterfile_item",notes = "注意问题点")
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    public JsonResult creat(@RequestBody CreateBean b){
      return this.create(resItemRepository, DbResItem.class,b);
    }

  @ApiOperation(value = "修改item",tags = "masterfile_item",notes = "注意问题点")
    @RequestMapping(method = RequestMethod.POST,path = "/edit")
    public JsonResult edit(@RequestBody EditBean b){
      return this.edit(resItemRepository, DbResItem.class,b);
    }

  @ApiOperation(value = "删除item",tags = "masterfile_item",notes = "注意问题点")
    @RequestMapping(method = RequestMethod.POST,path = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids){
      return this.delete(resItemRepository,ids);
    }

}
