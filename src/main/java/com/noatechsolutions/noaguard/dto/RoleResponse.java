package com.noatechsolutions.noaguard.dto;

import com.noatechsolutions.noaguard.enums.RoleType;

public class RoleResponse {
    private Long id;
    private RoleType name;

    public RoleResponse() {}

    public RoleResponse(Long id, RoleType name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleType getName() {
        return name;
    }

    public void setName(RoleType name) {
        this.name = name;
    }
}
