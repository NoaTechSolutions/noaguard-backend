package com.noatechsolutions.noaguard.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ParentRequest {

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String middleName;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @Size(max = 30)
    private String relationshipToStudent;

    @NotBlank
    private String phone;

    private String workPhone;

    private String occupation;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private List<Long> studentIds; // ✅ Ahora es lista

    @Valid
    private AddressRequest address; // Opcional

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRelationshipToStudent() {
        return relationshipToStudent;
    }

    public void setRelationshipToStudent(String relationshipToStudent) {
        this.relationshipToStudent = relationshipToStudent;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Long> studentIds) {
        this.studentIds = studentIds;
    }

    public AddressRequest getAddress() {
        return address;
    }

    public void setAddress(AddressRequest address) {
        this.address = address;
    }
}
