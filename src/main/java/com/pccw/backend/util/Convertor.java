package com.pccw.backend.util;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.*;

/**
 * Convertor
 */

 public class Convertor {

    //  public static <T> Specification<T> convertSpecification() {
    //     return new Specification<T>() {
	// 		private static final long serialVersionUID = 1L;

	// 		@Override
	// 		public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
	// 		CriteriaBuilder builder) {
	// 			List<Predicate> list = new ArrayList<>();
	// 			list.add(builder.equal(root.get("skuNum").as(String.class),"C001"));
	// 			list.add(builder.like(root.get("repoNum").as(String.class), "%B%"));
	// 			return builder.and(list.stream().toArray(Predicate[]::new));
	// 		}
	// 	};
    // }
    public final static String test(){
        return "ok";
    }
}
