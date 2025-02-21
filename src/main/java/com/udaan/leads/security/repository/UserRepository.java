package com.udaan.leads.security.repository;

import com.udaan.leads.security.entity.SystemUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<SystemUser, String> {

    @Query("{username:'?0'}")
    SystemUser findUserByUsername(String username);
}
