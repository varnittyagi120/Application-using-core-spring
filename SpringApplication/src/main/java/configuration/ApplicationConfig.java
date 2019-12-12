package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import dto.Client;
import dto.Employee;
import service.CMSManagement;
import service.CRUDContainer;
import service.EMSManagement;

@Configuration
@ComponentScan(basePackages = { "dto", "service", "dao" })
public class ApplicationConfig {
	@Bean
	@Scope(value = "prototype")
	public Client getClientObject() {
		return new Client();
	}

	@Bean
	public Employee getEmployeeObject() {
		return new Employee();
	}
//	@Bean
//	public CRUDContainer getemsManagementObject() {
//		return new EMSManagement();
//	}
//	@Bean
//	public CRUDContainer getcmsManagementObject() {
//		return new CMSManagement();
//	}
}
