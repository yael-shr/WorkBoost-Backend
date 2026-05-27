package com.example.demo.controller;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders; // ייבוא מובנה ויציב ב-Core
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class EmployeeControllerTest {

private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockitoBean
    private EmployeeService employeeService;

    // 🔥 התיקון: מחיקת ה-@Autowired העודף, והשארת המשתנה כפרטי
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // בנייה ידנית של צינור ה-HTTP
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        // 🔥 התיקון: אתחול ידני ומהיר של ה-ObjectMapper ללא תלות בקונטקסט
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void login_Success_ShouldReturnStatus200AndEmployeeDTO() throws Exception {
        EmployeeDTO mockResponseDTO = new EmployeeDTO(1L, "יעל שרגע", "yael@example.com", 150, 0);
        
        when(employeeService.loginEmployee("yael@example.com", "Password123")).thenReturn(mockResponseDTO);

        // 1. הגדרת ה-Map
        Map<String, String> loginRequest = new HashMap<>();
        
        // 🔥 התיקון: שימוש במתודת .put() המיועדת עבור Map
        loginRequest.put("email", "yael@example.com");
        loginRequest.put("password", "Password123");
        mockMvc.perform(post("/api/employees/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("יעל שרגע"))
                .andExpect(jsonPath("$.points").value(150));
    }

    @Test
    void login_InvalidCredentials_ShouldReturnStatus401Unauthorized() throws Exception {
        when(employeeService.loginEmployee("wrong@example.com", "badpass"))
                .thenThrow(new RuntimeException("Error: Invalid email or password"));

         Map<String, String> invalidRequest = new HashMap<>();
         invalidRequest.put("email", "wrong@example.com");
         invalidRequest.put("password", "badpass");

        mockMvc.perform(post("/api/employees/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isUnauthorized());
    }
}