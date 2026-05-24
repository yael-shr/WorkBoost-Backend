package com.example.demo.controller;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.EmployeeRegisterDTO;
import com.example.demo.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*") // מאפשר בהמשך לצד הלקוח להתחבר לשרת ללא חסימות דפדפן
public class EmployeeController {

    private final EmployeeService employeeService;

    // הזרקת התלויות של ה-Service (הממשק) דרך הבנאי
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // נקודת קצה (Endpoint) לרישום עובד חדש
    @PostMapping("/register")
    public EmployeeDTO register(@RequestBody EmployeeRegisterDTO registerDTO) {
        // קריאה ללוגיקה העסקית שבשכבת ה-Service והחזרת התוצאה המאובטחת
        return employeeService.registerEmployee(registerDTO);
    }

    // נקודת קצה לקבלת רשימת כל העובדים במערכת
    @GetMapping
    public List<EmployeeDTO> getAll() {
        return employeeService.getAllEmployees();
    }
}