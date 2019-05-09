package com.onboard.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Address {

    @Id
    @GeneratedValue
    private Long id;

    private String Street;

    private String unitNumber;

    private String zipCode;

    private String city;

    private String state;

    private String Country;
}
