package com.example.demo.dto;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiftDTO {
    
    private Long id;
    private String title;
    private String description;
    private int priceInPoints;
    private int stock;
}
