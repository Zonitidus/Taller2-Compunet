package co.edu.icesi.dev.uccareapp.transport;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import co.edu.icesi.dev.uccareapp.transport.model.UserApp;
import co.edu.icesi.dev.uccareapp.transport.model.UserType;
import co.edu.icesi.dev.uccareapp.transport.model.person.Businessentity;
import co.edu.icesi.dev.uccareapp.transport.model.person.Countryregion;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Currencyrate;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salespersonquotahistory;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritoryhistory;
import co.edu.icesi.dev.uccareapp.transport.repository.BusinessEntittyRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.CountryRegionRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.UserRepository;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.CurrencyRateService;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesPersonServiceImp;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesTerritoryHistoryServiceImp;
import co.edu.icesi.dev.uccareapp.transport.service.interfaces.SalesPersonQuotaHistoryService;

@SpringBootApplication
public class TallerunoApplication {

	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TallerunoApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public CommandLineRunner dummy(UserRepository ur, BusinessEntittyRepository ber, 
			CountryRegionRepository countryRegionRepo, SalesTerritoryRepository salesTerritoryRepository,
			SalesPersonServiceImp salesPersonService, CurrencyRateService currencyRateService, SalesPersonQuotaHistoryService sphqs,
			SalesTerritoryHistoryServiceImp salesterritoryhistoryservice) {

		return (args) -> {
			UserApp u = new UserApp();
			u.setId(1);
			u.setType(UserType.admin);
			u.setUsername("admin");
			u.setPassword("{noop}123");
		
			/*ur.save(u);*/
			
			UserApp u2 = new UserApp();
			u2.setId(2);
			u2.setType(UserType.operator);
			u2.setUsername("operator");
			u2.setPassword("{noop}123");
		
			/*ur.save(u2);*/
			
			
			Businessentity be = new Businessentity();
			be.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
			
			Businessentity be2 = new Businessentity();
			be2.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(10)));
			
			Businessentity be3 = new Businessentity();
			be3.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(6)));
			
			Businessentity be4 = new Businessentity();
			be4.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(2)));
			
			/*ber.save(be);
			ber.save(be2);*/

			
			
			
			String regionCodeUK = "";
			String regionCodeUSA = "";
			
			Countryregion cruk = new Countryregion();
			cruk.setName("Inglaterra");
			Countryregion crusa = new Countryregion();
			crusa.setName("EstadosUnidos");

			/*countryRegionRepo.save(cruk);
			countryRegionRepo.save(crusa);*/
			
			Iterable<Countryregion> crs = countryRegionRepo.findAll();
			for (Countryregion country : crs) {
				
				if(country.getName() != null) {
					if (country.getName().equals("Inglaterra")) {
						regionCodeUK = country.getCountryregioncode();
					} else {
						regionCodeUSA = country.getCountryregioncode();
					}
				}
			}
			
			Salesterritory st = new Salesterritory();
			st.setTerritoryid(1);
			st.setName("London");
			st.setCountryregioncode(regionCodeUK);

			//salesTerritoryRepository.save(st);
			
			Salesterritory stEdit = new Salesterritory();
			stEdit.setTerritoryid(2);
			stEdit.setName("Boston");
			stEdit.setCountryregioncode(regionCodeUSA);
			
			//salesTerritoryRepository.save(stEdit);
			
			
			Salesperson sp = new Salesperson();
			sp.setCommissionpct(new BigDecimal(0.24));
			sp.setBusinessentityid(1);
			sp.setSalesquota(new BigDecimal(100));
			
			sp.setSalesterritory(st);
			sp.setBusinessentityid(be.getBusinessentityid());
			
			Salesperson sp2 = new Salesperson();
			sp2.setCommissionpct(new BigDecimal(0.38));
			sp2.setBusinessentityid(2);
			sp2.setSalesquota(new BigDecimal(100));
			
			sp2.setSalesterritory(stEdit);
			sp2.setBusinessentityid(be2.getBusinessentityid());
			
			/*salesPersonService.save(sp);
			salesPersonService.save(sp2);*/
			
			Currencyrate currencyrate = new Currencyrate();
			currencyrate.setAveragerate(BigDecimal.valueOf(10));
			
			//currencyRateService.save(currencyrate);
			
			Currencyrate currencyrate2 = new Currencyrate();
			currencyrate2.setAveragerate(BigDecimal.valueOf(20));
			
			//currencyRateService.save(currencyrate2);
			
			
			Salesterritoryhistory sth = new Salesterritoryhistory();
			sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(7)));
			sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(2)));
			sth.setSalesperson(sp);
			sth.setSalesterritory(st);
			
			Salesterritoryhistory sth2 = new Salesterritoryhistory();
			sth2.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
			sth2.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
			sth2.setSalesperson(sp2);
			sth2.setSalesterritory(st);
			
			//salesterritoryhistoryservice.save(sth);
			//salesterritoryhistoryservice.save(sth2);
			
		};

	}
}
