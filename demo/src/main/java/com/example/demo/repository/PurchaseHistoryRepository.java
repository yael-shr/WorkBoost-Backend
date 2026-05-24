package com.example.demo.repository;

import com.example.demo.model.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
    
    List<PurchaseHistory> findByEmployeeIdOrderByPurchaseDateDesc(Long employeeId);
}