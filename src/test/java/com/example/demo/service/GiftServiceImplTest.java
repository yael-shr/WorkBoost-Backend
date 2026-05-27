package com.example.demo.service;

import com.example.demo.dto.BuyGiftRequestDTO;
import com.example.demo.model.Employee;
import com.example.demo.model.Gift;
import com.example.demo.model.PurchaseHistory;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.GiftRepository;
import com.example.demo.repository.PurchaseHistoryRepository;
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
public class GiftServiceImplTest {

    @Mock
    private GiftRepository giftRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private PurchaseHistoryRepository purchaseHistoryRepository;

    @InjectMocks
    private GiftServiceImpl giftService; 
    private Employee mockEmployee;
    private Gift mockGift;
    private BuyGiftRequestDTO mockRequest;

    @BeforeEach
    void setUp() {
        mockEmployee = new Employee();
        mockEmployee.setId(1L);
        mockEmployee.setName("יעל בדיקה");
        mockEmployee.setPoints(100); 

        mockGift = new Gift();
        mockGift.setId(2L);
        mockGift.setTitle("אוזניות אלחוטיות");
        mockGift.setPriceInPoints(60); 
        mockGift.setStock(5);

        mockRequest = new BuyGiftRequestDTO(1L, 2L);
    }

    @Test
    void buyGift_Success_ShouldDeductPointsAndStock() {
       
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(mockEmployee));
        when(giftRepository.findById(2L)).thenReturn(Optional.of(mockGift));
        when(purchaseHistoryRepository.save(any(PurchaseHistory.class))).thenReturn(new PurchaseHistory());

        giftService.buyGift(mockRequest);

        assertEquals(40, mockEmployee.getPoints()); 
        assertEquals(4, mockGift.getStock()); 
        assertEquals(4, mockGift.getStock()); 
        verify(purchaseHistoryRepository, times(1)).save(any(PurchaseHistory.class));
    }

    @Test
    void buyGift_NotEnoughPoints_ShouldThrowException() {
        mockEmployee.setPoints(30);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(mockEmployee));
        when(giftRepository.findById(2L)).thenReturn(Optional.of(mockGift));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            giftService.buyGift(mockRequest);
        });

        assertEquals("Error: Not enough points to buy this gift!", exception.getMessage());
        
        assertEquals(30, mockEmployee.getPoints());
        assertEquals(5, mockGift.getStock());
    }
}