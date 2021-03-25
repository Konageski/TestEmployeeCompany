package com.ebf.backend.rest.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Employees Wrapper DTO", description = "Represents an Root Employee List DTO")
public class EmployeesWrapperDTO {

    @ApiModelProperty(value = "Employee List")
    Set<EmployeeDTO> employee;
}
