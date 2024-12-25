package com.nhom2.hrms.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Position {
    @Id
    @Column(name = "position_id")
    String positionId;
    String title;
    String description;
}
