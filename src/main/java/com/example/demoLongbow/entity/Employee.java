package com.example.demoLongbow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "employees", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"})
})
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;
    private String name;
    private String email;
    private String mobile;
    private String password;

    private String linkId;
    @Column(name = "creation_on")
    private LocalDateTime creationOn=LocalDateTime.now();
    private  Boolean isActive=true;
    private Boolean isEmailVerified=false;
}
