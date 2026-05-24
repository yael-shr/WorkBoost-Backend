package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    // פונקציה מותאמת אישית ש-Spring תממש לבד לפי השם שלה!
    Optional<Employee> findByEmail(String email);
}