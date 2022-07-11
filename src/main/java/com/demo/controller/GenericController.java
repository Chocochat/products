package com.demo.controller;

import com.demo.io.entity.Deal;
import com.demo.io.entity.Discount;
import com.demo.io.repositories.DealsRepository;
import com.demo.io.repositories.DiscountsRepository;
import com.demo.io.repositories.ProductsRepository;
import com.demo.io.models.requests.PriceRequest;
import com.demo.io.models.responses.PriceResponse;
import com.demo.service.PriceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GenericController {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private DealsRepository dealsRepository;

    @Autowired
    private DiscountsRepository discountsRepository;

    @Autowired
    private PriceCalculator priceCalculator;

    @GetMapping("/allInfo")
    public List retrieveAllData() {
        List alldata = new ArrayList();
        alldata.add(productsRepository.findAll());
        alldata.add(dealsRepository.findAll());
        alldata.add(discountsRepository.findAll());
        return alldata;
    }

    @GetMapping("/price")
    public PriceResponse price(@RequestBody PriceRequest priceRequest) {
        String customer = priceRequest.getCustomer();
        Discount discount = null;
        Deal deal = null;
        if(!priceRequest.getCustomer().equalsIgnoreCase("default")) {

            discount = discountsRepository.findByCompany(customer);

            deal = dealsRepository.findByCompany(customer);

        }

        PriceResponse priceResponse = priceCalculator.calculate(productsRepository.findAll(), priceRequest, discount, deal);

        return priceResponse;
    }

}
