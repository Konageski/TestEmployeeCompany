package com.ebf.backend.rest.DTO;

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
@ApiModel(value = "Report Average Salary By Company DTO", description = "Represents an Report Average Salary By Company")
public class ReportAverageSalaryByCompanyDTO {

    @ApiModelProperty(value = "Company's Id")
    private Long id;
    @ApiModelProperty(value = "Company's Name", required = true)
    private String name;
    @ApiModelProperty(value = "Company's Employees", required = true)
    private Float averageSalary;

    public ReportAverageSalaryByCompanyDTO(CompanyEntity companyEntity) {
        this.id = companyEntity.getId();
        this.name = companyEntity.getName();
        this.averageSalary = 0f;
    }

    public static CompanyEntity convertCompanyDtoToEntity(ReportAverageSalaryByCompanyDTO companyDTO) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(companyDTO.getId());
        companyEntity.setName(companyDTO.getName());

        return companyEntity;
    }

    public static Set<ReportAverageSalaryByCompanyDTO> convertCompaniesEntityToDTO(List<CompanyEntity> companyEntities) {
        Set<ReportAverageSalaryByCompanyDTO> companyDTOs = new HashSet<>();
        companyEntities.forEach(companyEntity -> companyDTOs.add(new ReportAverageSalaryByCompanyDTO(companyEntity)));
        return companyDTOs;
    }
}