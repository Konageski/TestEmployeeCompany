package com.ebf.backend.rest.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Report Average Salary By Company DTO", description = "Represents an Root Report Average Salary By Company List DTO")
public class ReportAverageSalaryByCompaniesWrapperDTO {

    @ApiModelProperty(value = "Report Average Salary By Company List")
    Set<ReportAverageSalaryByCompanyDTO> reportAverageSalaryByCompany;
}
