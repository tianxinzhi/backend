package com.pccw.backend.ctrl;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.reservation_rule.CreateBean;
import com.pccw.backend.bean.reservation_rule.EditBean;
import com.pccw.backend.bean.reservation_rule.SearchBean;
import com.pccw.backend.entity.DbResReservationRule;
import com.pccw.backend.repository.*;
import com.pccw.backend.util.Convertor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/stock_reservation_rule")
@Api(value="Stock_ReservationRuleCtrl",tags={"stock_reservation_rule"})
public class Stock_ReservationRuleCtrl extends BaseCtrl<DbResReservationRule> {

    @Autowired
    ResReservationRuleRepository reservationRepository;

    @ApiOperation(value="预留sku优先级",tags={"stock_reservation_rule"},notes="查询")
    @RequestMapping("/search")
    public JsonResult search(SearchBean bean) {
        try {
            Specification<DbResReservationRule> spec = Convertor.<DbResReservationRule>convertSpecification(bean);
            List<DbResReservationRule> list = reservationRepository.findAll(spec, PageRequest.of(bean.getPageIndex(),bean.getPageSize())).getContent();
            return JsonResult.success(list);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
    }

    @ApiOperation(value="预留sku优先级",tags={"stock_reservation_rule"},notes="新增")
    @RequestMapping("/create")
    public JsonResult create(@RequestBody CreateBean bean) {
        return create(reservationRepository,DbResReservationRule.class,bean);
    }

    @ApiOperation(value="删除",tags={"stock_reservation_rule"},notes="")
    @RequestMapping(method = RequestMethod.POST,value = "/delete")
    public JsonResult delete(@RequestBody EditBean ids) {
        reservationRepository.deleteById(ids.getId());
        return JsonResult.success(Arrays.asList());
    }

    @ApiOperation(value="修改",tags={"stock_reservation_rule"},notes="")
    @RequestMapping(method = RequestMethod.POST,value = "/edit")
    public JsonResult edit(@RequestBody EditBean b) {
        return this.edit(reservationRepository, DbResReservationRule.class, b);
    }


}
