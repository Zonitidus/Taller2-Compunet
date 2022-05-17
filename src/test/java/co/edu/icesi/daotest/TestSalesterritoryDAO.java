package co.edu.icesi.daotest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.icesi.dev.uccareapp.transport.TallerunoApplication;
import co.edu.icesi.dev.uccareapp.transport.daos.SalesterritoryDAO;
import co.edu.icesi.dev.uccareapp.transport.model.person.Countryregion;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.repository.CountryRegionRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesPersonRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryRepository;

@SpringBootTest
@ContextConfiguration(classes = TallerunoApplication.class)
@ExtendWith(SpringExtension.class)
@Rollback(false)
@TestInstance(Lifecycle.PER_CLASS)
public class TestSalesterritoryDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private SalesterritoryDAO salesterritorydao;
	
	@Autowired
	private CountryRegionRepository countryregionrepo;
	@Autowired
	private SalesTerritoryRepository salesterritoryrepo;
	@Autowired
	private SalesPersonRepository salespersonrepo;

	private String regionCodeGA;
	private String regionCodeRU;
	
	@BeforeEach
	public void setup() {
		//this.salesterritoryrepo.deleteAll();
		//this.countryregionrepo.deleteAll();
	}
	
	@BeforeAll
	public void setupCountryRegions() {
		
		Countryregion cruk = new Countryregion();
		cruk.setName("Gales");
		Countryregion crusa = new Countryregion();
		crusa.setName("Rusia");

		this.countryregionrepo.save(cruk);
		this.countryregionrepo.save(crusa);
		
		Iterable<Countryregion> crs = this.countryregionrepo.findAll();
		for (Countryregion country : crs) {
			
			if(country.getName() != null) {
				if (country.getName().equals("Gales")) {
					regionCodeGA = country.getCountryregioncode();
				} else {
					regionCodeRU = country.getCountryregioncode();
				}
			}
		}
	}
	
	@Test
	@Transactional
	@Order(1)
	public void saveTest() {
		
		Salesterritory st = new Salesterritory();
		st.setName("Cardiff");
		st.setCountryregioncode(this.regionCodeGA);

		this.salesterritorydao.save(st);
		
		Salesterritory streturn = this.entityManager.find(Salesterritory.class, 3);
		
		Assertions.assertAll(() -> assertTrue(
				st.getTerritoryid().compareTo(streturn.getTerritoryid()) == 0),
				() -> assertTrue(st.getName().compareTo(streturn.getName()) == 0),
				() -> assertTrue(st.getCountryregioncode().compareTo(streturn.getCountryregioncode()) == 0));
	}
	
	@Test
	@Transactional
	@Order(2)
	public void updateTest() {

		Salesterritory st = new Salesterritory();
		st.setName("Bangor");
		st.setCountryregioncode(this.regionCodeGA);

		this.entityManager.persist(st);

		Salesterritory stEdit = new Salesterritory();
		stEdit.setTerritoryid(4);
		stEdit.setName("Moscu");
		stEdit.setCountryregioncode(this.regionCodeRU);

		this.salesterritorydao.update(stEdit);
		
		Salesterritory streturn = this.entityManager.find(Salesterritory.class, 4);

		Assertions.assertAll(() -> assertTrue(stEdit.getTerritoryid().compareTo(streturn.getTerritoryid()) == 0),
				() -> assertTrue(streturn.getName().compareTo("Moscu") == 0),
				() -> assertTrue(streturn.getCountryregioncode().compareTo(this.regionCodeRU) == 0));
	}
	
	@Test
	@Transactional
	@Order(3)
	public void findByIdTest() {
		
		Salesterritory st = new Salesterritory();
		st.setName("Newport");
		st.setCountryregioncode(this.regionCodeGA);

		this.entityManager.persist(st);
		
		Salesterritory streturn = this.salesterritorydao.findById(5).get();
		
		System.out.println(streturn.getName());
		
		Assertions.assertAll(() -> assertTrue(streturn.getTerritoryid().compareTo(5) == 0),
				() -> assertTrue(streturn.getName().compareTo("Newport") == 0),
				() -> assertTrue(streturn.getCountryregioncode().compareTo(this.regionCodeGA) == 0));
	}
	
	@Test
	@Transactional
	@Order(4)
	public void findAllTest() {
		
		this.salespersonrepo.deleteAll();
		this.salesterritoryrepo.deleteAll();
		
		Salesterritory st = new Salesterritory();
		st.setName("Newport");
		st.setCountryregioncode(this.regionCodeGA);

		Salesterritory st2 = new Salesterritory();
		st2.setName("Kazan");
		st2.setCountryregioncode(this.regionCodeRU);

		Salesterritory st3 = new Salesterritory();
		st3.setName("Bangor");
		st3.setCountryregioncode(this.regionCodeGA);

		this.entityManager.persist(st);
		this.entityManager.persist(st2);
		this.entityManager.persist(st3);
		
		ArrayList<Salesterritory> sts = (ArrayList<Salesterritory>)this.salesterritorydao.findAll();
		
		Assertions.assertAll(
				() -> assertTrue(sts.contains(st)),
				() -> assertTrue(sts.contains(st2)),
				() -> assertTrue(sts.contains(st3)),
				() -> assertTrue(sts.size() == 3));
	}
	
	@Test
	@Transactional
	@Order(5)
	public void customQueryTest() {
		
	}
}
