package com.example.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by arahansa on 2016-01-05.
 */
@Data
@AllArgsConstructor
public class ProductDTO {

    private String username;
    private int price;

    public ProductDTO() {
    }


}
