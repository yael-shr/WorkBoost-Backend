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

        // 1. שליפת שני העובדים המובילים כרגע
        List<Employee> topEmployees = employeeRepository.findTop2ByOrderByCompletedTasksCountDesc();

        // 2. שליפת כל העובדים במערכת (כך שאנחנו עובדים על אותם אובייקטים בדיוק בזיכרון)
        List<Employee> allEmployees = employeeRepository.findAll();

        // 3. לוגיקת חלוקת הבונוס בזיכרון בלבד
        for (Employee topEmployee : topEmployees) {
            if (topEmployee.getCompletedTasksCount() > 0) {
                System.out.println("LOG: Top employee found! " + topEmployee.getName() + " with " + topEmployee.getCompletedTasksCount() + " tasks.");
                
                // מוצאים את העובד האמיתי מתוך הרשימה המלאה כדי לעדכן אותו
                for (Employee emp : allEmployees) {
                    if (emp.getId().equals(topEmployee.getId())) {
                        emp.setPoints(emp.getPoints() + 50); // הוספת הבונוס
                        System.out.println("LOG: Prepared 50 bonus points for " + emp.getName());
                    }
                }
            }
        }

        // 4. עכשיו מאפסים את המונים לכולם בזיכרון
        for (Employee emp : allEmployees) {
            emp.setCompletedTasksCount(0);
        }

        // 5. שמירה אחת ויחידה ל-SQL Server שמבצעת את כל העדכונים במכה אחת!
        employeeRepository.saveAll(allEmployees);
        System.out.println("LOG: SQL Server updated successfully - Bonuses given and counters reset.");
    }
}