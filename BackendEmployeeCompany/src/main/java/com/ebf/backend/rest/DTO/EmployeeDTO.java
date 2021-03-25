package com.ebf.backend.rest.DTO;

import com.ebf.backend.db.entity.EmployeeEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value="employee")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Employee DTO", description = "Represents an Employee entity")
public class EmployeeDTO {

    @ApiModelProperty(value = "Employee's Id")
    private Long id;
    @ApiModelProperty(value = "Employee's Name", required = true)
    private String name;
    @ApiModelProperty(value = "Employee's Surname", required = true)
    private String surname;
    @ApiModelProperty(value = "Employee's Email", required = true)
    private String email;
    @ApiModelProperty(value = "Employee's Address", required = true)
    private String address;
    @ApiModelProperty(value = "Employee's Salary", required = true)
    private Float salary;
    @ApiModelProperty(value = "Employee's Company", required = true)
    private CompanyDTO company;

    public EmployeeDTO(EmployeeEntity employeeEntity) {
        this.id = employeeEntity.getId();
        this.name = employeeEntity.getName();
        this.surname = employeeEntity.getSurname();
        this.email = employeeEntity.getEmail();
        this.address = employeeEntity.getAddress();
        this.salary = employeeEntity.getSalary();

        if (employeeEntity.getCompanyEntity() != null) {
            employeeEntity.getCompanyEntity().setEmployeeEntities(null);
            this.company = new CompanyDTO(employeeEntity.getCompanyEntity());
        }
    }

    public static EmployeeEntity convertEmployeeDtoToEntity(EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(employeeDTO.getId());
        employeeEntity.setName(employeeDTO.getName());
        employeeEntity.setSurname(employeeDTO.getSurname());
        employeeEntity.setEmail(employeeDTO.getEmail());
        employeeEntity.setAddress(employeeDTO.getAddress());
        employeeEntity.setSalary(employeeDTO.getSalary());

        if (employeeDTO.getCompany() != null) {
            employeeDTO.getCompany().setEmployees(null);
            employeeEntity.setCompanyEntity(CompanyDTO.convertCompanyDtoToEntity(employeeDTO.getCompany()));
        }

        return employeeEntity;
    }

    public static Set<EmployeeDTO> convertEmployeesEntityToDTO(Set<EmployeeEntity> employeeEntities) {
        if (employeeEntities == null)
            return null;

        Set<EmployeeDTO> employeeDTOs = new HashSet<>();
        employeeEntities.forEach(employeeEntity -> employeeDTOs.add(new EmployeeDTO(employeeEntity)));
        return employeeDTOs;
    }

    public static Set<EmployeeEntity> convertEmployeesDtoToEntity(Set<EmployeeDTO> employeeDTOs) {
        if (employeeDTOs == null)
            return null;

        Set<EmployeeEntity> employeeEntities = new HashSet<>();
        employeeDTOs.forEach(employeeDTO -> employeeEntities.add(convertEmployeeDtoToEntity(employeeDTO)));
        return employeeEntities;
    }
}
