package com.demo.io.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Discount {

    @Id
    @Column(nullable = false)
    private String company;

    @Column
    private double classic;

    @Column
    private double std;

    @Column
    private double premium;

    public Discount(String company, double classic, double std, double premium) {
        this.company = company;
        this.classic = classic;
        this.std = std;
        this.premium = premium;
    }
}
