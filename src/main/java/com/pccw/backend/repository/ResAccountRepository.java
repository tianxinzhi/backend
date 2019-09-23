package com.pccw.backend.repository;

import com.pccw.backend.entity.DbResAccount;
import com.pccw.backend.entity.DbResAttr;
import org.springframework.stereotype.Repository;

@Repository
public interface ResAccountRepository extends BaseRepository<DbResAccount> {

    DbResAccount getDbResAccountsByAccountNameAndPassword(String accountName,String password);
}
