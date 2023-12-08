package com.taskmanagement.system.redis;


import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRedisRepository extends KeyValueRepository<UserSession, String> {

}

