package com.api.restuniversity.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum Permission {
    ADMIN("ADMIN"),
    COORDINATOR("COORDINATOR"),
    PROFESSOR("PROFESSOR"),
    STUDENT("STUDENT");

    private final String value;

    private Permission(String value) {
        this.value = value;
    }

}