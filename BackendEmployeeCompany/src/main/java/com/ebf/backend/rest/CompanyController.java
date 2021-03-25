package com.ebf.backend.rest;

import com.ebf.backend.db.entity.CompanyEntity;
import com.ebf.backend.db.repository.CompanyRepository;
import com.ebf.backend.rest.DTO.CompaniesWrapperDTO;
import com.ebf.backend.rest.DTO.CompanyDTO;
import com.ebf.backend.rest.DTO.CompanyWrapperDTO;
import com.ebf.backend.rest.exception.RestException;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Company Rest Service")
@RestController
@RequestMapping("/api/${api.version}/companies")
public class CompanyController {

    private final CompanyRepository repository;

    CompanyController(CompanyRepository repository) {
        this.repository = repository;
    }

    @ApiOperation(value = "Create company")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Created company"),
                    @ApiResponse(code = 500, message = "Return exception")
            })
    @PostMapping()
    CompanyWrapperDTO insert(@RequestBody CompanyWrapperDTO dto) {
        this.validationInsertUpdate(dto.getCompany());

        CompanyEntity companyEntity = CompanyDTO.convertCompanyDtoToEntity(dto.getCompany());
        return new CompanyWrapperDTO(new CompanyDTO(repository.save(companyEntity)));
    }

    @ApiOperation(value = "Update company")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Updated company"),
                    @ApiResponse(code = 500, message = "Return exception")
            })
    @PutMapping("/{id}")
    CompanyWrapperDTO update(@RequestBody CompanyWrapperDTO updateDTO, @PathVariable Long id) {
        this.validationInsertUpdate(updateDTO.getCompany());
        this.validationPK(id);
        updateDTO.getCompany().setId(id);

        return new CompanyWrapperDTO(new CompanyDTO(repository.save(CompanyDTO.convertCompanyDtoToEntity(updateDTO.getCompany()))));
    }

    @ApiOperation(value = "Delete company by id")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Deleted company"),
                    @ApiResponse(code = 500, message = "Return exception")
            })
    @ApiImplicitParams(
            value = {
                    @ApiImplicitParam(
                            name = "id",
                            required = true,
                            dataType = "long",
                            paramType = "path",
                            value = "Company's id")
            })
    @DeleteMapping("/{id}")
    CompanyWrapperDTO delete(@PathVariable Long id) {
        this.validationPK(id);

        repository.deleteById(id);
        return new CompanyWrapperDTO();
    }

    @ApiOperation(value = "Get company by id")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Get company"),
                    @ApiResponse(code = 500, message = "Return exception")
            })
    @ApiImplicitParams(
            value = {
                    @ApiImplicitParam(
                            name = "id",
                            required = true,
                            dataType = "long",
                            paramType = "path",
                            value = "Company's id")
            })
    @GetMapping("/{id}")
    CompanyWrapperDTO findById(@PathVariable Long id) {
        this.validationPK(id);

        return new CompanyWrapperDTO(new CompanyDTO(repository.findById(id).orElseThrow(() -> new RestException("Could not find Company   Id: " + id))));
    }

    @ApiOperation(value = "Getting all companies")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Getting all companies"),
                    @ApiResponse(code = 500, message = "Return exception")
            })
    @GetMapping()
    CompaniesWrapperDTO findAll() {
        return new CompaniesWrapperDTO(CompanyDTO.convertCompaniesEntityToDTO(repository.findAll()));
    }

    private void validationInsertUpdate(CompanyDTO newDTO) {
        if (newDTO.getName() == null || newDTO.getName().isEmpty() || newDTO.getName().isBlank())
            throw new RestException("Company Name is required!");
        if (newDTO.getEmployees() != null && newDTO.getEmployees().size() > 0) {
            newDTO.getEmployees().forEach(employeeDTO -> {
                if (employeeDTO.getName() == null || employeeDTO.getName().isEmpty() || employeeDTO.getName().isBlank())
                    throw new RestException("Employee Name is required!");
                if (employeeDTO.getSurname() == null || employeeDTO.getSurname().isEmpty() || employeeDTO.getSurname().isBlank())
                    throw new RestException("Employee Surname is required!");
                if (employeeDTO.getEmail() == null || employeeDTO.getEmail().isEmpty() || employeeDTO.getEmail().isBlank())
                    throw new RestException("Employee Email is required!");
                if (employeeDTO.getAddress() == null || employeeDTO.getAddress().isEmpty() || employeeDTO.getAddress().isBlank())
                    throw new RestException("Employee Address is required!");
                if (employeeDTO.getSalary() == null || employeeDTO.getSalary() <= 0)
                    throw new RestException("Employee Salary is required!");
            });
        }
    }

    private void validationPK(Long id) {
        if (id == null || id <= 0)
            throw new RestException("Company Id is required!");
    }
}
