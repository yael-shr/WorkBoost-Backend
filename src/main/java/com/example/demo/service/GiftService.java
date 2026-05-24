package com.example.demo.service;

import com.example.demo.dto.GiftDTO;
import com.example.demo.dto.BuyGiftRequestDTO;
import java.util.List;

public interface GiftService {
 
    GiftDTO addGift(GiftDTO giftDTO);
    
    List<GiftDTO> getAllGifts();
    
    void buyGift(BuyGiftRequestDTO buyRequest);
}
