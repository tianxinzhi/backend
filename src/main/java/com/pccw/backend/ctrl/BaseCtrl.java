package com.pccw.backend.ctrl;


import java.util.Arrays;
import java.util.List;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.authright.CreateBean;
import com.pccw.backend.bean.authright.EditBean;
// import com.pccw.backend.bean.authright.*;
import com.pccw.backend.bean.authright.SearchBean;
import com.pccw.backend.entity.DbResRight;
import com.pccw.backend.repository.BaseRepository;
import com.pccw.backend.repository.ResRightRepository;
import com.pccw.backend.util.Convertor;
import com.pccw.backend.bean.BaseBean;
import com.pccw.backend.bean.BaseSearchBean;
import com.pccw.backend.bean.DeleteBean;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.MethodDelegationBinder.MethodInvoker.Virtual;

/**
 * AuthRightCtrl
 */

@Slf4j
public class BaseCtrl<T>{

    public <G extends BaseSearchBean> JsonResult search(BaseRepository repo, Class<G> cls, G b) {
        try {
            Specification<T> spec = Convertor.<T,G>convertSpecification(cls,b);
            List<T> res =repo.findAll(spec,PageRequest.of(b.getPageIndex(),b.getPageSize())).getContent();
            return JsonResult.success(res);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    public JsonResult delete(BaseRepository repo, DeleteBean ids){
        try {
            repo.deleteByIdIn(ids.getIds());
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }


    public JsonResult create(BaseRepository repo, Class<T> cls,BaseBean b) {
        try {
            T entity = cls.newInstance();
            BeanUtils.copyProperties(b, entity);
            log.info(entity.toString());
            repo.<T>saveAndFlush(entity);
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    public JsonResult edit(BaseRepository repo, Class<T> cls,BaseBean b){
        try {
            T entity = cls.newInstance();
            BeanUtils.copyProperties(b, entity);
            log.info(entity.toString());
            repo.<T>saveAndFlush(entity);
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }
}