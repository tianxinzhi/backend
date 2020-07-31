package com.pccw.backend.ctrl;

import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.stock_reservation.CreateBean;
import com.pccw.backend.bean.stock_reservation.EditBean;
import com.pccw.backend.bean.stock_reservation.SearchBean;
import com.pccw.backend.entity.*;
import com.pccw.backend.repository.*;
import com.pccw.backend.util.Convertor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/stock_reservation")
@Api(value="Stock_ReservationCtrl",tags={"stock_reservation"})
public class Stock_ReservationCtrl extends BaseCtrl<DbResReservation> {

    @Autowired
    ResLogMgtRepository logMgtRepository;
    @Autowired
    ResSkuRepoRepository skuRepoRepository;
    @Autowired
    ResReservationRepository reservationRepository;
    @Autowired
    ResStockTypeRepository stockTypeRepository;
    @Autowired
    Process_ProcessCtrl processProcessCtrl;

    @ApiOperation(value="预留",tags={"stock_reservation"},notes="查询")
    @RequestMapping("/search")
    public JsonResult search(@RequestBody SearchBean bean) {
        try {
            Specification<DbResReservation> spec = Convertor.<DbResReservation>convertSpecification(bean);
            List<DbResReservation> list = reservationRepository.findAll(spec, PageRequest.of(bean.getPageIndex(),bean.getPageSize())).getContent();
            return JsonResult.success(list);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="预留",tags={"stock_reservation"},notes="新增")
    @RequestMapping("/create")
    public JsonResult create(@RequestBody CreateBean bean) {
        return create(reservationRepository,DbResReservation.class,bean);
    }

    @ApiOperation(value="删除",tags={"stock_reservation"},notes="")
    @RequestMapping(method = RequestMethod.POST,value = "/delete")
    public JsonResult delete(@RequestBody EditBean ids) {
        reservationRepository.deleteById(ids.getId());
        return JsonResult.success(Arrays.asList());
    }

    @ApiOperation(value="修改",tags={"stock_reservation"},notes="")
    @RequestMapping(method = RequestMethod.POST,value = "/edit")
    public JsonResult edit(@RequestBody EditBean b) {
        return this.edit(reservationRepository, DbResReservation.class, b);
    }


}
