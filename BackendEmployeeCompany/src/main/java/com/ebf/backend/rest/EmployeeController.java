package com.ebf.backend.rest;

import com.ebf.backend.db.entity.CompanyEntity;
import com.ebf.backend.db.entity.EmployeeEntity;
import com.ebf.backend.db.repository.CompanyRepository;
import com.ebf.backend.db.repository.EmployeeRepository;
import com.ebf.backend.rest.exception.RestNotFoundException;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Api(tags = "Company Rest Service")
@RestController
@RequestMapping("/api/${api.version}/company")
public class CompanyController {

    private final CompanyRepository repository;
    private final EmployeeRepository employeeRepository;

    CompanyController(CompanyRepository repository, EmployeeRepository employeeRepository) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
    }

    @ApiOperation(value = "Create company")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Created company"),
                    @ApiResponse(code = 500, message = "Return exception")
            })
    @PostMapping()
    CompanyDTO save(@RequestBody CompanyDTO newDTO) {
        CompanyEntity companyEntity = CompanyDTO.convertCompanyDtoToEntity(newDTO);
        CompanyDTO returnDTO = new CompanyDTO(repository.save(companyEntity));
        if (newDTO.getEmployees() != null && newDTO.getEmployees().size() > 0) {
            returnDTO.setEmployees(new HashSet<>());
            newDTO.getEmployees().forEach(employeeDTO -> {
                EmployeeEntity employeeEntity = CompanyDTO.EmployeeDTO.convertEmployeeDtoToEntity(employeeDTO);
                employeeEntity.setCompanyEntity(companyEntity);
                returnDTO.getEmployees().add(new CompanyDTO.EmployeeDTO(employeeRepository.save(employeeEntity)));
            });
        }
        return returnDTO;
    }

    @ApiOperation(value = "Update company")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Updated company"),
                    @ApiResponse(code = 500, message = "Return exception")
            })
    @PutMapping("/{id}")
    CompanyDTO update(@RequestBody CompanyDTO updateDTO, @PathVariable Long id) {
        return new CompanyDTO(repository.save(CompanyDTO.convertCompanyDtoToEntity(updateDTO)));
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
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
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
    CompanyDTO findById(@PathVariable Long id) {
        return new CompanyDTO(repository.findById(id).orElseThrow(() -> new RestNotFoundException("Company", id)));
    }

    @ApiOperation(value = "Getting all companies")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Getting all companies"),
                    @ApiResponse(code = 500, message = "Return exception")
            })
    @GetMapping()
    Set<CompanyDTO> findAll() {
        return CompanyDTO.convertCompaniesEntityToDTO(repository.findAll());
    }
}
