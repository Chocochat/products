package com.demo.io.models.responses;

import lombok.Data;

import java.util.List;

@Data
public class PriceResponse {
    private String customer;
    private List<String> items;
    private double total;
}
