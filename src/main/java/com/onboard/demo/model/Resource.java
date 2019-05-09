package com.onboard.demo.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Set;

@Entity
@Data
public class Resource {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    private String user;

    @NotNull
    private String category;

    private String type;

    private String active;

    private Set<String> phone;

    @Email
    private Set<String> email;

    private Address address;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Instant created = Instant.now();

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant modified = Instant.now();
}
