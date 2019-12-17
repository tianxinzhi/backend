package com.pccw.backend.ctrl;


import java.util.*;

import com.pccw.backend.bean.*;
import com.pccw.backend.cusinterface.ICheck;
import com.pccw.backend.entity.Base;
import com.pccw.backend.entity.DbResAttrAttrValue;
import com.pccw.backend.entity.DbResAttrValue;
import com.pccw.backend.exception.BaseException;
import com.pccw.backend.repository.BaseRepository;
import com.pccw.backend.repository.ResAccountRepository;
import com.pccw.backend.repository.ResAttrAttrValueRepository;
import com.pccw.backend.util.Convertor;

import com.pccw.backend.util.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import lombok.extern.slf4j.Slf4j;


/**
 * BaseCtrl
 * BaseCtrl includs CURD
 */

@Slf4j
public class BaseCtrl<T>{

    @Autowired
    Session<Map> session;

    @Autowired
    ResAccountRepository accountRepository;

    public <G extends BaseSearchBean> JsonResult search(BaseRepository repo, G b) {
        try {
            log.info(b.toString());
            Specification<T> spec = Convertor.<T>convertSpecification(b);
            List<T> res =repo.findAll(spec,PageRequest.of(b.getPageIndex(),b.getPageSize())).getContent();
            for (T re : res) {
                Base base = (Base)re;
                base.setCreateAccountName(getAccountName(base.getCreateBy()));
                base.setUpdateAccountName(getAccountName(base.getUpdateBy()));
            }
            return JsonResult.success(res);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return JsonResult.fail(e);
        }
    }

    public JsonResult delete(BaseRepository repo, BaseDeleteBean ids){
        try {
            repo.deleteByIdIn(ids.getIds());
            
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
    }


    public JsonResult create(BaseRepository repo, Class<T> cls,BaseBean b) {
        try {
            long t = new Date().getTime();
            b.setCreateAt(t);
            b.setUpdateAt(t);
            b.setActive("Y");
            b.setCreateBy(getAccount());
            b.setUpdateBy(getAccount());
            saveAndFlush(repo, cls, b);       
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
    }

    public JsonResult edit(BaseRepository repo, Class<T> cls,BaseBean b){
        try {
            b.setUpdateAt(new Date().getTime());
            b.setActive("Y");
            b.setUpdateBy(getAccount());
            saveAndFlush(repo, cls, b);
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
    }
    private void saveAndFlush(BaseRepository repo, Class<T> cls, BaseBean b) throws Exception {
        try {         
            T entity = cls.newInstance();
            BeanUtils.copyProperties(b, entity);
            log.info(entity.toString());
            repo.<T>saveAndFlush(entity);
        } catch (Exception e) {
            throw e;
        }
    }

    public JsonResult disable(BaseRepository repo, BaseDeleteBean ids,Class<?> cl,BaseRepository... checks) {
        try {

            ICheck check = (ICheck) cl.newInstance();
            long id = check.checkCanDisable(ids,checks);
            if(id>0){
                return JsonResult.fail(BaseException.getDataUsedException(id));
            }
            for (Long priKey : ids.getIds()) {
                Base base = (Base)repo.findById(priKey).get();
                base.setActive("N");
                base.setUpdateAt(System.currentTimeMillis());
                base.setUpdateBy(getAccount());
                repo.saveAndFlush(base);
            }
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
    }

    long getAccount() {
        Map user = session.getUser();
        return Long.parseLong(user.get("account").toString());
    }

    String getAccountName(long account) {
        return account==0?"System":accountRepository.findDbResAccountById(account).getAccountName();
    }

}
