package com.pccw.backend.util;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;


import lombok.extern.slf4j.Slf4j;


import java.lang.reflect.Field;
import java.sql.Date;
import java.util.*;

import javax.persistence.criteria.*;

import com.pccw.backend.annotation.PredicateAnnotation;
import com.pccw.backend.annotation.PredicateType;
import com.pccw.backend.bean.BaseSearchBean;



/**
 * Convertor
 */
@Aspect
@Slf4j
@Component
 public class Convertor {

	/**
	 * for multiple condition search
	 */
    public static <T> Specification<T> convertSpecification(Object o)
    // public static <T> Specification<T> convertSpecification(Class<? extends BaseSearchBean> cls,Object o)
			throws IllegalArgumentException, IllegalAccessException {

	    // get all Fields which haved the PredicateAnnotation uysing reflact
		Field[] fieldArr = o.getClass().getDeclaredFields();

		ArrayList<Parm> arr = new ArrayList<Parm>();

		for (Field f : fieldArr) {
			f.setAccessible(true); 
			Object obj = f.get(o);
			if(obj!=null){
				PredicateAnnotation annotation = f.getAnnotation(PredicateAnnotation.class);
				if(annotation!=null){
					Parm parm = new Parm(annotation.type(), f.getName(), obj);
					arr.add(parm);
					log.info(arr.toString());
				}
			}
		}

		// return multiple search condition
        return new Specification<T>() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
			CriteriaBuilder builder) {
				// builder.createQuery()
				List<Predicate> list = new ArrayList<>();

				arr.stream().forEach( a->{
					Parm parm = (Parm)a;

					PredicateType code = PredicateType.getByValue(parm.getPredicateType().getCode());

					switch (code) {
						case EQUEL:
//							list.add(builder.equal(root.get(parm.getName()).as(String.class),parm.getValue()));
							list.add(builder.equal(builder.lower(root.get(parm.getName()).as(String.class)),parm.getValue().toString().toLowerCase()));
						break;
						case LIKE:
//							list.add(builder.like(root.get(parm.getName()).as(String.class),"%"+parm.getValue()+"%"));
							list.add(builder.like(builder.lower(root.get(parm.getName()).as(String.class)),"%"+parm.getValue().toString().toLowerCase()+"%"));
						break;
						case LESS_THAN:
							list.add(builder.lessThan(root.get(parm.getName()).as(String.class),parm.getValue().toString()));
						break;
						case LESSTHAN_OR_EQUEL:
							list.add(builder.lessThanOrEqualTo(root.get(parm.getName()).as(String.class),parm.getValue().toString()));
						break;
						case GREATER_THAN:
						list.add(builder.greaterThan(root.get(parm.getName()).as(String.class),parm.getValue().toString()));
						break;
						case GREATERTHAN_OR_EQUEL:
							list.add(builder.greaterThanOrEqualTo(root.get(parm.getName()).as(String.class),parm.getValue().toString()));
						break;
                         case BETWEEN:
                         String[] arr = (String[])parm.getValue();
							 list.add(builder.between(root.get(parm.getName()).as(String.class),arr[0],arr[1]));
                         break;
						default:
							break;
					}
				});
				return builder.and(list.stream().toArray(Predicate[]::new));
			}
		};
	}

	/**将map值全部转换为小写
	 *
	 * @param orgMap
	 * @return
	 */
	public static Map<String, Object> transformLowerCase(Map<String, Object> orgMap) {
		Map<String, Object> resultMap = new HashMap<>();
		if (orgMap == null || orgMap.isEmpty()) {
			return resultMap;
		}
		Set<String> keySet = orgMap.keySet();
		for (String key : keySet) {
			String newKey = key.toLowerCase();
			resultMap.put(newKey, orgMap.get(key));
		}
		return resultMap;
	}


	/**
	 * 将Map中的key由下划线转换为驼峰
	 * @param map
	 * @return
	 */
	public static Map<String, Object> formatHumpName(Map<String, Object> map) {
		Map<String, Object> newMap = new HashMap<String, Object>();
		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = toFormatHump(key);
			newMap.put(newKey, entry.getValue());
		}
		return newMap;
	}

	/**
	 * 将字符串转由下划线转换为驼峰
	 * @param colName
	 * @return
	 */
	public static String toFormatHump(String colName) {
		StringBuffer sb = new StringBuffer();
		String[] str = colName.toLowerCase().split("_");
		int i = 0;
		for (String s : str) {
			if (s.length() == 1) {
				s = s.toUpperCase();
			}
			i++;
			if (i == 1) {
				sb.append(s);
				continue;
			}
			if (s.length() > 0) {
				sb.append(s.substring(0, 1).toUpperCase());
				sb.append(s.substring(1));
			}
		}
		return sb.toString();
	}

}
