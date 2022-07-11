package com.demo.service;

import com.demo.io.entity.Deal;
import com.demo.io.entity.Discount;
import com.demo.io.entity.Product;
import com.demo.io.models.requests.PriceRequest;
import com.demo.io.models.responses.PriceResponse;

import java.util.List;

public interface PriceCalculator {
    PriceResponse calculate(List<Product> products, PriceRequest priceRequest, Discount discount, Deal deal);
}
