package com.pccw.backend.ctrl;


import java.util.*;

import com.pccw.backend.bean.*;
import com.pccw.backend.repository.BaseRepository;
import com.pccw.backend.util.Convertor;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import lombok.extern.slf4j.Slf4j;


/**
 * BaseCtrl
 * BaseCtrl includs CURD
 */

@Slf4j
public class BaseCtrl<T>{

    public <G extends BaseSearchBean> JsonResult search(BaseRepository repo, G b) {
        try {
            log.info(b.toString());
            Specification<T> spec = Convertor.<T>convertSpecification(b);

            List<T> res =repo.findAll(spec,PageRequest.of(b.getPageIndex(),b.getPageSize())).getContent();

            return JsonResult.success(res);
        } catch (Exception e) {
            log.info(e.getMessage());
            return JsonResult.fail(e);
        }
    }

    public JsonResult delete(BaseRepository repo, BaseDeleteBean ids){
        try {
            repo.deleteByIdIn(ids.getIds());
            
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }


    public JsonResult create(BaseRepository repo, Class<T> cls,BaseBean b) {
        try {
            long t = new Date().getTime();
            b.setCreateAt(t);
            b.setUpdateAt(t);
            b.setActive("Y");
            saveAndFlush(repo, cls, b);       
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    public JsonResult edit(BaseRepository repo, Class<T> cls,BaseBean b){
        try {
            b.setUpdateAt(new Date().getTime());
            b.setActive("Y");
            saveAndFlush(repo, cls, b);
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }
    private void saveAndFlush(BaseRepository repo, Class<T> cls, BaseBean b) throws Exception{
        try {         
            T entity = cls.newInstance();
            BeanUtils.copyProperties(b, entity);
            log.info(entity.toString());
            repo.<T>saveAndFlush(entity);
        } catch (Exception e) {
            throw e;
        }
    }
}