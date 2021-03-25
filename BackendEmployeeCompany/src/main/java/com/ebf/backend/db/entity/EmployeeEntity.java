package com.ebf.backend.db.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Employee {

    @Id @GeneratedValue(generator = "employee_generator")
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String address;
    private Float salary;

    @ManyToOne()
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity companyEntity;

    public Employee(String name, String surname, String email, String address, Float salary, CompanyEntity companyEntity) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address = address;
        this.salary = salary;
        this.companyEntity = companyEntity;
    }
}
