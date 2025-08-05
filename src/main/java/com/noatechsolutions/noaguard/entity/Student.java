package com.noatechsolutions.noaguard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String firstName;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(length = 50)
    private String nickname;

    @PastOrPresent
    private LocalDate birthdate;

    // Relación 1:1 con Daycare
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daycare_id", nullable = false)
    private Daycare daycare;

    // Relación 1:1 con Teacher (User con rol TEACHER)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private User teacher;

    // Relación 1:1 con DaycareAdmin (User con rol DAYCARE_ADMIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daycare_admin_id")
    private User daycareAdmin;

    // Relación 1:N con Parent
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Parent> parents;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String updatedBy;

    public Student() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public LocalDate getBirthdate() { return birthdate; }
    public void setBirthdate(LocalDate birthdate) { this.birthdate = birthdate; }

    public Daycare getDaycare() { return daycare; }
    public void setDaycare(Daycare daycare) { this.daycare = daycare; }

    public User getTeacher() { return teacher; }
    public void setTeacher(User teacher) { this.teacher = teacher; }

    public User getDaycareAdmin() { return daycareAdmin; }
    public void setDaycareAdmin(User daycareAdmin) { this.daycareAdmin = daycareAdmin; }

    public List<Parent> getParents() { return parents; }
    public void setParents(List<Parent> parents) { this.parents = parents; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
}
