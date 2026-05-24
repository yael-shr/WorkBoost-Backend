package com.example.demo.controller;

import com.example.demo.dto.GiftDTO;
import com.example.demo.dto.BuyGiftRequestDTO;
import com.example.demo.service.GiftService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gifts")
@CrossOrigin(origins = "*")
public class GiftController {

    private final GiftService giftService;

    public GiftController(GiftService giftService) {
        this.giftService = giftService;
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
    public String buyGift(@RequestBody BuyGiftRequestDTO buyRequest) {
        giftService.buyGift(buyRequest);
        
        return "Success: Gift purchased successfully!";
    }
}