package com.onboard.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "resources")
@Data
public class Resource {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    private String active;

//    private Set<String> phone;
//
//    private Set<String> email;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Instant created = Instant.now();

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant modified = Instant.now();

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    @JsonIgnore
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
//    @JsonIgnore
    private Category category;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
//    @JsonIgnore
    private Type type;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
//    @JsonIgnore
    private Address address;
}
