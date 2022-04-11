package co.edu.icesi.unitTest;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import co.edu.icesi.dev.uccareapp.transport.model.person.Businessentity;
import co.edu.icesi.dev.uccareapp.transport.model.person.Countryregion;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.repository.BusinessEntittyRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.CountryRegionRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.EmployeeRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesPersonRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryRepository;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesPersonServiceImp;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesTerritoryServiceImp;
import co.edu.icesi.dev.uccareapp.transport.TallerunoApplication;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TallerunoApplication.class)
public class SalesTerritoryServiceTest {

	@Mock
	private CountryRegionRepository countryRegionRepo;
	@Mock
	private SalesTerritoryRepository salesTerritoryRepo;

	
	private SalesTerritoryServiceImp salesTerritoryService;

	private Optional<Countryregion> COUNTRYREGION = Optional.of(new Countryregion());

	@BeforeEach
	public void setup() {
		this.salesTerritoryService = new SalesTerritoryServiceImp(countryRegionRepo, salesTerritoryRepo);
	}

	@Nested
	class Save {

		@Test
		@DisplayName("Null object test")
		public void save1() {
			assertThrows(RuntimeException.class, () -> {
				salesTerritoryService.save(null);
			});
		}

		@Test
		@DisplayName("Short name")
		public void save2() {
			assertThrows(RuntimeException.class, () -> {
				Salesterritory st = new Salesterritory();
				st.setName("Camb");
				st.setCountryregioncode("UK");
				salesTerritoryService.save(st);
			});
		}

		@Test
		@DisplayName("Invalid country-region code")
		public void save3() {

			assertThrows(RuntimeException.class, () -> {

				Salesterritory st = new Salesterritory();
				st.setName("Cambodia");
				st.setCountryregioncode("90w8oefd");
				
				salesTerritoryService.save(st);
			});
		}
		
		@Test
		@DisplayName("All attributes within valid range")
		public void save4() {

			Salesterritory st = new Salesterritory();
			st.setName("Cambodia");
			st.setCountryregioncode("UK");
			

			when(salesTerritoryRepo.save(st)).thenReturn(st);
			when(countryRegionRepo.findById(st.getCountryregioncode())).thenReturn(COUNTRYREGION);

			Salesterritory streturn = salesTerritoryService.save(st);

			assertSame(st, streturn);

			verify(salesTerritoryRepo).save(st);
			verify(countryRegionRepo).findById(st.getCountryregioncode());

			verifyNoMoreInteractions(salesTerritoryRepo);
			verifyNoMoreInteractions(countryRegionRepo);
		}
	}

	@Nested
	class Edit {

		@Test
		@DisplayName("Null object test")
		public void edit1() {
			assertThrows(RuntimeException.class, () -> {
				salesTerritoryService.edit(null);
			});
		}

		@Test
		@DisplayName("Short name")
		public void edit2() {
			assertThrows(RuntimeException.class, () -> {
				Salesterritory st = new Salesterritory();
				st.setName("Camb");
				st.setCountryregioncode("UK");
				salesTerritoryService.edit(st);
			});
		}

		@Test
		@DisplayName("Invalid country-region code")
		public void edit3() {

			assertThrows(RuntimeException.class, () -> {
				Salesterritory st = new Salesterritory();
				st.setName("Cambodia");
				st.setCountryregioncode("90w8oefd");
				salesTerritoryService.edit(st);
			});
		}
		
		@Test
		@DisplayName("All attributes within valid range")
		public void edit4() {

			
			Salesterritory st = new Salesterritory();
			st.setTerritoryid(10);
			st.setName("Cambodia");
			st.setCountryregioncode("UK");
			
			
			lenient().when(salesTerritoryRepo.findById(st.getTerritoryid())).thenReturn(Optional.of(st));
			when(salesTerritoryRepo.save(st)).thenReturn(st);
			when(countryRegionRepo.findById(st.getCountryregioncode())).thenReturn(COUNTRYREGION);

			Salesterritory ste = new Salesterritory();
			ste.setTerritoryid(10);
			ste.setName("AAAAAAAAAA");
			ste.setCountryregioncode("UK");
			
			Salesterritory streturn = salesTerritoryService.edit(ste);

			assertNotSame(ste, streturn);

			verify(salesTerritoryRepo).save(st);
			verify(countryRegionRepo).findById(st.getCountryregioncode());

			verifyNoMoreInteractions(countryRegionRepo);
		}
	}

}
