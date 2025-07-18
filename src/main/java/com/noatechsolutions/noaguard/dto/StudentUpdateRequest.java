package com.noatechsolutions.noaguard.dto;

import java.time.LocalDate;

public class StudentUpdateRequest {

    private String firstName;
    private String lastName;
    private String nickname;
    private LocalDate birthdate;
    private AddressRequest address;
    private Long daycareId;
    private Long teacherId;

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

    public AddressRequest getAddress() {
        return address;
    }

    public void setAddress(AddressRequest address) {
        this.address = address;
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
}