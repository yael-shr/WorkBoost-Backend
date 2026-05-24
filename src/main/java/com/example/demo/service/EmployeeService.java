package com.example.demo.service;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.EmployeeRegisterDTO;
import java.util.List;

public interface EmployeeService {
    
    EmployeeDTO registerEmployee(EmployeeRegisterDTO registerDTO);
    EmployeeDTO loginEmployee(String email, String password);
    List<EmployeeDTO> getAllEmployees();
}