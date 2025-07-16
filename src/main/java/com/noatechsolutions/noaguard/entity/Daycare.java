package com.noatechsolutions.noaguard.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_daycare")
public class Daycare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    private String logoUrl;

    @NotBlank
    private String phone;

    @NotBlank
    @Email
    private String email;

    @Embedded
    @Valid
    private Address address;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String updatedBy;
}
