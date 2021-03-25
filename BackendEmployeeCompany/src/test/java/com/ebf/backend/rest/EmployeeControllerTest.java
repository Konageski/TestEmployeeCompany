package com.ebf.backend.rest;

import com.ebf.backend.db.entity.EmployeeEntity;
import com.ebf.backend.db.repository.CompanyRepository;
import com.ebf.backend.db.repository.EmployeeRepository;
import com.ebf.backend.rest.DTO.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Value("${api.version}")
    private String API_VERSION;
    private final String MODULE_NAME = "employees";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testInsertOK() throws Exception {
        EmployeeDTO employeeDTO = this.createEntity(createDTO());
        EmployeeEntity employeeEntity = this.employeeRepository.findById(employeeDTO.getId()).get();

        assertThat(employeeDTO.getId()).isEqualTo(employeeEntity.getId());
        assertThat(employeeDTO.getName()).isEqualTo(employeeEntity.getName());
        assertThat(employeeDTO.getSurname()).isEqualTo(employeeEntity.getSurname());
        assertThat(employeeDTO.getEmail()).isEqualTo(employeeEntity.getEmail());
        assertThat(employeeDTO.getAddress()).isEqualTo(employeeEntity.getAddress());
        assertThat(employeeDTO.getSalary()).isEqualTo(employeeEntity.getSalary());
    }

    @Test
    public void testInsertError() throws Exception {
        EmployeeDTO dto = createDTO();
        dto.setName("");
        testInsertError(dto, "Employee Name is required!");

        dto = createDTO();
        dto.setSurname("");
        testInsertError(dto, "Employee Surname is required!");

        dto = createDTO();
        dto.setEmail("");
        testInsertError(dto, "Employee Email is required!");

        dto = createDTO();
        dto.setAddress("");
        testInsertError(dto, "Employee Address is required!");

        dto = createDTO();
        dto.setSalary(-1.0f);
        testInsertError(dto, "Employee Salary is required!");

        dto = createDTO();
        dto.setCompany(null);
        testInsertError(dto, "Employee Company is required!");
    }

    @Test
    public void testUpdateOK() throws Exception {
        EmployeeDTO dto = this.createEntity(createDTO());
        dto.setName("New Name");
        dto.setSurname("New Surname");
        dto.setEmail("New Email");
        dto.setAddress("New Address");
        dto.setSalary(98765.4f);
        dto.setCompany(new CompanyDTO(2L, "Pepsi", new HashSet<>()));

        String json = new ObjectMapper().writeValueAsString(new EmployeeWrapperDTO(dto));
        MvcResult mvcResult = this.mockMvc.perform(put("/api/" + this.API_VERSION + "/" + this.MODULE_NAME +"/1").content(json)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andReturn();

        ObjectMapper objectMapper =  new ObjectMapper().configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        dto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), EmployeeDTO.class);
        EmployeeEntity entity = this.employeeRepository.findById(dto.getId()).get();

        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getName()).isEqualTo(entity.getName());
        assertThat(dto.getSurname()).isEqualTo(entity.getSurname());
        assertThat(dto.getEmail()).isEqualTo(entity.getEmail());
        assertThat(dto.getAddress()).isEqualTo(entity.getAddress());
        assertThat(dto.getSalary()).isEqualTo(entity.getSalary());
        assertThat(dto.getCompany().getId()).isEqualTo(entity.getCompanyEntity().getId());
    }

    @Test
    public void testUpdateError() throws Exception {
        EmployeeDTO dto = createDTO();
        dto.setId(-1L);
        testUpdateError(dto, "Employee Id is required!");

        dto = createDTO();
        dto.setName("");
        testUpdateError(dto, "Employee Name is required!");

        dto = createDTO();
        dto.setSurname("");
        testUpdateError(dto, "Employee Surname is required!");

        dto = createDTO();
        dto.setEmail("");
        testUpdateError(dto, "Employee Email is required!");

        dto = createDTO();
        dto.setAddress("");
        testUpdateError(dto, "Employee Address is required!");

        dto = createDTO();
        dto.setSalary(-1f);
        testUpdateError(dto, "Employee Salary is required!");

        dto = createDTO();
        dto.setCompany(null);
        testUpdateError(dto, "Employee Company is required!");
    }

    @Test
    public void testDeleteOK() throws Exception {
        EmployeeDTO dto = this.createEntity(createDTO());

        this.mockMvc.perform(delete("/api/" + this.API_VERSION + "/" + this.MODULE_NAME +"/" + dto.getId())).andDo(print()).andExpect(status().isOk());
        assertThat(this.companyRepository.findById(dto.getId()).isPresent()).isFalse();
    }

    @Test
    public void testDeleteError() throws Exception {
        EmployeeDTO dto = this.createEntity(createDTO());

        this.mockMvc.perform(delete("/api/" + this.API_VERSION + "/" + this.MODULE_NAME +"/" + dto.getId())).andDo(print()).andExpect(status().isOk());
        assertThat(this.companyRepository.findById(dto.getId()).isPresent()).isFalse();

        try {
            this.mockMvc.perform(delete("/api/" + this.API_VERSION + "/" + this.MODULE_NAME +"/")).andDo(print());
        } catch (Exception ex) {
            assertThat(ex.getCause().getMessage()).isEqualTo("Company Id is required!");
        }
    }

    @Test
    public void testGetById() throws Exception {
        EmployeeDTO dto = this.createEntity(createDTO());
        MvcResult mvcResult = this.mockMvc.perform(get("/api/" + this.API_VERSION + "/" + this.MODULE_NAME + "/" + dto.getId()))
                .andDo(print()).andExpect(status().isOk()).andReturn();

        ObjectMapper objectMapper =  new ObjectMapper().configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        EmployeeDTO dtoReturn = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), EmployeeDTO.class);

        assertThat(dto.getId()).isEqualTo(dtoReturn.getId());
        assertThat(dto.getName()).isEqualTo(dtoReturn.getName());
        assertThat(dto.getSurname()).isEqualTo(dtoReturn.getSurname());
        assertThat(dto.getEmail()).isEqualTo(dtoReturn.getEmail());
        assertThat(dto.getAddress()).isEqualTo(dtoReturn.getAddress());
        assertThat(dto.getSalary()).isEqualTo(dtoReturn.getSalary());
        assertThat(dto.getCompany().getId()).isEqualTo(dtoReturn.getCompany().getId());
    }

    @Test
    public void testGetByIdError() throws Exception {
        try {
            this.mockMvc.perform(get("/api/" + this.API_VERSION + "/" + this.MODULE_NAME + "/99999999")).andDo(print());
        } catch (Exception ex) {
            assertThat(ex.getCause().getMessage()).isEqualTo("Could not find Employee   Id: 99999999");
        }
    }

    @Test
    public void testGetAll() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/" + this.API_VERSION + "/" + this.MODULE_NAME +"/")).andDo(print()).andExpect(status().isOk()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString().isEmpty()).isFalse();
    }

    private EmployeeDTO createDTO() {
        CompanyDTO companyDTO = new CompanyDTO(1L, "Coca-Cola", new HashSet<>());
        return new EmployeeDTO(null, "Test", "Employee", "test@test.com", "Adr 1", 123456.78f, companyDTO);
    }

    private EmployeeDTO createEntity(EmployeeDTO dto) throws Exception {
        String json = new ObjectMapper().writeValueAsString(new EmployeeWrapperDTO(dto));
        MvcResult mvcResult = this.mockMvc.perform(post("/api/" + this.API_VERSION + "/" + this.MODULE_NAME +"/").content(json)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andReturn();
        ObjectMapper objectMapper =  new ObjectMapper().configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), EmployeeDTO.class);
    }

    private void testInsertError(EmployeeDTO dto, String message) throws JsonProcessingException {
        String json = new ObjectMapper().writeValueAsString(new EmployeeWrapperDTO(dto));
        try {
            this.mockMvc.perform(post("/api/" + this.API_VERSION + "/" + this.MODULE_NAME +"/").content(json).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        } catch (Exception ex) {
            assertThat(ex.getCause().getMessage()).isEqualTo(message);
        }
    }

    private void testUpdateError(EmployeeDTO dto, String message) throws JsonProcessingException {
        String json = new ObjectMapper().writeValueAsString(new EmployeeWrapperDTO(dto));
        try {
            this.mockMvc.perform(put("/api/" + this.API_VERSION + "/" + this.MODULE_NAME +"/" + dto.getId()).content(json).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        } catch (Exception ex) {
            assertThat(ex.getCause().getMessage()).isEqualTo(message);
        }
    }


}