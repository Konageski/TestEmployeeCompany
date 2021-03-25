package com.ebf.backend.rest;

import com.ebf.backend.db.entity.CompanyEntity;
import com.ebf.backend.db.repository.CompanyRepository;
import com.ebf.backend.db.repository.EmployeeRepository;
import com.ebf.backend.rest.DTO.CompanyDTO;
import com.ebf.backend.rest.DTO.CompanyWrapperDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class CompanyControllerTest {

    @Value("${api.version}")
    private String API_VERSION;
    private final String MODULE_NAME = "companies";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testInsertOK() throws Exception {
        CompanyDTO companyDTO = this.createEntity(createDTO());
        CompanyEntity companyEntity = this.companyRepository.findById(companyDTO.getId()).get();

        assertThat(companyDTO.getId()).isEqualTo(companyEntity.getId());
        assertThat(companyDTO.getName()).isEqualTo(companyEntity.getName());
    }

    @Test
    public void testInsertError() throws Exception {
        CompanyDTO companyDTO = createDTO();
        companyDTO.setName("");
        testInsertError(companyDTO, "Company Name is required!");
    }

    @Test
    public void testUpdateOK() throws Exception {
        CompanyDTO companyDTO = this.createEntity(createDTO());
        companyDTO.setName("New Name");

        String json = new ObjectMapper().writeValueAsString(new CompanyWrapperDTO(companyDTO));
        MvcResult mvcResult = this.mockMvc.perform(put("/api/" + this.API_VERSION + "/" + this.MODULE_NAME +"/1").content(json)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andReturn();

        CompanyWrapperDTO companyWrapperDTO =  new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), CompanyWrapperDTO.class);
        companyDTO = companyWrapperDTO.getCompany();
        CompanyEntity companyEntity = this.companyRepository.findById(companyDTO.getId()).get();

        assertThat(companyDTO.getId()).isEqualTo(companyEntity.getId());
        assertThat(companyDTO.getName()).isEqualTo(companyEntity.getName());
    }

    @Test
    public void testUpdateError() throws Exception {
        CompanyDTO companyDTO = createDTO();
        companyDTO.setId(-1L);
        testUpdateError(companyDTO, "Company Id is required!");

        companyDTO = createDTO();
        companyDTO.setName("");
        testUpdateError(companyDTO, "Company Name is required!");
    }

    @Test
    public void testDeleteOK() throws Exception {
        CompanyDTO companyDTO = this.createEntity(createDTO());

        this.mockMvc.perform(delete("/api/" + this.API_VERSION + "/" + this.MODULE_NAME +"/" + companyDTO.getId())).andDo(print()).andExpect(status().isOk());
        assertThat(this.companyRepository.findById(companyDTO.getId()).isPresent()).isFalse();
    }

    @Test
    public void testDeleteError() throws Exception {
        CompanyDTO companyDTO = this.createEntity(createDTO());

        this.mockMvc.perform(delete("/api/" + this.API_VERSION + "/" + this.MODULE_NAME +"/" + companyDTO.getId())).andDo(print()).andExpect(status().isOk());
        assertThat(this.companyRepository.findById(companyDTO.getId()).isPresent()).isFalse();

        try {
            this.mockMvc.perform(delete("/api/" + this.API_VERSION + "/" + this.MODULE_NAME +"/")).andDo(print());
        } catch (Exception ex) {
            assertThat(ex.getCause().getMessage()).isEqualTo("Company Id is required!");
        }
    }

    @Test
    public void testGetById() throws Exception {
        CompanyDTO companyDTO = this.createEntity(createDTO());
        MvcResult mvcResult = this.mockMvc.perform(get("/api/" + this.API_VERSION + "/" + this.MODULE_NAME + "/" + companyDTO.getId()))
                .andDo(print()).andExpect(status().isOk()).andReturn();

        CompanyWrapperDTO companyWrapperDTO =  new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), CompanyWrapperDTO.class);
        CompanyDTO companyDTOService = companyWrapperDTO.getCompany();
        assertThat(companyDTO.getId()).isEqualTo(companyDTOService.getId());
        assertThat(companyDTO.getName()).isEqualTo(companyDTOService.getName());
    }

    @Test
    public void testGetByIdError() throws Exception {
        try {
            this.mockMvc.perform(get("/api/" + this.API_VERSION + "/" + this.MODULE_NAME + "/99999999")).andDo(print());
        } catch (Exception ex) {
            assertThat(ex.getCause().getMessage()).isEqualTo("Could not find Company   Id: 99999999");
        }
    }

    @Test
    public void testGetAll() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/" + this.API_VERSION + "/" + this.MODULE_NAME +"/")).andDo(print()).andExpect(status().isOk()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString().isEmpty()).isFalse();
    }

    private CompanyDTO createDTO() {
        return new CompanyDTO(null, "Test Corp", new HashSet<>());
    }

    private CompanyDTO createEntity(CompanyDTO dto) throws Exception {
        String json = new ObjectMapper().writeValueAsString(new CompanyWrapperDTO(dto));
        MvcResult mvcResult = this.mockMvc.perform(post("/api/" + this.API_VERSION + "/" + this.MODULE_NAME +"/").content(json)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andReturn();
        CompanyWrapperDTO companyWrapperDTO =  new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), CompanyWrapperDTO.class);
        return companyWrapperDTO.getCompany();
    }

    private void testInsertError(CompanyDTO companyDTO, String message) throws JsonProcessingException {
        String json = new ObjectMapper().writeValueAsString(new CompanyWrapperDTO(companyDTO));
        try {
            this.mockMvc.perform(post("/api/" + this.API_VERSION + "/" + this.MODULE_NAME +"/").content(json).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        } catch (Exception ex) {
            assertThat(ex.getCause().getMessage()).isEqualTo(message);
        }
    }

    private void testUpdateError(CompanyDTO companyDTO, String message) throws JsonProcessingException {
        String json = new ObjectMapper().writeValueAsString(new CompanyWrapperDTO(companyDTO));
        try {
            this.mockMvc.perform(put("/api/" + this.API_VERSION + "/" + this.MODULE_NAME +"/" + companyDTO.getId()).content(json).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        } catch (Exception ex) {
            assertThat(ex.getCause().getMessage()).isEqualTo(message);
        }
    }


}