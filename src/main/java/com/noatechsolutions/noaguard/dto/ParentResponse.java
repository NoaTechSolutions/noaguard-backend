package com.noatechsolutions.noaguard.dto;

import java.time.LocalDateTime;

public class ParentResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private AddressResponse address;

    private Long daycareId;
    private String daycareName;

    private Long studentId;
    private String studentFirstName;
    private String studentLastName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters y setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public AddressResponse getAddress() { return address; }
    public void setAddress(AddressResponse address) { this.address = address; }

    public Long getDaycareId() { return daycareId; }
    public void setDaycareId(Long daycareId) { this.daycareId = daycareId; }

    public String getDaycareName() { return daycareName; }
    public void setDaycareName(String daycareName) { this.daycareName = daycareName; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentFirstName() { return studentFirstName; }
    public void setStudentFirstName(String studentFirstName) { this.studentFirstName = studentFirstName; }

    public String getStudentLastName() { return studentLastName; }
    public void setStudentLastName(String studentLastName) { this.studentLastName = studentLastName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}