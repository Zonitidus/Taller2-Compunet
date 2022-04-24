package co.edu.icesi.dev.uccareapp.transport;


import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import co.edu.icesi.dev.uccareapp.transport.model.UserApp;
import co.edu.icesi.dev.uccareapp.transport.model.UserType;
import co.edu.icesi.dev.uccareapp.transport.model.person.Businessentity;
import co.edu.icesi.dev.uccareapp.transport.model.person.Countryregion;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.repository.BusinessEntittyRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.CountryRegionRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.UserRepository;

@SpringBootApplication
public class TallerunoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TallerunoApplication.class, args);
	}

	@Bean
	public CommandLineRunner dummy(UserRepository ur, BusinessEntittyRepository ber, 
			CountryRegionRepository countryRegionRepo, SalesTerritoryRepository salesTerritoryRepository) {

		return (args) -> {
			UserApp u = new UserApp();
			u.setId(1);
			u.setType(UserType.admin);
			u.setUsername("Admin");
			u.setPassword("{noop}123456789");
		
			ur.save(u);
			
			UserApp u2 = new UserApp();
			u2.setId(2);
			u2.setType(UserType.operator);
			u2.setUsername("Operator");
			u2.setPassword("{noop}123456789");
		
			ur.save(u2);
			
			
			Businessentity be = new Businessentity();
			be.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
			
			ber.save(be);
			
			Businessentity be2 = new Businessentity();
			be2.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(10)));
			
			ber.save(be2);
			
			
			
			String regionCodeUK = "";
			String regionCodeUSA = "";
			
			Countryregion cruk = new Countryregion();
			cruk.setName("Inglaterra");
			Countryregion crusa = new Countryregion();
			crusa.setName("EstadosUnidos");

			countryRegionRepo.save(cruk);
			countryRegionRepo.save(crusa);
			
			Iterable<Countryregion> crs = countryRegionRepo.findAll();
			for (Countryregion country : crs) {
				
				System.out.println(country.getName());
				
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
			st.setName("Inlgaterra");
			st.setCountryregioncode(regionCodeUK);

			salesTerritoryRepository.save(st);
			
			Salesterritory stEdit = new Salesterritory();
			stEdit.setTerritoryid(2);
			stEdit.setName("EstadosUnidos");
			stEdit.setCountryregioncode(regionCodeUSA);
			
			salesTerritoryRepository.save(stEdit);
			
		};

	}
}
