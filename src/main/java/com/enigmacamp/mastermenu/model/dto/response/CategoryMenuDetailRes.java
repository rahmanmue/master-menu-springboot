package com.enigmacamp.mastermenu.model.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

// import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CategoryMenuDetailRes {
    private String id;
    private String name;
    private String description;
    // private LocalDateTime createdAt;
    // private LocalDateTime updatedAt;
}
