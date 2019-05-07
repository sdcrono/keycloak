package com.onboard.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Resource {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String user;

    private String category;

    private String type;

    private String active;

    private String phone;

    private String email;
}
