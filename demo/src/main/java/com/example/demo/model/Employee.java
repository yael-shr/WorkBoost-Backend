package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity 
@Table(name = "employees") 
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
public class Employee {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false) 
    private String name;

    @Column(unique = true, nullable = false) 
    private String email;

    @Column(nullable = false)
    private String password;

    private int points = 0; 
}