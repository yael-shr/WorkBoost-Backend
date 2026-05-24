package com.example.demo.dto;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor  
@AllArgsConstructor

public class BuyGiftRequestDTO {
    private Long employeeId;
    private Long giftId;    
}
