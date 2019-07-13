package com.onboard.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onboard.demo.error.ResourceNotFoundException;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "resources")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Resource implements AutoCloseable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    private String active;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Instant created = Instant.now();

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant modified = Instant.now();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "phone_id", referencedColumnName = "id")
    private Set<Phone> phones;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "emails_id", referencedColumnName = "id")
    private Set<Email> emails;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private Type type;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Override
    public void close() throws ResourceNotFoundException {
        System.out.println("Resource is not found");
    }
}
