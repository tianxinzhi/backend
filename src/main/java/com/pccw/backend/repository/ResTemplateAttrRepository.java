package com.pccw.backend.repository;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

// import com.pccw.backend.repository.dto.resourcesDTO.RestemplateAttrDTO;
import com.pccw.backend.entity.DbresTemplateAttr;

@Profile("dev1")
public interface ResTemplateAttrRepository extends JpaRepository<DbresTemplateAttr, Long> {

	//void saveAll(List<ResemplateAttrDTO> templateAttr); 
	
List<DbresTemplateAttr> findByTemplateId(Long templateId);

}
