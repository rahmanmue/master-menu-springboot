package com.enigmacamp.mastermenu.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MenuReq {

    private String id;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @NotEmpty(message = "Name is required")
    @Size(max = 50, message = "Name should not exceed 50 characters")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Name must contain only letters and spaces")
    private String name;

    private String imageUrl;

    @Size(max = 255, message = "Description should not exceed 255 characters")
    private String description;

    @NotNull(message = "Price cannot be null")
    private int price;

    @NotNull(message = "Stock cannot be null")
    private int stock;

    @NotNull(message = "Category id cannot be null")
    private String categoryId; 

}
