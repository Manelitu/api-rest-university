package com.api.restuniversity.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum Roles {
    ADMIN("ADMIN"),
    COORDINATOR("COORDINATOR"),
    PROFESSOR("PROFESSOR"),
    STUDENT("STUDENT");

    private final String role;

    private Roles(String role) {
        this.role = role;
    }

}