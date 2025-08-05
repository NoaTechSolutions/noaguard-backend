package com.noatechsolutions.noaguard.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class ParentUpdateRequest {

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    private String phone;

    @Email
    private String email;

    private Long studentId;

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
}
