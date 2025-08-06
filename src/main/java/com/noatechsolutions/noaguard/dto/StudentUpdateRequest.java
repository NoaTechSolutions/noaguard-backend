package com.noatechsolutions.noaguard.dto;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class StudentUpdateRequest {

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Size(max = 50)
    private String nickname;

    private LocalDate birthdate;
    private Long daycareId;
    private Long teacherId;
    private Long daycareAdminId;
    private AddressRequest address;
    private Boolean active; // ✅ Nuevo campo

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public LocalDate getBirthdate() { return birthdate; }
    public void setBirthdate(LocalDate birthdate) { this.birthdate = birthdate; }

    public Long getDaycareId() { return daycareId; }
    public void setDaycareId(Long daycareId) { this.daycareId = daycareId; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public Long getDaycareAdminId() { return daycareAdminId; }
    public void setDaycareAdminId(Long daycareAdminId) { this.daycareAdminId = daycareAdminId; }

    public AddressRequest getAddress() { return address; }
    public void setAddress(AddressRequest address) { this.address = address; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
