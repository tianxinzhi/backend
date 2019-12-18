package com.pccw.backend.repository;

import com.pccw.backend.entity.DbResAccountRole;
import com.pccw.backend.entity.DbResAttr;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResAccountRoleRepository extends BaseRepository<DbResAccountRole> {

    List<DbResAccountRole> getDbResAccountRolesByRoleId(Long roleId);
}
