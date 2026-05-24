package com.example.demo.service;

import com.example.demo.dto.GiftDTO;
import com.example.demo.dto.BuyGiftRequestDTO;
import com.example.demo.model.Employee;
import com.example.demo.model.Gift;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.GiftRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GiftServiceImpl implements GiftService {

    private final GiftRepository giftRepository;
    private final EmployeeRepository employeeRepository;

    public GiftServiceImpl(GiftRepository giftRepository, EmployeeRepository employeeRepository) {
        this.giftRepository = giftRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public GiftDTO addGift(GiftDTO giftDTO) {
        Gift gift = new Gift();
        gift.setTitle(giftDTO.getTitle());
        gift.setDescription(giftDTO.getDescription());
        gift.setPriceInPoints(giftDTO.getPriceInPoints());
        gift.setStock(giftDTO.getStock());

        Gift savedGift = giftRepository.save(gift);
        return convertToDTO(savedGift);
    }

    @Override
    public List<GiftDTO> getAllGifts() {
        return giftRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional 
    public void buyGift(BuyGiftRequestDTO buyRequest) {
        Employee employee = employeeRepository.findById(buyRequest.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Error: Employee not found!"));

        Gift gift = giftRepository.findById(buyRequest.getGiftId())
                .orElseThrow(() -> new RuntimeException("Error: Gift not found!"));

        if (gift.getStock() <= 0) {
            throw new RuntimeException("Error: Gift is out of stock!");
        }

        if (employee.getPoints() < gift.getPriceInPoints()) {
            throw new RuntimeException("Error: Not enough points to buy this gift!");
        }

        employee.setPoints(employee.getPoints() - gift.getPriceInPoints());
        gift.setStock(gift.getStock() - 1);

        employeeRepository.save(employee);
        giftRepository.save(gift);
    }

    private GiftDTO convertToDTO(Gift gift) {
        return new GiftDTO(
                gift.getId(),
                gift.getTitle(),
                gift.getDescription(),
                gift.getPriceInPoints(),
                gift.getStock()
        );
    }
}