package com.pccw.backend.ctrl;

import com.pccw.backend.annotation.JsonResultParamHandle;
import com.pccw.backend.bean.GeneralBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.repository.BaseRepository;

import javax.persistence.JoinColumn;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GeneralCtrl {
    /**
     * 将结果集按照指定bean格式输出
     * @param repo repository对象
     * @param bean 指定的bean对象
     * @param <E>
     * @return
     */
    public <E> JsonResult JsonResultHandle(BaseRepository repo, GeneralBean bean){
        try {
            List<E> list = repo.findAll();
            List<GeneralBean> res = list.stream().map(item->{
                JsonResultParamHandle annotation = item.getClass().getAnnotation(JsonResultParamHandle.class);
                GeneralBean generalBean = bean;
                if (!Objects.isNull(annotation)) {
                    generalBean = setGeneralBean(item, annotation,bean);
                }
                return generalBean;
            }).collect(Collectors.toList());
            return JsonResult.success(res);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    /**
     * 装配bean
     * @param item
     * @param annotation
     * @param generalBean
     * @param <E>
     * @return
     */
    public <E> GeneralBean setGeneralBean(E item, JsonResultParamHandle annotation, GeneralBean generalBean) {
        GeneralBean bean = generalBean;
        try {
            //将bean的属性类型存入数组
            Field[] generalBeanFields = generalBean.getClass().getDeclaredFields();
            Class[] classes = new Class[3];
            for (int i = 0; i <generalBeanFields.length ; i++) {
                classes[i] = generalBeanFields[i].getType();
            }
            //初始化装配到bean的参数
            Object param1 = null;
            Object param2 = null;
            Object param3 = null;
            Method[] methods = annotation.getClass().getDeclaredMethods();
            for (int i = 4; i <methods.length; i++) {
                //得到注解中传入的值（注解中存放的是entity的某个属性）
                String annotationMethodValue = (String) methods[i].invoke(annotation);
                //初始化entity属性的值
                Object entityFieldValue = null;
                if (!annotationMethodValue.equals("")) {
                    //通过注解的值得到这个entity的属性
                    Field entityDeclaredField = item.getClass().getDeclaredField(annotationMethodValue);
                    entityDeclaredField.setAccessible(true);
                    //判断该字段是否为entity的外键关联字段
                    JoinColumn JoinColumnAnnotation = entityDeclaredField.getAnnotation(JoinColumn.class);
                    if (Objects.nonNull(JoinColumnAnnotation)){
                        //通过这个属性的对象得到其类，并获取其id属性和id属性的值
                        Class<?> fieldClass = entityDeclaredField.get(item).getClass();
                        Field idField = fieldClass.getDeclaredField("id");
                        idField.setAccessible(true);
                        entityFieldValue = idField.get(entityDeclaredField.get(item));
                    }else {
                        entityFieldValue = entityDeclaredField.get(item);
                    }
                    //判断注解的方法名，将属性值赋给对应bean的参数
                    switch (methods[i].getName()){
                        case "param1" :
                            param1 = entityFieldValue;
                            break;
                        case "param2" :
                            param2 = entityFieldValue;
                            break;
                        case "param3" :
                            param3 = entityFieldValue;
                            break;
                    }

                }

            }
            //初始化bean的有参构造器
            Constructor<? extends GeneralBean> constructor = generalBean.getClass().getConstructor(classes);
            constructor.setAccessible(true);
            //装配参数
            bean = constructor.newInstance(new Object[]{param1,param2,param3});
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return bean;
    }
}
