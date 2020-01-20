package com.pccw.backend.repository;


import com.pccw.backend.entity.DbResRepo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ResRepoRepository extends BaseRepository<DbResRepo> {


    DbResRepo findFirst1ByRepoCode(String repo_id);
}
