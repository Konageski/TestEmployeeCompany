package com.ebf.backend.rest;

import com.ebf.backend.db.entity.CompanyEntity;
import com.ebf.backend.db.repository.CompanyRepository;
import com.ebf.backend.rest.DTO.ReportAverageSalaryByCompaniesWrapperDTO;
import com.ebf.backend.rest.DTO.ReportAverageSalaryByCompanyDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Api(tags = "Report Average Salary By Company Rest Service")
@RestController
@RequestMapping("/api/${api.version}/reportAverageSalaryByCompanies")
public class ReportAverageSalaryByCompanyController {

    private final CompanyRepository repository;

    public ReportAverageSalaryByCompanyController(CompanyRepository repository) {
        this.repository = repository;
    }

    @ApiOperation(value = "Getting Average Salary By Company")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Getting Average Salary By Company"),
                    @ApiResponse(code = 500, message = "Return exception")
            })
    @GetMapping()
    ReportAverageSalaryByCompaniesWrapperDTO getReport() {
        List<CompanyEntity> companyEntities = repository.findAll();

        Set<ReportAverageSalaryByCompanyDTO> returnDTO = new HashSet<>();
        companyEntities.forEach(companyEntity -> {
            ReportAverageSalaryByCompanyDTO repDTO = new ReportAverageSalaryByCompanyDTO(companyEntity);
            if (companyEntity.getEmployeeEntities() != null && companyEntity.getEmployeeEntities().size() > 0) {
                companyEntity.getEmployeeEntities().forEach(employeeEntity -> repDTO.setAverageSalary(repDTO.getAverageSalary() + employeeEntity.getSalary()));
                repDTO.setAverageSalary(repDTO.getAverageSalary() / companyEntity.getEmployeeEntities().size());
            }
            returnDTO.add(repDTO);
        });

        return new ReportAverageSalaryByCompaniesWrapperDTO(returnDTO);
    }

}
