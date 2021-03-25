package com.ebf.backend.rest.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Company Wrapper DTO", description = "Represents an Root Company DTO")
public class CompanyWrapperDTO {

    @ApiModelProperty(value = "Company")
    CompanyDTO company;
}
