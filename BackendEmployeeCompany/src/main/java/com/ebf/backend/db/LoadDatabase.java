package com.ebf.backend.db;

import com.ebf.backend.db.entity.CompanyEntity;
import com.ebf.backend.db.entity.EmployeeEntity;
import com.ebf.backend.db.repository.CompanyRepository;
import com.ebf.backend.db.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {

        return args -> {
            log.info("Create local database");
            CompanyEntity companyEntity1 = companyRepository.save(new CompanyEntity("Coca-Cola"));
            log.info("Created: " + companyEntity1);
            log.info("Created: " + employeeRepository.save(new EmployeeEntity("James", "Smith", "James.Smith@cocacola.com", "Av 1", 50000.0f, companyEntity1)));
            log.info("Created: " + employeeRepository.save(new EmployeeEntity("Mary", "Johnson", "Mary.Johnson@cocacola.com", "Av 2", 60000.0f, companyEntity1)));
            log.info("Created: " + employeeRepository.save(new EmployeeEntity("John", "Williams", "John.Williams@cocacola.com", "Av 3", 65000.0f, companyEntity1)));
            log.info("Created: " + employeeRepository.save(new EmployeeEntity("Patricia", "Brown", "Patricia.Brown@cocacola.com", "Av 4", 70000.0f, companyEntity1)));
            log.info("Created: " + employeeRepository.save(new EmployeeEntity("Robert", "Jones", "Robert.Jones@cocacola.com", "Av 5", 85000.0f, companyEntity1)));

            CompanyEntity companyEntity2 = companyRepository.save(new CompanyEntity("Pepsi"));
            log.info("Created: " + companyEntity2);
            log.info("Created: " + employeeRepository.save(new EmployeeEntity("Linda", "Miller", "Linda.Miller@pepsi.com", "Av 6", 60000.0f, companyEntity2)));
            log.info("Created: " + employeeRepository.save(new EmployeeEntity("Michael", "Davis", "Michael.Davis@pepsi.com", "Av 7", 75000.0f, companyEntity2)));
            log.info("Created: " + employeeRepository.save(new EmployeeEntity("Barbara", "Garcia", "Barbara.Garcia@pepsi.com", "Av 8", 85000.0f, companyEntity2)));
            log.info("Created: " + employeeRepository.save(new EmployeeEntity("William", "Rodriguez", "William.Rodriguez@pepsi.com", "Av 9", 90000.0f, companyEntity2)));
            log.info("Created: " + employeeRepository.save(new EmployeeEntity("Elizabeth", "Wilson", "Elizabeth.Wilson@pepsi.com", "Av 10", 110000.0f, companyEntity2)));

            CompanyEntity companyEntity3 = companyRepository.save(new CompanyEntity("Vilsa"));
            log.info("Created: " + companyEntity3);
            log.info("Created: " + employeeRepository.save(new EmployeeEntity("David", "Martinez", "David.Martinez@vilsa.com", "Av 11", 80000.0f, companyEntity3)));
            log.info("Created: " + employeeRepository.save(new EmployeeEntity("Jennifer", "Anderson", "Jennifer.Anderson@vilsa.com", "Av 12", 90000.0f, companyEntity3)));
            log.info("Created: " + employeeRepository.save(new EmployeeEntity("Richard", "Taylor", "Richard.Taylor@vilsa.com", "Av 13", 95000.0f, companyEntity3)));
            log.info("Created: " + employeeRepository.save(new EmployeeEntity("Maria", "Thomas", "Maria.Thomas@vilsa.com", "Av 14", 105000.0f, companyEntity3)));
            log.info("Created: " + employeeRepository.save(new EmployeeEntity("Charles", "Hernandez", "Charles.Hernandez@vilsa.com", "Av 15", 115000.0f, companyEntity3)));

        };
    }
}
