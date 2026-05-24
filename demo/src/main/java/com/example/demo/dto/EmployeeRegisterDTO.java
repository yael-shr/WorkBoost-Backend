package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRegisterDTO {
    
    private String name;
    private String email;
    private String password;
}