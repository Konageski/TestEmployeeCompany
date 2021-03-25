package com.ebf.backend.db.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity {

    @Id @GeneratedValue(generator = "company_generator")
    private Long id;
    private String name;
    @JsonManagedReference
    @OneToMany(mappedBy = "companyEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<EmployeeEntity> employeeEntities;

    public CompanyEntity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CompanyEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
