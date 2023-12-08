package com.taskmanagement.system.entity;

import jakarta.persistence.*;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String text;
    @ManyToOne
    private Task task;
}
