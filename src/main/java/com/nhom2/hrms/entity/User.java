package com.nhom2.hrms.entity;

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

    @Column(name = "role")
    String roles;

    public void setRoles(Set<String> roles) {
        this.roles = String.join(", ", roles);
    }

    public Set<String> getRoles() {
        return new HashSet<>(Arrays.asList(roles.split(", ")));
    }
}
