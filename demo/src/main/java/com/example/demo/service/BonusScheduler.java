package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BonusScheduler {

    private final EmployeeRepository employeeRepository;

    // הזרקת ה-Repository כדי שנוכל לעדכן את העובדים בבסיס הנתונים
    public BonusScheduler(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Scheduled(fixedRate = 30000)
    public void giveMonthlyBonusAutomated() {
        System.out.println("LOG: Automated scheduler started - Giving monthly bonus to all employees...");

        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {
            int currentPoints = employee.getPoints();
            employee.setPoints(currentPoints + 10);
        }

        employeeRepository.saveAll(employees);

        System.out.println("LOG: Successfully updated " + employees.size() + " employees with 10 bonus points.");
    }
}