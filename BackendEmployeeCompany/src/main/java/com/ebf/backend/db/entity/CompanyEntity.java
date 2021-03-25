package com.ebf.backend.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Company {

    @Id @GeneratedValue(generator = "company_generator")
    private Long id;
    private String name;
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private Set<Employee> employees;

    public Company(String name) {
        this.name = name;
    }
}
