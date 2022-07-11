package com.demo.controller;

import com.demo.exception.UserServiceExceptions;
import com.demo.io.entity.Product;
import com.demo.io.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("products")
public class ProductsController {

    @Autowired
    private ProductsRepository productsRepository;

    @GetMapping
    public List<Product> retrieveAllAdProducts() {
        return productsRepository.findAll();
    }

    @GetMapping("{name}")
    public Product retrieve(@PathVariable String name) {
        Optional<Product> data = Optional.ofNullable(productsRepository.findByName(name));

        if (!data.isPresent())
            throw new UserServiceExceptions("data not found");

        return data.get();
    }

}
