package com.demo.io.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Deal {

    @Id
    @Column(nullable = false)
    private String company;

    @Column
    private String classic;

    @Column
    private String std;

    @Column
    private String premium;

    public Deal(String company, String classic, String std, String premium) {
        this.company = company;
        this.classic = classic;
        this.std = std;
        this.premium = premium;
    }
}
