package com.enigmacamp.mastermenu.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuDetailRes {
    private String id;
    private CategoryMenuRes category; 
    private String imageUrl;
    private String name;
    private String description;
    private int price;
    private int stock;
    // private LocalDateTime createdAt;
    // private LocalDateTime updatedAt;
}
