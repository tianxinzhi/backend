package com.pccw.backend.ctrl;

import com.pccw.backend.bean.GeneralBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.repository.BaseRepository;
import com.pccw.backend.util.Convertor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Slf4j
public class GeneralCtrl {
    /**
     * 将默认查询结果集按照指定bean格式,并装入JsonResult返回
     * @param repo repository对象
     * @param bean 指定的bean对象
     * @param <E>
     * @return
     */
    public <E> JsonResult JsonResultHandle(BaseRepository repo, GeneralBean bean){
        try {
            List<GeneralBean> res = getDefualtSearchBeans(repo, bean);
            return JsonResult.success(res);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    /**
     * 将默认查询结果集按照指定bean格式,并装入JsonResult，并在末尾插入一个bean
     * @param repo repository对象
     * @param bean 插入的bean对象
     * @param <E>
     * @return
     */
    public <E> JsonResult addRowJsonResultHandle(BaseRepository repo, GeneralBean bean){
        try {
            List<GeneralBean> res = getDefualtSearchBeans(repo, bean);
            res.add(bean);
            return JsonResult.success(res);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    /**
     * 将自定义查询的结果集按照指定bean格式,并装入JsonResult
     * @param bean
     * @param list
     * @param <E>
     * @return
     */
    public <E> JsonResult customSearchJsonResultHandle(GeneralBean bean, List<E> list){
        try {
            List<GeneralBean> res = Convertor.getCollect(bean, list);
            return JsonResult.success(res);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    /**
     * 默认查询全部的数据结果集
     * @param repo repository对象
     * @param bean 指定的bean对象
     * @param <E>
     * @return
     */
    private <E> List<GeneralBean> getDefualtSearchBeans(BaseRepository repo, GeneralBean bean) throws IllegalAccessException {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Specification<E> spec = new Specification<E>() {
            @Override
            public Predicate toPredicate(Root<E> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("active").as(String.class), "Y");
                return predicate;
            }
        };
        List<E> list = repo.findAll(spec,sort);
//        List<E> list = repo.findAll();
        return Convertor.getCollect(bean, list);
    }

//    /**
//     * 将集合转换成指定bean格式
//     * @param bean
//     * @param list
//     * @param <E>
//     * @return
//     */
//    private <E> List<GeneralBean> getCollect(GeneralBean bean, List<E> list) {
//        return list.stream().map(item->{
//            JsonResultParamHandle annotation = item.getClass().getAnnotation(JsonResultParamHandle.class);
//            GeneralBean generalBean = bean;
//            if (!Objects.isNull(annotation)) {
//                generalBean = setGeneralBean(item, annotation,bean);
//                // log.info(generalBean.toString());
//            }
//            return generalBean;
//        }).collect(Collectors.toList());
//    }
//
//    /**
//     * 装配bean
//     * @param item
//     * @param annotation
//     * @param generalBean
//     * @param <E>
//     * @return
//     */
//    private <E> GeneralBean setGeneralBean(E item, JsonResultParamHandle annotation, GeneralBean generalBean) {
//        GeneralBean bean = generalBean;
//        try {
//            //将bean的属性类型存入数组
//            Field[] generalBeanFields = generalBean.getClass().getDeclaredFields();
//            Class[] classes = new Class[3];
//            for (int i = 0; i <generalBeanFields.length ; i++) {
//                classes[i] = generalBeanFields[i].getType();
//            }
//            //初始化装配到bean的参数
//            Object param1 = null;
//            Object param2 = null;
//            Object param3 = null;
//            Method[] methods = annotation.getClass().getDeclaredMethods();
//            for (int i = 4; i <methods.length; i++) {
//                //得到注解中传入的值（注解中存放的是entity的某个属性）
//                String annotationMethodValue = (String) methods[i].invoke(annotation);
//                //初始化entity属性的值
//                Object entityFieldValue = null;
//                if (!annotationMethodValue.equals("")) {
//                    //通过注解的值得到这个entity的属性
//                    Field entityDeclaredField = item.getClass().getDeclaredField(annotationMethodValue);
//                    entityDeclaredField.setAccessible(true);
//                    //判断该字段是否为entity的外键关联字段
//                    JoinColumn JoinColumnAnnotation = entityDeclaredField.getAnnotation(JoinColumn.class);
//                    if (Objects.nonNull(JoinColumnAnnotation)){
//                        //通过这个属性的对象得到其类，并获取其id属性和id属性的值
//                        Class<?> fieldClass = entityDeclaredField.get(item).getClass();
//                        Field idField = fieldClass.getDeclaredField("id");
//                        idField.setAccessible(true);
//                        entityFieldValue = idField.get(entityDeclaredField.get(item));
//                    }else {
//                        entityFieldValue = entityDeclaredField.get(item);
//                    }
//                    //判断注解的方法名，将属性值赋给对应bean的参数
//                    switch (methods[i].getName()){
//                        case "param1" :
//                            param1 = entityFieldValue;
//                            break;
//                        case "param2" :
//                            param2 = entityFieldValue;
//                            break;
//                        case "param3" :
//                            param3 = entityFieldValue;
//                            break;
//                    }
//
//                }
//
//            }
//            //初始化bean的有参构造器
//            Constructor<? extends GeneralBean> constructor = generalBean.getClass().getConstructor(classes);
//            constructor.setAccessible(true);
//            //装配参数
//            bean = constructor.newInstance(new Object[]{param1,param2,param3});
//
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        return bean;
//    }
}
