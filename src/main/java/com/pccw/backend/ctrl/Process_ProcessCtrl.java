package com.pccw.backend.ctrl;

import com.pccw.backend.bean.process_process.SearchBean;
import com.pccw.backend.util.Convertor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/process")
public class Process_ProcessCtrl extends BaseCtrl{

    @RequestMapping(method = RequestMethod.POST,path = "/search")
    public void search(@RequestBody SearchBean bean){
        Date[] dateRange = bean.getDate();

        dateRange[0] = Convertor.beginOfDay(dateRange[0]);
        dateRange[1] = Convertor.endOfDay(dateRange[1]);

        bean.setDate(dateRange);
        log.info(bean.toString());
    }


}
