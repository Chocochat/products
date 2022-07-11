package com.demo.controller;

import com.demo.exception.ErrorMessages;
import com.demo.exception.UserServiceExceptions;
import com.demo.io.entity.Deal;
import com.demo.io.repositories.DealsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("deals")
public class DealsController {
    @Autowired
    private DealsRepository dealsRepository;

    @GetMapping
    public List<Deal> retrieveAllDeals() {
        return dealsRepository.findAll();
    }

    @GetMapping("{company}")
    public Deal retrieve(@PathVariable String company) {
        Optional<Deal> data = Optional.ofNullable(dealsRepository.findByCompany(company));

        if (!data.isPresent())
            throw new UserServiceExceptions(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        return data.get();
    }

    @DeleteMapping("{company}")
    public void deleteDeal(@PathVariable String company) {
        Deal deal = dealsRepository.findByCompany(company);
        Optional<Deal> data = Optional.ofNullable(deal);
        if (!data.isPresent())
            throw new UserServiceExceptions(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        dealsRepository.delete(deal);
    }

    @PostMapping
    public ResponseEntity<Object> createDeal(@RequestBody Deal deal) {
        Deal saveDeal = dealsRepository.save(deal);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{company}")
                .buildAndExpand(saveDeal.getCompany()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("{company}")
    public ResponseEntity<Object> updateDeal(@RequestBody Deal deal, @PathVariable String company) {
        Deal dealRetrived = dealsRepository.findByCompany(company);
        Optional<Deal> data = Optional.ofNullable(dealRetrived);

        if (!data.isPresent())
            throw new UserServiceExceptions(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        deal.setCompany(company);

        dealsRepository.save(deal);

        return ResponseEntity.noContent().build();
    }

}
