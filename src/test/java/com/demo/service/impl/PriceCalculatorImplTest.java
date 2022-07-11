package com.demo.service.impl;

import com.demo.exception.ErrorMessages;
import com.demo.exception.UserServiceExceptions;
import com.demo.io.entity.Deal;
import com.demo.io.entity.Discount;
import com.demo.io.entity.Product;
import com.demo.io.models.requests.PriceRequest;
import com.demo.io.models.responses.PriceResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

class PriceCalculatorImplTest {
    Product productClassic = new Product("classic", "classic description", 100.10);
    Product productStd = new Product("std", "std description", 200.10);
    Product productPremium = new Product("premium", "premium description", 300.10);

    List<Product> products = Arrays.asList(productClassic, productStd, productPremium);

    @DisplayName("Default where there is no discount")
    @Test
    void calculateDefault() {
        //default case
        PriceRequest priceRequest = new PriceRequest("default", Arrays.asList("classic", "std", "premium"));
        Discount discount = null;
        Deal deal = null;
        PriceResponse priceResponse = new PriceCalculatorImpl().calculate(products, priceRequest, discount, deal);
        Assertions.assertEquals(600.30, priceResponse.getTotal());
    }

    @DisplayName("Not Default but also not also in premier list should go to default")
    @Test
    void notPremierList() {
        PriceRequest priceRequest = new PriceRequest("xyz", Arrays.asList("classic", "std", "premium"));
        Discount discount = null;
        Deal deal = null;
        PriceResponse priceResponse = new PriceCalculatorImpl().calculate(products, priceRequest, discount, deal);
        Assertions.assertEquals(600.30, priceResponse.getTotal());
    }

    @DisplayName("Not Default but also not also in premier list should go to default")
    @Test
    void randomList() {
        PriceRequest priceRequest = new PriceRequest("xyz", Arrays.asList("classic", "premium", "premium"));
        Discount discount = null;
        Deal deal = null;
        PriceResponse priceResponse = new PriceCalculatorImpl().calculate(products, priceRequest, discount, deal);
        Assertions.assertEquals(700.30, priceResponse.getTotal());
    }

    @DisplayName("Premium alone and has discount")
    @Test
    void premiumAlone() {
        PriceRequest priceRequest = new PriceRequest("xyz", Arrays.asList( "premium", "premium", "premium"));
        Discount discount = new Discount("myer", 0.0, 0.0, 0.0);
        Deal deal = new Deal("myer", "1:1", "1:1", "3:2");
        PriceResponse priceResponse = new PriceCalculatorImpl().calculate(products, priceRequest, discount, deal);
        Assertions.assertEquals(600.20, priceResponse.getTotal());
    }

    @DisplayName("Has Deal and Discount")
    @Test
    void dealAndDiscount() {
        PriceRequest priceRequest = new PriceRequest("xyz", Arrays.asList( "classic", "std", "std", "std", "std", "std", "premium", "premium", "premium"));
        Discount discount = new Discount("myer", 50.0, 0.0, 100.1);
        Deal deal = new Deal("myer", "1:1", "5:4", "1:1");
        PriceResponse priceResponse = new PriceCalculatorImpl().calculate(products, priceRequest, discount, deal);
        Assertions.assertEquals(1150.70, priceResponse.getTotal());
    }

    @DisplayName("delta classic should have default price")
    @Test
    void classicListTest() {
        PriceRequest priceRequest = new PriceRequest("xyz", Arrays.asList("classic", "classic", "classic", "classic", "classic", "premium"));
        Discount discount = new Discount("myer", 0.0, 0.0, 0.0);
        Deal deal = new Deal("myer", "3:2", "1:1", "1:1");
        PriceResponse priceResponse = new PriceCalculatorImpl().calculate(products, priceRequest, discount, deal);
        Assertions.assertEquals(700.50, priceResponse.getTotal());
    }

    @DisplayName("Multiple std customised price list for the same customer")
    @Test
    void dataStdErrorException() {
        //default case
        PriceRequest priceRequest = new PriceRequest("myer", Arrays.asList("classic", "std", "premium"));
        Discount discount = new Discount("myer", 0.0, 150.50, 0.0);
        Deal deal = new Deal("myer", "1:1", "3:2", "1:1");
        Exception exception = assertThrows(UserServiceExceptions.class, () -> {
            new PriceCalculatorImpl().calculate(products, priceRequest, discount, deal);
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(ErrorMessages.CONFLICT_OFFERS.getErrorMessage()));
    }

    @DisplayName("Multiple classic customised price list for the same customer")
    @Test
    void dataClassicErrorException() {
        //default case
        PriceRequest priceRequest = new PriceRequest("myer", Arrays.asList("classic", "std", "premium"));
        Discount discount = new Discount("myer", 25.0, 0.0, 0.0);
        Deal deal = new Deal("myer", "2:1", "1:1", "1:1");
        Exception exception = assertThrows(UserServiceExceptions.class, () -> {
            new PriceCalculatorImpl().calculate(products, priceRequest, discount, deal);
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(ErrorMessages.CONFLICT_OFFERS.getErrorMessage()));
    }

    @DisplayName("Multiple premium customised price list for the same customer")
    @Test
    void dataErrorException() {
        //default case
        PriceRequest priceRequest = new PriceRequest("myer", Arrays.asList("classic", "std", "premium"));
        Discount discount = new Discount("myer", 0.0, 0.0, 10.0);
        Deal deal = new Deal("myer", "1:1", "1:1", "2:1");
        Exception exception = assertThrows(UserServiceExceptions.class, () -> {
            new PriceCalculatorImpl().calculate(products, priceRequest, discount, deal);
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(ErrorMessages.CONFLICT_OFFERS.getErrorMessage()));
    }



}