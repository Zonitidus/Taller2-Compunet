package co.edu.icesi.daotest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
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

@SpringBootTest
@ContextConfiguration(classes = TallerunoApplication.class)
@ExtendWith(SpringExtension.class)
@Rollback(false)
@TestInstance(Lifecycle.PER_CLASS)
public class TestSalespersonquotahistoryDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private SalespersonquotahistoryDAO salespersonquotahistorydao;

	@Test
	@Transactional
	@Order(1)
	public void saveTest() {

		Salespersonquotahistory spqh = new Salespersonquotahistory();

		spqh.setSalesperson(this.entityManager.find(Salesperson.class, 1));
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

}
