package com.demo.controller;

import com.demo.exception.ErrorMessages;
import com.demo.exception.UserServiceExceptions;
import com.demo.io.entity.Discount;
import com.demo.io.repositories.DiscountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("discounts")
public class DiscountsController {

    @Autowired
    private DiscountsRepository discountsRepository;

    @GetMapping
    public List<Discount> retrieveAllDiscounts() {
        return discountsRepository.findAll();
    }

    @GetMapping("{name}")
    public Discount retrieve(@PathVariable String name) {
        Optional<Discount> data = Optional.ofNullable(discountsRepository.findByCompany(name));

        if (!data.isPresent())
            throw new UserServiceExceptions("data not found");

        return data.get();
    }

    @DeleteMapping("{name}")
    public void deleteDiscount(@PathVariable String name) {
        Discount discount = discountsRepository.findByCompany(name);
        discountsRepository.delete(discount);
    }

    @PostMapping
    public ResponseEntity<Object> createDiscount(@RequestBody Discount discount) {
        Discount saveDiscount = discountsRepository.save(discount);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{company}")
                .buildAndExpand(saveDiscount.getCompany()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("{company}")
    public ResponseEntity<Object> updateDiscount(@RequestBody Discount discount, @PathVariable String company) {
        Discount discountRetrived = discountsRepository.findByCompany(company);
        Optional<Discount> data = Optional.ofNullable(discountRetrived);

        if (!data.isPresent())
            throw new UserServiceExceptions(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        discount.setCompany(company);

        discountsRepository.save(discount);

        return ResponseEntity.noContent().build();
    }

}
