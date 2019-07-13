package com.onboard.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "types")
@Data
public class Type {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "type")
    @JsonIgnore
    private Set<Resource> resources;
}
