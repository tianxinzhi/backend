package com.pccw.backend.ctrl;

import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.MasterFile_Item.CreateBean;
import com.pccw.backend.bean.MasterFile_Item.EditBean;
import com.pccw.backend.bean.MasterFile_Item.SearchBean;
import com.pccw.backend.entity.DbResItem;
import com.pccw.backend.repository.ResItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/masterfile_item")
public class MasterFile_ItemCtrl extends BaseCtrl<DbResItem> {
    @Autowired
    ResItemRepository resItemRepository;

  @RequestMapping(method = RequestMethod.POST,path = "/search")
  public JsonResult search(@RequestBody SearchBean b){
      log.info(b.toString());
    return this.search(resItemRepository,b);
  }

    @RequestMapping(method = RequestMethod.POST,path = "/create")
    public JsonResult creat(@RequestBody CreateBean b){
      return this.create(resItemRepository, DbResItem.class,b);
    }

    @RequestMapping(method = RequestMethod.POST,path = "/edit")
    public JsonResult edit(@RequestBody EditBean b){
      return this.edit(resItemRepository, DbResItem.class,b);
    }

    @RequestMapping(method = RequestMethod.POST,path = "/delete")
    public JsonResult delete(@RequestBody BaseDeleteBean ids){
      return this.delete(resItemRepository,ids);
    }

}
