package com.example.demo.controller;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.EmployeeRegisterDTO;
import com.example.demo.service.EmployeeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:5173") // מאפשר לריאקט לגשת ל-API ללא חסימות דפדפן
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

    @GetMapping("/by-email/{email}")
    public ResponseEntity<?> getEmployeeByEmail(@PathVariable String email) {
        try {
            // שליפת ה-EmployeeDTO המעודכן ביותר מהמסד לפי האימייל
            EmployeeDTO employeeDTO = employeeService.getEmployeeByEmail(email);
            
            return ResponseEntity.ok(employeeDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            EmployeeDTO employeeDTO = employeeService.loginEmployee(loginRequest.getEmail(), loginRequest.getPassword());
            
            return ResponseEntity.ok(employeeDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    
    public static class LoginRequest {
        private String email;
        private String password;
    
        // גטרים וסטרים (Getters & Setters)
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}