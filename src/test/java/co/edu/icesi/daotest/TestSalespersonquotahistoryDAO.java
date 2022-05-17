package co.edu.icesi.daotest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
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
import co.edu.icesi.dev.uccareapp.transport.daos.SalespersonquotahistoryDAO;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salespersonquotahistory;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesPersonQuotaHistoryRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesPersonRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryRepository;

@SpringBootTest
@ContextConfiguration(classes = TallerunoApplication.class)
@ExtendWith(SpringExtension.class)
@Rollback(false)
@TestInstance(Lifecycle.PER_CLASS)
public class TestSalespersonquotahistoryDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private SalesPersonRepository salespersonrepo;
	@Autowired
	private SalesPersonQuotaHistoryRepository salespersonquotahistoryrepo;

	@Autowired
	private SalespersonquotahistoryDAO salespersonquotahistorydao;

	@BeforeEach
	public void setup() {
		// this.salespersonrepo.deleteAll();
		this.salespersonquotahistoryrepo.deleteAll();
	}

	@Test
	@Transactional
	@Order(1)
	public void saveTest() {

		Salespersonquotahistory spqh = new Salespersonquotahistory();

		Salesperson sp = new Salesperson();
		sp.setBusinessentityid(1);

		spqh.setSalesperson(sp);
		spqh.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
		spqh.setSalesquota(new BigDecimal(100));

		this.salespersonquotahistorydao.save(spqh);

		Salespersonquotahistory spqhreturn = this.entityManager.find(Salespersonquotahistory.class, 1);

		Assertions.assertAll(() -> assertTrue(1 == spqhreturn.getId().intValue()),
				() -> assertTrue(spqh.getSalesperson().getBusinessentityid().intValue() == spqhreturn.getSalesperson()
						.getBusinessentityid().intValue()),
				() -> assertTrue(spqh.getModifieddate().compareTo(spqhreturn.getModifieddate()) == 0),
				() -> assertTrue(spqh.getSalesquota().compareTo(spqhreturn.getSalesquota()) == 0));

	}

	@Test
	@Transactional
	@Order(2)
	public void updateTest() {

		Salespersonquotahistory spqh = new Salespersonquotahistory();

		Salesperson sp = new Salesperson();
		sp.setCommissionpct(new BigDecimal(0.5));
		sp.setSalesquota(new BigDecimal(100));
		sp.setSalesterritory(this.entityManager.find(Salesterritory.class, 1));

		this.entityManager.persist(sp);

		Timestamp ts = Timestamp.valueOf(LocalDateTime.now().minusDays(5));

		spqh.setSalesperson(sp);
		spqh.setModifieddate(ts);
		spqh.setSalesquota(new BigDecimal(100));

		this.salespersonquotahistorydao.save(spqh);

		Salespersonquotahistory spqhEdit = new Salespersonquotahistory();

		Salesperson sp2 = new Salesperson();
		sp2.setCommissionpct(new BigDecimal(0.7));
		sp2.setSalesquota(new BigDecimal(2000));
		sp2.setSalesterritory(this.entityManager.find(Salesterritory.class, 2));

		this.entityManager.persist(sp2);

		spqhEdit.setId(2);
		spqhEdit.setSalesperson(sp2);
		spqhEdit.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(3)));
		spqhEdit.setSalesquota(new BigDecimal(50));
		
		this.salespersonquotahistorydao.update(spqhEdit);
		Salespersonquotahistory spqhreturn = this.entityManager.find(Salespersonquotahistory.class, 2);

		Assertions.assertAll(

				() -> assertTrue(spqhreturn.getId().intValue() == 2),
				() -> assertTrue(spqhreturn.getSalesperson().getBusinessentityid() == 3),
				() -> assertTrue(spqhreturn.getModifieddate().compareTo(ts) != 0),
				() -> assertTrue(spqhreturn.getSalesquota().compareTo(new BigDecimal(50)) == 0));

	}

	@Test
	@Transactional
	@Order(3)
	public void findByIdTest() {

		Salespersonquotahistory spqh = new Salespersonquotahistory();

		Timestamp ts = Timestamp.valueOf(LocalDateTime.now().minusDays(5));

		spqh.setSalesperson(this.entityManager.find(Salesperson.class, 1));
		spqh.setModifieddate(ts);
		spqh.setSalesquota(new BigDecimal(9999));

		this.entityManager.persist(spqh);
		
		Salespersonquotahistory sqphfound = this.salespersonquotahistorydao.findById(12).get();
		
		Assertions.assertAll(

				() -> assertTrue(sqphfound.getId().intValue() == 12),
				() -> assertTrue(sqphfound.getSalesperson().getBusinessentityid() == 1),
				() -> assertTrue(sqphfound.getModifieddate().compareTo(ts) == 0),
				() -> assertTrue(sqphfound.getSalesquota().compareTo(new BigDecimal(9999)) == 0));
	}
	
	@Test
	@Transactional
	@Order(3)
	public void findByAllTest() {

		Salespersonquotahistory spqh = new Salespersonquotahistory();

		spqh.setSalesperson(this.entityManager.find(Salesperson.class, 1));
		spqh.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
		spqh.setSalesquota(new BigDecimal(57891));
		
		Salespersonquotahistory spqh2 = new Salespersonquotahistory();

		spqh2.setSalesperson(this.entityManager.find(Salesperson.class, 2));
		spqh2.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(2)));
		spqh2.setSalesquota(new BigDecimal(7890));
		
		Salespersonquotahistory spqh3 = new Salespersonquotahistory();

		spqh3.setSalesperson(this.entityManager.find(Salesperson.class, 1));
		spqh3.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
		spqh3.setSalesquota(new BigDecimal(10000));

		this.entityManager.persist(spqh);
		this.entityManager.persist(spqh2);
		this.entityManager.persist(spqh3);
		
		ArrayList<Salespersonquotahistory> spqhs = (ArrayList<Salespersonquotahistory>) this.salespersonquotahistorydao.findAll();
		
		Assertions.assertAll(

				() -> assertTrue(spqhs.contains(spqh)),
				() -> assertTrue(spqhs.contains(spqh2)),
				() -> assertTrue(spqhs.contains(spqh3)),
				() -> assertTrue(spqhs.size() == 3));
	}
	
	@Test
	@Transactional
	@Order(4)
	public void findBySalespersonIdTest() {
		

		Salespersonquotahistory spqh = new Salespersonquotahistory();

		spqh.setSalesperson(this.entityManager.find(Salesperson.class, 1));
		spqh.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
		spqh.setSalesquota(new BigDecimal(57891));
		
		Salespersonquotahistory spqh2 = new Salespersonquotahistory();

		spqh2.setSalesperson(this.entityManager.find(Salesperson.class, 2));
		spqh2.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(2)));
		spqh2.setSalesquota(new BigDecimal(7890));
		
		Salespersonquotahistory spqh3 = new Salespersonquotahistory();

		spqh3.setSalesperson(this.entityManager.find(Salesperson.class, 1));
		spqh3.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
		spqh3.setSalesquota(new BigDecimal(10000));

		this.entityManager.persist(spqh);
		this.entityManager.persist(spqh2);
		this.entityManager.persist(spqh3);
		
		ArrayList<Salespersonquotahistory> spqhs = (ArrayList<Salespersonquotahistory>) this.salespersonquotahistorydao.findBySalespersonid(1);
		
		Assertions.assertAll(

				() -> assertTrue(spqhs.contains(spqh)),
				() -> assertTrue(spqhs.contains(spqh3)),
				() -> assertFalse(spqhs.contains(spqh2)),
				() -> assertTrue(spqhs.size() == 2));
	}
	
	@Test
	@Transactional
	@Order(4)
	public void findBySalesquotaTest() {
		

		Salespersonquotahistory spqh = new Salespersonquotahistory();

		spqh.setSalesperson(this.entityManager.find(Salesperson.class, 1));
		spqh.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
		spqh.setSalesquota(new BigDecimal(10000));
		
		Salespersonquotahistory spqh2 = new Salespersonquotahistory();

		spqh2.setSalesperson(this.entityManager.find(Salesperson.class, 3));
		spqh2.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(2)));
		spqh2.setSalesquota(new BigDecimal(7890));
		
		Salespersonquotahistory spqh3 = new Salespersonquotahistory();

		spqh3.setSalesperson(this.entityManager.find(Salesperson.class, 1));
		spqh3.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
		spqh3.setSalesquota(new BigDecimal(10000));

		this.entityManager.persist(spqh);
		this.entityManager.persist(spqh2);
		this.entityManager.persist(spqh3);
		
		ArrayList<Salespersonquotahistory> spqhs = (ArrayList<Salespersonquotahistory>) this.salespersonquotahistorydao.findBySalesquota(new BigDecimal(10000));
		
		Assertions.assertAll(

				() -> assertTrue(spqhs.contains(spqh)),
				() -> assertTrue(spqhs.contains(spqh3)),
				() -> assertFalse(spqhs.contains(spqh2)),
				() -> assertTrue(spqhs.size() == 2));
	}

	public void print() {
		ArrayList<Salespersonquotahistory> spqhs = (ArrayList<Salespersonquotahistory>) this.salespersonquotahistorydao
				.findAll();

		for (Salespersonquotahistory s : spqhs) {
			System.out.println("Spqh: " + s.getId());
			System.out.println("\tsp: " + s.getSalesperson().getBusinessentityid());
			System.out.println("\tSalesquota: " + s.getSalesquota());
			System.out.println("\tModifieddate: " + s.getModifieddate());
		}
	}

	public void printSalesperson() {
		ArrayList<Salesperson> sps = (ArrayList<Salesperson>) this.salespersonrepo.findAll();

		for (Salesperson s : sps) {
			System.out.println(s.getBusinessentityid());
			System.out.println(s.getSalesterritory());
			System.out.println(s.getSalesquota());
		}
	}

}
