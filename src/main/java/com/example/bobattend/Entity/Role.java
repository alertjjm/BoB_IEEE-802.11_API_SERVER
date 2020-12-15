package com.example.bobattend.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
//권한 entity
@AllArgsConstructor
@Getter
public enum Role {
    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER");
    private String value;
}
