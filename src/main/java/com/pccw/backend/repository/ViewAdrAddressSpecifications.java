package com.pccw.backend.repository;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.pccw.backend.entity.DbViewAdrAddress;

public class ViewAdrAddressSpecifications {

	public static Specification<DbViewAdrAddress> findByTypeId(String type, Long id) {
		return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(type), id);
        };
	}

	public static Specification<DbViewAdrAddress> findByCountryIdAndTypeId(String type, Long id, Long countryId) {
		return (root, query, criteriaBuilder) -> {
			Predicate equalToTypeId = criteriaBuilder.equal(root.get(type), id);
			Predicate equalToCountryId = criteriaBuilder.equal(root.get("countryId"), countryId);
            return criteriaBuilder.and(equalToTypeId, equalToCountryId);
        };
	}
}
