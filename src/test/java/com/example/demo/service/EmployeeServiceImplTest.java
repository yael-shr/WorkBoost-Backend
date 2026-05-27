package com.example.demo.service;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.EmployeeRegisterDTO;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService; 

    private Employee mockEmployee;
    private EmployeeRegisterDTO registerDTO;

    @BeforeEach
    void setUp() {
        mockEmployee = new Employee();
        mockEmployee.setId(1L);
        mockEmployee.setName("משה כהן");
        mockEmployee.setEmail("moshe@example.com");
        mockEmployee.setPassword("123456");
        mockEmployee.setPoints(0);

        registerDTO = new EmployeeRegisterDTO("משה כהן", "moshe@example.com", "123456");
    }

    @Test
    void registerEmployee_Success_ShouldReturnSavedEmployee() {
        when(employeeRepository.findByEmail(registerDTO.getEmail())).thenReturn(Optional.empty());
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);

        EmployeeDTO result = employeeService.registerEmployee(registerDTO);

        assertNotNull(result);
        assertEquals("moshe@example.com", result.getEmail());
        assertEquals(1L, result.getId());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void registerEmployee_EmailAlreadyExists_ShouldThrowException() {
        when(employeeRepository.findByEmail(registerDTO.getEmail())).thenReturn(Optional.of(mockEmployee));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            employeeService.registerEmployee(registerDTO);
        });

        assertEquals("Error: Email is already in use!", exception.getMessage());
        verify(employeeRepository, never()).save(any(Employee.class)); 
    }

    @Test
    void loginEmployee_Success_ShouldReturnEmployeeDTO() {
        when(employeeRepository.findByEmail("moshe@example.com")).thenReturn(Optional.of(mockEmployee));

        EmployeeDTO result = employeeService.loginEmployee("moshe@example.com", "123456");

        assertNotNull(result);
        assertEquals("משה כהן", result.getName());
    }

    @Test
    void loginEmployee_WrongPassword_ShouldThrowException() {
        when(employeeRepository.findByEmail("moshe@example.com")).thenReturn(Optional.of(mockEmployee));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            employeeService.loginEmployee("moshe@example.com", "wrong_password");
        });

        assertEquals("Error: Invalid email or password", exception.getMessage());
    }
}