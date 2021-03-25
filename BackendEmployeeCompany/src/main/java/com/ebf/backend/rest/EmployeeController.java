package com.ebf.backend.rest;

import com.ebf.backend.db.entity.EmployeeEntity;
import com.ebf.backend.db.repository.EmployeeRepository;
import com.ebf.backend.rest.DTO.EmployeeDTO;
import com.ebf.backend.rest.DTO.EmployeeWrapperDTO;
import com.ebf.backend.rest.DTO.EmployeesWrapperDTO;
import com.ebf.backend.rest.exception.RestException;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@Api(tags = "Employee Rest Service")
@RestController
@RequestMapping("/api/${api.version}/employees")
public class EmployeeController {

    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @ApiOperation(value = "Create Employee")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Created Employee"),
                    @ApiResponse(code = 500, message = "Return exception")
            })
    @PostMapping()
    EmployeeWrapperDTO insert(@RequestBody EmployeeWrapperDTO dto) {
        this.validationInsertUpdate(dto.getEmployee());

        EmployeeEntity employeeEntity = EmployeeDTO.convertEmployeeDtoToEntity(dto.getEmployee());
        return new EmployeeWrapperDTO(new EmployeeDTO(repository.save(employeeEntity)));
    }

    @ApiOperation(value = "Update Employee")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Updated Employee"),
                    @ApiResponse(code = 500, message = "Return exception")
            })
    @PutMapping("/{id}")
    EmployeeWrapperDTO update(@RequestBody EmployeeWrapperDTO dto, @PathVariable Long id) {
        this.validationPK(id);
        this.validationInsertUpdate(dto.getEmployee());
        dto.getEmployee().setId(id);

        return new EmployeeWrapperDTO(new EmployeeDTO(repository.save(EmployeeDTO.convertEmployeeDtoToEntity(dto.getEmployee()))));
    }

    @ApiOperation(value = "Delete Employee by id")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Deleted Employee"),
                    @ApiResponse(code = 500, message = "Return exception")
            })
    @ApiImplicitParams(
            value = {
                    @ApiImplicitParam(
                            name = "id",
                            required = true,
                            dataType = "long",
                            paramType = "path",
                            value = "Employee's id")
            })
    @DeleteMapping("/{id}")
    EmployeesWrapperDTO delete(@PathVariable Long id) {
        this.validationPK(id);

        repository.deleteById(id);
        return new EmployeesWrapperDTO();
    }

    @ApiOperation(value = "Get Employee by id")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Get Employee"),
                    @ApiResponse(code = 500, message = "Return exception")
            })
    @ApiImplicitParams(
            value = {
                    @ApiImplicitParam(
                            name = "id",
                            required = true,
                            dataType = "long",
                            paramType = "path",
                            value = "Employee's id")
            })
    @GetMapping("/{id}")
    EmployeeWrapperDTO findById(@PathVariable Long id) {
        this.validationPK(id);

        return new EmployeeWrapperDTO(new EmployeeDTO(repository.findById(id).orElseThrow(() -> new RestException("Could not find Employee   Id: " + id))));
    }

    @ApiOperation(value = "Getting all Employees")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Getting all Employee"),
                    @ApiResponse(code = 500, message = "Return exception")
            })
    @GetMapping()
    EmployeesWrapperDTO findAll() {
        return new EmployeesWrapperDTO(EmployeeDTO.convertEmployeesEntityToDTO(new HashSet<>(repository.findAll())));
    }

    private void validationInsertUpdate(EmployeeDTO dto) {
        if (dto.getName() == null || dto.getName().isEmpty() || dto.getName().isBlank())
            throw new RestException("Employee Name is required!");
        if (dto.getSurname() == null || dto.getSurname().isEmpty() || dto.getSurname().isBlank())
            throw new RestException("Employee Surname is required!");
        if (dto.getEmail() == null || dto.getEmail().isEmpty() || dto.getEmail().isBlank())
            throw new RestException("Employee Email is required!");
        if (dto.getAddress() == null || dto.getAddress().isEmpty() || dto.getAddress().isBlank())
            throw new RestException("Employee Address is required!");
        if (dto.getSalary() == null || dto.getSalary() <= 0)
            throw new RestException("Employee Salary is required!");
        if (dto.getCompany() == null || dto.getCompany().getId() == null || dto.getCompany().getId() <= 0)
            throw new RestException("Employee Company is required!");
    }

    private void validationPK(Long id) {
        if (id == null || id <= 0)
            throw new RestException("Employee Id is required!");
    }

}
