package com.ebf.backend.rest;

import com.ebf.backend.db.entity.CompanyEntity;
import com.ebf.backend.db.entity.EmployeeEntity;
import com.ebf.backend.db.repository.CompanyRepository;
import com.ebf.backend.rest.DTO.ReportAverageSalaryByCompaniesWrapperDTO;
import com.ebf.backend.rest.DTO.ReportAverageSalaryByCompanyDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportAverageSalaryByCompanyControllerTest {

    @Value("${api.version}")
    private String API_VERSION;
    private final String MODULE_NAME = "reportAverageSalaryByCompanies";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void testGetAll() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/" + this.API_VERSION + "/" + this.MODULE_NAME +"/"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        ReportAverageSalaryByCompaniesWrapperDTO repDTOs = new ObjectMapper()
                .readValue(mvcResult.getResponse().getContentAsString(), ReportAverageSalaryByCompaniesWrapperDTO.class);

        for (ReportAverageSalaryByCompanyDTO repDTO : repDTOs.getReportAverageSalaryByCompany()) {
            CompanyEntity companyEntity = this.companyRepository.findById(repDTO.getId()).get();
            Float averageSalary = 0f;
            if (companyEntity.getEmployeeEntities() != null && companyEntity.getEmployeeEntities().size() > 0) {
                for (EmployeeEntity employeeEntity : companyEntity.getEmployeeEntities()) {
                    averageSalary += employeeEntity.getSalary();
                }
                averageSalary /= companyEntity.getEmployeeEntities().size();
            }
            assertThat(repDTO.getAverageSalary()).isEqualTo(averageSalary);
        }
    }

}