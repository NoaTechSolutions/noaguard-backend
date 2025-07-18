package com.noatechsolutions.noaguard.dto;

import com.noatechsolutions.noaguard.entity.Address;

import java.time.LocalDateTime;


public class ParentResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    private AddressResponse address;

    private Long daycareId;   // Solo ID
    private Long studentId;   // Solo ID

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String updatedBy;

    public ParentResponse() {}

    public ParentResponse(Long id, String firstName, String lastName, String email, String phone,
                          AddressResponse address, Long daycareId, Long studentId,
                          LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.daycareId = daycareId;
        this.studentId = studentId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters y Setters

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AddressResponse getAddress() {
        return address;
    }

    public void setAddress(AddressResponse address) {
        this.address = address;
    }

    public Long getDaycareId() {
        return daycareId;
    }

    public void setDaycareId(Long daycareId) {
        this.daycareId = daycareId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
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
}