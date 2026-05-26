package com.example.demo.controller;

import com.example.demo.dto.GiftDTO;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.BuyGiftRequestDTO;
import com.example.demo.service.GiftService;
import com.example.demo.service.EmployeeService; 

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gifts")
@CrossOrigin(origins = "*") 
public class GiftController {

    private final GiftService giftService;
    private final EmployeeService employeeService; 
    public GiftController(GiftService giftService, EmployeeService employeeService) {
        this.giftService = giftService;
        this.employeeService = employeeService;
    }

    @PostMapping
    public GiftDTO createGift(@RequestBody GiftDTO giftDTO) {
        return giftService.addGift(giftDTO);
    }

    @GetMapping
    public List<GiftDTO> getAll() {
        return giftService.getAllGifts();
    }

    @PostMapping("/buy")
    public ResponseEntity<?> buyGift(@RequestBody BuyGiftRequestDTO buyRequest) {
        try {
            giftService.buyGift(buyRequest);
            
            EmployeeDTO updatedEmployee = employeeService.getAllEmployees().stream()
                    .filter(emp -> emp.getId().equals(buyRequest.getEmployeeId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Error: Employee not found after purchase"));
            
            return ResponseEntity.ok(updatedEmployee);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}