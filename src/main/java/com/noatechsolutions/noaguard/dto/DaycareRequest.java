package com.noatechsolutions.noaguard.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DaycareRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    private String logoUrl;

    @NotBlank
    private String phone;

    @NotBlank
    @Email
    private String email;

    private Long adminId;

    private AddressRequest address;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getAdminId() { return adminId; }
    public void setAdminId(Long adminId) { this.adminId = adminId; }

    public AddressRequest getAddress() { return address; }
    public void setAddress(AddressRequest address) { this.address = address; }
}
