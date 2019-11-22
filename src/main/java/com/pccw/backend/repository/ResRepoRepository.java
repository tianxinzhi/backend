package com.pccw.backend.repository;


import com.pccw.backend.entity.DbResRepo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ResRepoRepository extends BaseRepository<DbResRepo> {

    @Query(value = "select  count(*)  from res_repo where  id = ?1 and  repo_type='W'",nativeQuery = true)
    int getRepoType(@Param("shop") long shop);

}
