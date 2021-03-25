package com.ebf.backend.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity {

    @Id @GeneratedValue(generator = "employee_generator")
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String address;
    private Float salary;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity companyEntity;

    public EmployeeEntity(String name, String surname, String email, String address, Float salary, CompanyEntity companyEntity) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address = address;
        this.salary = salary;
        this.companyEntity = companyEntity;
    }

    @Override
    public String toString() {
        return "EmployeeEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                '}';
    }
}
