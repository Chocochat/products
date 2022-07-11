package com.demo.io.models.requests;

import lombok.Data;

import java.util.List;
@Data
public class PriceRequest {
    private String customer;
    private List<String> items;

    public PriceRequest(String customer, List<String> items) {
        this.customer = customer;
        this.items = items;
    }
}
