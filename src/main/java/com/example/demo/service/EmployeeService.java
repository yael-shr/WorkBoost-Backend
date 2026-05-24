package com.example.demo.service;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.EmployeeRegisterDTO;
import java.util.List;

public interface EmployeeService {
    
    // הגדרת החוזה: כל מי שיממש את השירות הזה חייב לדעת לרשום עובד
    EmployeeDTO registerEmployee(EmployeeRegisterDTO registerDTO);
    
    // הגדרת החוזה: חייב לדעת להחזיר את כל העובדים
    List<EmployeeDTO> getAllEmployees();
}