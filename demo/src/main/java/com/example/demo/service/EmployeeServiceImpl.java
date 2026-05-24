package com.example.demo.service;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.EmployeeRegisterDTO;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service // האנוטציה עוברת לפה, כי זה הרכיב האמיתי שרץ בזיכרון
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    // הזרקת ה-Repository כרגיל דרך הבנאי
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDTO registerEmployee(EmployeeRegisterDTO registerDTO) {
        if (employeeRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        Employee employee = new Employee();
        employee.setName(registerDTO.getName());
        employee.setEmail(registerDTO.getEmail());
        employee.setPassword(registerDTO.getPassword());
        employee.setPoints(0);

        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDTO(savedEmployee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setPoints(employee.getPoints());
        return dto;
    }
}