package com.demo.io.repositories;

import com.demo.io.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountsRepository extends JpaRepository<Discount, Long>{

    Discount findByCompany(String company);

}

