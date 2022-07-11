package com.demo.io.repositories;

import com.demo.io.entity.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealsRepository extends JpaRepository<Deal, Long>{

    Deal findByCompany(String company);

}

