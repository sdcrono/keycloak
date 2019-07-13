package com.onboard.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
@Data
public class Address {

    @Id
    @GeneratedValue
    private Long id;

    private String street;

    private String unitNumber;

    private String zipCode;

    private String city;

    private String state;

    private String country;

    @OneToOne(mappedBy = "address")
    @JsonIgnore
    private Resource resources;
}
