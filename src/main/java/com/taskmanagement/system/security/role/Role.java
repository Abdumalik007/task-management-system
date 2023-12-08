package com.taskmanagement.system.security.role;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public enum Role {
    USER(List.of(
            Permission.USER_CREATE,
            Permission.USER_READ,
            Permission.USER_UPDATE,
            Permission.USER_DELETE
    ));

    private final List<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority> simpleGrantedAuthorities
                = new ArrayList<>(List.of(new SimpleGrantedAuthority("ROLE_".concat(this.name()))));

        simpleGrantedAuthorities.addAll(
                permissions.stream().map(
                        p -> new SimpleGrantedAuthority(p.name())
                ).toList()
        );
        return simpleGrantedAuthorities;
    }

}
