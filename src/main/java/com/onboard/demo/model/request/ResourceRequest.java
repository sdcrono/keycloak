package com.onboard.demo.model.request;

import com.onboard.demo.model.Address;
import lombok.Data;

import java.util.Set;

@Data
public class ResourceRequest {

    private Long id;

    private String name;

    private String user;

    private String category;

    private String type;

    private String active;

    private String phone;

    private Set<String> email;

    private Address address;
}
