package com.ebf.backend.rest;

import com.ebf.backend.db.entity.CompanyEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Company DTO", description = "Represents an Company entity")
public class CompanyDTO {

    @ApiModelProperty(value = "Company's Id")
    private Long id;
    @ApiModelProperty(value = "Company's Name", required = true)
    private String name;
    @ApiModelProperty(value = "Company's Employees", required = true)
    private Set<EmployeeDTO> employees;

    public CompanyDTO(CompanyEntity companyEntity) {
        this.id = companyEntity.getId();
        this.name = companyEntity.getName();
        if (companyEntity.getEmployeeEntities() != null && !companyEntity.getEmployeeEntities().isEmpty()) {
            companyEntity.getEmployeeEntities().forEach(employeeEntity -> employeeEntity.setCompanyEntity(null));
            this.employees = EmployeeDTO.convertEmployeesEntityToDTO(companyEntity.getEmployeeEntities());
        }
    }

    public static CompanyEntity convertCompanyDtoToEntity(CompanyDTO companyDTO) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(companyDTO.getId());
        companyEntity.setName(companyDTO.getName());

        if (companyDTO.getEmployees() != null && !companyDTO.getEmployees().isEmpty()) {
            companyDTO.getEmployees().forEach(employeeDTO -> employeeDTO.setCompanyDTO(null));
            companyEntity.setEmployeeEntities(EmployeeDTO.convertEmployeesDtoToEntity(companyDTO.getEmployees()));
        }
        return companyEntity;
    }

    public static Set<CompanyDTO> convertCompaniesEntityToDTO(List<CompanyEntity> companyEntities) {
        Set<CompanyDTO> companyDTOs = new HashSet<>();
        companyEntities.forEach(companyEntity -> companyDTOs.add(new CompanyDTO(companyEntity)));
        return companyDTOs;
    }
}