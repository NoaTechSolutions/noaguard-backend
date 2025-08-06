package com.noatechsolutions.noaguard.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class StudentResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String nickname;
    private LocalDate birthdate;
    private Long daycareId;
    private Long teacherId;
    private Long daycareAdminId;
    private List<Long> parentIds;
    private boolean active; // Nuevo campo
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private AddressResponse address;
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Long getDaycareId() {
        return daycareId;
    }

    public void setDaycareId(Long daycareId) {
        this.daycareId = daycareId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getDaycareAdminId() {
        return daycareAdminId;
    }

    public void setDaycareAdminId(Long daycareAdminId) {
        this.daycareAdminId = daycareAdminId;
    }

    public List<Long> getParentIds() {
        return parentIds;
    }

    public void setParentIds(List<Long> parentIds) {
        this.parentIds = parentIds;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public AddressResponse getAddress() {
        return address;
    }

    public void setAddress(AddressResponse address) {
        this.address = address;
    }
}
