package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchase_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gift_id", nullable = false)
    private Gift gift;

    @Column(name = "purchase_date", nullable = false)
    private LocalDateTime purchaseDate;
}