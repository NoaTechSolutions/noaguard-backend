package com.noatechsolutions.noaguard.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.util.List;

public class ParentUpdateRequest {

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String middleName;

    @Size(max = 50)
    private String lastName;

    @Size(max = 50)
    private String relationshipToStudent;

    @Size(max = 20)
    private String phone;

    @Size(max = 20)
    private String workPhone;

    @Size(max = 100)
    private String occupation;

    @Email
    private String email;

    private AddressUpdateRequest address;

    private List<Long> studentIds;

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getRelationshipToStudent() { return relationshipToStudent; }
    public void setRelationshipToStudent(String relationshipToStudent) { this.relationshipToStudent = relationshipToStudent; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getWorkPhone() { return workPhone; }
    public void setWorkPhone(String workPhone) { this.workPhone = workPhone; }

    public String getOccupation() { return occupation; }
    public void setOccupation(String occupation) { this.occupation = occupation; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public AddressUpdateRequest getAddress() { return address; }
    public void setAddress(AddressUpdateRequest address) { this.address = address; }

    public List<Long> getStudentIds() { return studentIds; }
    public void setStudentIds(List<Long> studentIds) { this.studentIds = studentIds; }
}
