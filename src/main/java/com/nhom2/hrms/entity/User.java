package com.nhom2.hrms.entity;

import com.nhom2.hrms.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    String id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;

    String username;

    @Column(name = "password_hash")
    String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    Role role;
}
