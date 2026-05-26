package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BonusScheduler {

    private final EmployeeRepository employeeRepository;

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

@Scheduled(fixedRate = 60000)
    public void rewardTopEmployeesAutomated() {
        System.out.println("LOG: Automated scheduler started - Searching for the top 2 employees...");

        List<Employee> topEmployees = employeeRepository.findTop2ByOrderByCompletedTasksCountDesc();

        List<Employee> allEmployees = employeeRepository.findAll();

        for (Employee topEmployee : topEmployees) {
            if (topEmployee.getCompletedTasksCount() > 0) {
                System.out.println("LOG: Top employee found! " + topEmployee.getName() + " with " + topEmployee.getCompletedTasksCount() + " tasks.");
                
                for (Employee emp : allEmployees) {
                    if (emp.getId().equals(topEmployee.getId())) {
                        emp.setPoints(emp.getPoints() + 50);
                        System.out.println("LOG: Prepared 50 bonus points for " + emp.getName());
                    }
                }
            }
        }

        for (Employee emp : allEmployees) {
            emp.setCompletedTasksCount(0);
        }

        employeeRepository.saveAll(allEmployees);
        System.out.println("LOG: SQL Server updated successfully - Bonuses given and counters reset.");
    }
}