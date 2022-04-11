package co.edu.icesi.integrationTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import co.edu.icesi.dev.uccareapp.transport.TallerunoApplication;
import co.edu.icesi.dev.uccareapp.transport.model.person.Countryregion;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.repository.CountryRegionRepository;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesTerritoryServiceImp;

@SpringBootTest
@ContextConfiguration(classes = TallerunoApplication.class)
public class SalesTerritoryServiceIntTest {

	private CountryRegionRepository countryRegionRepo;
	private SalesTerritoryServiceImp salesTerritoryService;
	private String regionCodeUK;
	private String regionCodeUSA;

	@Autowired
	public SalesTerritoryServiceIntTest(CountryRegionRepository countryRegionRepo,
			SalesTerritoryServiceImp salesTerritoryService) {

		this.countryRegionRepo = countryRegionRepo;
		this.salesTerritoryService = salesTerritoryService;
	}

	@BeforeEach
	public void setup() {
		
		Countryregion cruk = new Countryregion();
		cruk.setName("Inglaterra");
		Countryregion crusa = new Countryregion();
		crusa.setName("EstadosUnidos");

		this.countryRegionRepo.save(cruk);
		this.countryRegionRepo.save(crusa);
		
		Iterable<Countryregion> crs = this.countryRegionRepo.findAll();
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
	}

	@Test
	public void save() {

		Salesterritory st = new Salesterritory();
		st.setTerritoryid(1);
		st.setName("Inlgaterra");
		st.setCountryregioncode(this.regionCodeUK);

		Salesterritory streturn = salesTerritoryService.save(st);

		Assertions.assertAll(() -> assertTrue(st.getTerritoryid().compareTo(streturn.getTerritoryid()) == 0),
				() -> assertTrue(st.getName().compareTo(streturn.getName()) == 0),
				() -> assertTrue(st.getCountryregioncode().compareTo(streturn.getCountryregioncode()) == 0));

	}

	@Test
	public void edit() {

		Salesterritory st = new Salesterritory();
		st.setTerritoryid(1);
		st.setName("Inlgaterra");
		st.setCountryregioncode(this.regionCodeUK);

		salesTerritoryService.save(st);

		Salesterritory stEdit = new Salesterritory();
		stEdit.setTerritoryid(1);
		stEdit.setName("EstadosUnidos");
		stEdit.setCountryregioncode(this.regionCodeUSA);

		Salesterritory streturn = salesTerritoryService.edit(stEdit);

		Assertions.assertAll(() -> assertTrue(stEdit.getTerritoryid().compareTo(streturn.getTerritoryid()) == 0),
				() -> assertTrue(streturn.getName().compareTo("EstadosUnidos") == 0),
				() -> assertTrue(streturn.getCountryregioncode().compareTo(this.regionCodeUSA) == 0));
	}

}
