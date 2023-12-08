package com.taskmanagement.system.redis;


import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import com.taskmanagement.system.entity.User;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(value = "userSession", timeToLive = 60 * 60 * 24)
public class UserSession {
    @Id
    private String id;

    private User value;
}
