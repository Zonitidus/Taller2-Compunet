package co.edu.icesi.daotest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
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
import org.springframework.transaction.annotation.Propagation;

import co.edu.icesi.dev.uccareapp.transport.TallerunoApplication;
import co.edu.icesi.dev.uccareapp.transport.daos.SalespersonDAO;
import co.edu.icesi.dev.uccareapp.transport.model.person.Businessentity;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesPersonRepository;

@SpringBootTest
@ContextConfiguration(classes = TallerunoApplication.class)
@ExtendWith(SpringExtension.class)
@Rollback(false)
@TestInstance(Lifecycle.PER_CLASS)
class TestSalespersonDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private SalespersonDAO salespersondao;

	@Autowired
	private SalesPersonRepository salespersonrepo;

	@BeforeEach
	public void setup() {
		this.salespersonrepo.deleteAll();
	}

	@Test
	@Transactional
	@Order(1)
	public void saveTest() {

		Salesperson sp = new Salesperson();
		sp.setCommissionpct(new BigDecimal(0.5));
		sp.setSalesquota(new BigDecimal(100));
		sp.setSalesterritory(this.entityManager.find(Salesterritory.class, 1));

		this.salespersondao.save(sp);

		Salesperson spfind = entityManager.find(Salesperson.class, 2);

		Assertions.assertAll(() -> assertTrue(sp.getCommissionpct().compareTo(spfind.getCommissionpct()) == 0),
				() -> assertTrue(sp.getSalesquota().compareTo(spfind.getSalesquota()) == 0),
				() -> assertTrue(sp.getSalesterritory().getTerritoryid()
						.compareTo(spfind.getSalesterritory().getTerritoryid()) == 0));
	}

	@Test
	@Transactional
	@Order(2)
	public void updateTest() {
		
		Salesperson sp = new Salesperson();
		sp.setCommissionpct(new BigDecimal(0.6));
		sp.setSalesquota(new BigDecimal(100));
		sp.setSalesterritory(this.entityManager.find(Salesterritory.class, 2));

		this.entityManager.persist(sp);

		Salesperson spbefTemp = this.entityManager.find(Salesperson.class, 6);
		Salesperson spbefore = new Salesperson();
		
		spbefore.setBusinessentityid(spbefTemp.getBusinessentityid());
		spbefore.setCommissionpct(new BigDecimal(0.7));
		spbefore.setSalesquota(new BigDecimal(500));	
		spbefore.setSalesterritory(this.entityManager.find(Salesterritory.class, 1));
		
		this.salespersondao.update(spbefore);
		
		Salesperson spafter = this.entityManager.find(Salesperson.class, 6);
		
		Assertions.assertAll(
				() -> assertTrue(spafter.getCommissionpct().compareTo(new BigDecimal(0.7)) == 0),
				() -> assertTrue(spafter.getSalesquota().compareTo(new BigDecimal(500)) == 0),
				() -> assertTrue(spafter.getSalesterritory().getTerritoryid().compareTo(1) == 0));
		
	}

	@Test
	@Transactional
	@Order(3)
	public void findByIdTest() {

		Salesperson sp = new Salesperson();
		sp.setCommissionpct(new BigDecimal(0.6));
		sp.setSalesquota(new BigDecimal(100));
		sp.setSalesterritory(this.entityManager.find(Salesterritory.class, 1));

		this.entityManager.persist(sp);
		
		Salesperson spfind = this.salespersondao.findById(16).get();

		Assertions.assertAll(() -> assertTrue(sp.getCommissionpct().compareTo(spfind.getCommissionpct()) == 0),
				() -> assertTrue(sp.getSalesquota().compareTo(spfind.getSalesquota()) == 0),
				() -> assertTrue(sp.getSalesterritory().getTerritoryid()
						.compareTo(spfind.getSalesterritory().getTerritoryid()) == 0));

	}
	
	@Test
	@Transactional
	@Order(4)
	public void findAllTest() {
		
		Salesperson sp = new Salesperson();
		sp.setCommissionpct(new BigDecimal(0.6));
		sp.setSalesquota(new BigDecimal(1));
		sp.setSalesterritory(this.entityManager.find(Salesterritory.class, 1));
		
		Salesperson sp2 = new Salesperson();
		sp2.setCommissionpct(new BigDecimal(0.6));
		sp2.setSalesquota(new BigDecimal(2));
		sp2.setSalesterritory(this.entityManager.find(Salesterritory.class, 2));

		Salesperson sp3 = new Salesperson();
		sp3.setCommissionpct(new BigDecimal(0.6));
		sp3.setSalesquota(new BigDecimal(3));
		sp3.setSalesterritory(this.entityManager.find(Salesterritory.class, 1));
		
		this.entityManager.persist(sp);
		this.entityManager.persist(sp2);
		this.entityManager.persist(sp3);
		
		ArrayList<Salesperson> sps = (ArrayList<Salesperson>) this.salespersondao.findAll();
		
		Assertions.assertAll(
				() -> assertTrue(sps.contains(sp)),
				() -> assertTrue(sps.contains(sp2)),
				() -> assertTrue(sps.contains(sp3)),
				() -> assertTrue(sps.size() == 3));

	}
	
	@Test
	@Transactional
	@Order(5)
	public void findByTerritoryIdTest() {
		
		Salesperson sp = new Salesperson();
		sp.setCommissionpct(new BigDecimal(0.6));
		sp.setSalesquota(new BigDecimal(1));
		sp.setSalesterritory(this.entityManager.find(Salesterritory.class, 1));
		
		Salesperson sp2 = new Salesperson();
		sp2.setCommissionpct(new BigDecimal(0.6));
		sp2.setSalesquota(new BigDecimal(2));
		sp2.setSalesterritory(this.entityManager.find(Salesterritory.class, 2));

		Salesperson sp3 = new Salesperson();
		sp3.setCommissionpct(new BigDecimal(0.6));
		sp3.setSalesquota(new BigDecimal(3));
		sp3.setSalesterritory(this.entityManager.find(Salesterritory.class, 1));
		
		this.entityManager.persist(sp);
		this.entityManager.persist(sp2);
		this.entityManager.persist(sp3);
		
		ArrayList<Salesperson> sps = (ArrayList<Salesperson>) this.salespersondao.findByTerritoryid(1);
		
		Assertions.assertAll(
				() -> assertTrue(sps.contains(sp)),
				() -> assertFalse(sps.contains(sp2)),
				() -> assertTrue(sps.contains(sp3)),
				() -> assertTrue(sps.size() == 2));

	}
	
	@Test
	@Transactional
	@Order(6)
	public void findBySalesquotaTest() {
		
		Salesperson sp = new Salesperson();
		sp.setCommissionpct(new BigDecimal(0.6));
		sp.setSalesquota(new BigDecimal(2));
		sp.setSalesterritory(this.entityManager.find(Salesterritory.class, 1));
		
		Salesperson sp2 = new Salesperson();
		sp2.setCommissionpct(new BigDecimal(0.6));
		sp2.setSalesquota(new BigDecimal(2));
		sp2.setSalesterritory(this.entityManager.find(Salesterritory.class, 2));

		Salesperson sp3 = new Salesperson();
		sp3.setCommissionpct(new BigDecimal(0.6));
		sp3.setSalesquota(new BigDecimal(3));
		sp3.setSalesterritory(this.entityManager.find(Salesterritory.class, 1));
		
		this.entityManager.persist(sp);
		this.entityManager.persist(sp2);
		this.entityManager.persist(sp3);
		
		ArrayList<Salesperson> sps = (ArrayList<Salesperson>) this.salespersondao.findBySalesquota(new BigDecimal(2));
		
		Assertions.assertAll(
				() -> assertTrue(sps.contains(sp)),
				() -> assertFalse(sps.contains(sp3)),
				() -> assertTrue(sps.contains(sp2)),
				() -> assertTrue(sps.size() == 2));

	}
	
	@Test
	@Transactional
	@Order(7)
	public void findByCommissionpctTest() {
		
		Salesperson sp = new Salesperson();
		sp.setCommissionpct(new BigDecimal(0.6));
		sp.setSalesquota(new BigDecimal(2));
		sp.setSalesterritory(this.entityManager.find(Salesterritory.class, 1));
		
		Salesperson sp2 = new Salesperson();
		sp2.setCommissionpct(new BigDecimal(0.6));
		sp2.setSalesquota(new BigDecimal(2));
		sp2.setSalesterritory(this.entityManager.find(Salesterritory.class, 2));

		Salesperson sp3 = new Salesperson();
		sp3.setCommissionpct(new BigDecimal(0.3));
		sp3.setSalesquota(new BigDecimal(3));
		sp3.setSalesterritory(this.entityManager.find(Salesterritory.class, 1));
		
		this.entityManager.persist(sp);
		this.entityManager.persist(sp2);
		this.entityManager.persist(sp3);
		
		ArrayList<Salesperson> sps = (ArrayList<Salesperson>) this.salespersondao.findByCommissionpct(new BigDecimal(0.6));
		
		System.out.println("Salesperson commision");
		for(Salesperson spprint: sps) {
			System.out.println(spprint.getCommissionpct());
		}
		
		System.out.println("All: ");
		this.printSalespersons();
		
		/*Assertions.assertAll(
				() -> assertTrue(sps.contains(sp)),
				() -> assertFalse(sps.contains(sp3)),
				() -> assertTrue(sps.contains(sp2)),
				() -> assertTrue(sps.size() == 2));*/

	}
	
	@Test
	@Transactional
	@Order(8)
	public void customQueryTest() {
		

	}

	private void printSalespersons() {
		Iterable<Salesperson> salespersons = this.salespersondao.findAll();

		for (Salesperson sptemp : salespersons) {
			System.out.println("Salesperson: " + sptemp.getBusinessentityid());
			System.out.println(sptemp.getCommissionpct());
			System.out.println(sptemp.getSalesquota());
			System.out.println(sptemp.getSalesterritory().getTerritoryid());
		}
	}

}
