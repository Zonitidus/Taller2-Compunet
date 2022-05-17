package co.edu.icesi.daotest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import co.edu.icesi.dev.uccareapp.transport.daos.SalesterritoryDAO;
import co.edu.icesi.dev.uccareapp.transport.daos.SalesterritoryhistoryDAO;
import co.edu.icesi.dev.uccareapp.transport.model.person.Businessentity;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritoryhistory;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryHistoryRepository;

@SpringBootTest
@ContextConfiguration(classes = TallerunoApplication.class)
@ExtendWith(SpringExtension.class)
@Rollback(false)
@TestInstance(Lifecycle.PER_CLASS)
public class TestSalesterritoryhistoryDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private SalesTerritoryHistoryRepository salesterritoryhistoryrepo;
	@Autowired
	private SalesterritoryhistoryDAO salesterritoryhistorydao;

	@BeforeEach
	public void setup() {
		this.salesterritoryhistoryrepo.deleteAll();
	}

	@Test
	@Transactional
	@Order(1)
	public void saveTest() {

		Salesterritoryhistory sth = new Salesterritoryhistory();

		sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
		sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

		sth.setSalesperson(this.entityManager.find(Salesperson.class, 1));
		sth.setSalesterritory(this.entityManager.find(Salesterritory.class, 1));

		this.salesterritoryhistorydao.save(sth);

		Salesterritoryhistory sthreturn = this.entityManager.find(Salesterritoryhistory.class, 1);

		System.out.println(sthreturn.getId());
		System.out.println(sthreturn.getSalesperson().getBusinessentityid());
		System.out.println(sthreturn.getSalesterritory().getTerritoryid());
		System.out.println(sthreturn.getEnddate());

		Assertions.assertAll(() -> assertTrue(sth.getId().intValue() == sthreturn.getId().intValue()),
				() -> assertTrue(sth.getSalesperson().getBusinessentityid().intValue() == sthreturn.getSalesperson()
						.getBusinessentityid().intValue()),
				() -> assertTrue(sth.getSalesterritory().getTerritoryid().intValue() == sthreturn.getSalesterritory()
						.getTerritoryid().intValue()),
				() -> assertTrue(sth.getEnddate().compareTo(sthreturn.getEnddate()) == 0));
	}

	@Test
	@Transactional
	@Order(2)
	public void updateTest() {

		Salesterritoryhistory sth = new Salesterritoryhistory();

		Timestamp ts = Timestamp.valueOf(LocalDateTime.now().minusDays(1));
		Timestamp tsend = Timestamp.valueOf(LocalDateTime.now().minusDays(5));

		sth.setEnddate(ts);
		sth.setModifieddate(tsend);

		sth.setSalesperson(this.entityManager.find(Salesperson.class, 1));
		sth.setSalesterritory(this.entityManager.find(Salesterritory.class, 1));

		this.entityManager.persist(sth);

		Salesterritoryhistory sthe = new Salesterritoryhistory();

		sthe.setId(2);
		sthe.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(4)));
		sthe.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(6)));
		sthe.setSalesperson(this.entityManager.find(Salesperson.class, 1));
		sthe.setSalesterritory(this.entityManager.find(Salesterritory.class, 2));

		this.salesterritoryhistorydao.update(sthe);

		Salesterritoryhistory sthedit = this.entityManager.find(Salesterritoryhistory.class, 2);

		Assertions.assertAll(() -> assertTrue(sth.getId().intValue() == sthedit.getId().intValue()),
				() -> assertTrue(sth.getSalesperson().getBusinessentityid().intValue() == 1),
				() -> assertTrue(sth.getSalesterritory().getTerritoryid().intValue() == 2),
				() -> assertFalse(tsend.compareTo(sthedit.getEnddate()) == 0));
	}

	@Test
	@Transactional
	@Order(3)
	public void findByIdTest() {

		Salesterritoryhistory sth = new Salesterritoryhistory();

		sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
		sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

		sth.setSalesperson(this.entityManager.find(Salesperson.class, 1));
		sth.setSalesterritory(this.entityManager.find(Salesterritory.class, 1));

		this.entityManager.persist(sth);

		Salesterritoryhistory sthreturn = this.entityManager.find(Salesterritoryhistory.class, 6);


		Assertions.assertAll(() -> assertTrue(sth.getId().intValue() == sthreturn.getId().intValue()),
				() -> assertTrue(sth.getSalesperson().getBusinessentityid().intValue() == sthreturn.getSalesperson()
						.getBusinessentityid().intValue()),
				() -> assertTrue(sth.getSalesterritory().getTerritoryid().intValue() == sthreturn.getSalesterritory()
						.getTerritoryid().intValue()),
				() -> assertTrue(sth.getEnddate().compareTo(sthreturn.getEnddate()) == 0));
	}
	
	@Test
	@Transactional
	@Order(3)
	public void findAllTest() {
		

		Salesterritoryhistory sth = new Salesterritoryhistory();

		sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
		sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

		sth.setSalesperson(this.entityManager.find(Salesperson.class, 1));
		sth.setSalesterritory(this.entityManager.find(Salesterritory.class, 1));


		Salesterritoryhistory sth2 = new Salesterritoryhistory();

		sth2.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
		sth2.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

		sth2.setSalesperson(this.entityManager.find(Salesperson.class, 1));
		sth2.setSalesterritory(this.entityManager.find(Salesterritory.class, 2));
		
		Salesterritoryhistory sth3 = new Salesterritoryhistory();

		sth3.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
		sth3.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

		sth3.setSalesperson(this.entityManager.find(Salesperson.class, 1));
		sth3.setSalesterritory(this.entityManager.find(Salesterritory.class, 2));
		
		this.entityManager.persist(sth);
		this.entityManager.persist(sth2);
		this.entityManager.persist(sth3);
		
		ArrayList<Salesterritoryhistory> sths = (ArrayList<Salesterritoryhistory>)this.salesterritoryhistorydao.findAll();
		
		Assertions.assertAll(
				() -> assertTrue(sths.contains(sth)),
				() -> assertTrue(sths.contains(sth2)),
				() -> assertTrue(sths.contains(sth3)),
				() -> assertTrue(sths.size() == 3));
		
	}
	@Test
	@Transactional
	@Order(4)
	public void findBySalespersonidTest() {
		
		Salesperson sp = new Salesperson();
		sp.setCommissionpct(new BigDecimal(0.6));
		sp.setSalesquota(new BigDecimal(100));
		sp.setSalesterritory(this.entityManager.find(Salesterritory.class, 1));

		this.entityManager.persist(sp);
		
		Salesterritoryhistory sth = new Salesterritoryhistory();

		sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
		sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

		sth.setSalesperson(this.entityManager.find(Salesperson.class, 1));
		sth.setSalesterritory(this.entityManager.find(Salesterritory.class, 1));


		Salesterritoryhistory sth2 = new Salesterritoryhistory();

		sth2.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
		sth2.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

		sth2.setSalesperson(this.entityManager.find(Salesperson.class, 2));
		sth2.setSalesterritory(this.entityManager.find(Salesterritory.class, 2));
		
		Salesterritoryhistory sth3 = new Salesterritoryhistory();

		sth3.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
		sth3.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

		sth3.setSalesperson(this.entityManager.find(Salesperson.class, 1));
		sth3.setSalesterritory(this.entityManager.find(Salesterritory.class, 2));
		
		this.entityManager.persist(sth);
		this.entityManager.persist(sth2);
		this.entityManager.persist(sth3);
		
		ArrayList<Salesterritoryhistory> sths = (ArrayList<Salesterritoryhistory>)this.salesterritoryhistorydao.findBySalespersonid(1);
		
		Assertions.assertAll(
				() -> assertTrue(sths.contains(sth)),
				() -> assertTrue(sths.contains(sth3)),
				() -> assertFalse(sths.contains(sth2)),
				() -> assertTrue(sths.size() == 2));
		
	}
	
	@Test
	@Transactional
	@Order(5)
	public void findBySalesterritoryidTest() {
		
		Salesterritoryhistory sth = new Salesterritoryhistory();

		sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
		sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

		sth.setSalesperson(this.entityManager.find(Salesperson.class, 1));
		sth.setSalesterritory(this.entityManager.find(Salesterritory.class, 1));


		Salesterritoryhistory sth2 = new Salesterritoryhistory();

		sth2.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
		sth2.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

		sth2.setSalesperson(this.entityManager.find(Salesperson.class, 1));
		sth2.setSalesterritory(this.entityManager.find(Salesterritory.class, 2));
		
		Salesterritoryhistory sth3 = new Salesterritoryhistory();

		sth3.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
		sth3.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

		sth3.setSalesperson(this.entityManager.find(Salesperson.class, 1));
		sth3.setSalesterritory(this.entityManager.find(Salesterritory.class, 2));
		
		this.entityManager.persist(sth);
		this.entityManager.persist(sth2);
		this.entityManager.persist(sth3);
		
		ArrayList<Salesterritoryhistory> sths = (ArrayList<Salesterritoryhistory>)this.salesterritoryhistorydao.findBySalesterritoryid(2);
		
		Assertions.assertAll(
				() -> assertTrue(sths.contains(sth2)),
				() -> assertTrue(sths.contains(sth3)),
				() -> assertFalse(sths.contains(sth)),
				() -> assertTrue(sths.size() == 2));
		
	}
}
