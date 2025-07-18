package com.noatechsolutions.noaguard.dto;

import java.util.List;

public class UserUpdateRequest {

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String nickname;

    private Boolean active;

    private List<Long> roleIds;

    // Getters y Setters

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    public List<Long> getRoleIds() {
        return roleIds;
    }
    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
}