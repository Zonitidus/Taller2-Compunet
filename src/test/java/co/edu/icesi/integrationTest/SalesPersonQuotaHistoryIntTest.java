package co.edu.icesi.integrationTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import co.edu.icesi.dev.uccareapp.transport.TallerunoApplication;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salespersonquotahistory;
import co.edu.icesi.dev.uccareapp.transport.model.sales.SalespersonquotahistoryPK;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritoryhistory;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesPersonRepository;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesPersonQuotaHistoryServiceImp;

@SpringBootTest
@ContextConfiguration(classes = TallerunoApplication.class)
public class SalesPersonQuotaHistoryIntTest {

	private SalesPersonRepository salesPersonRepo;

	private SalesPersonQuotaHistoryServiceImp salesPersonQuotaHistoryService;

	@Autowired
	public SalesPersonQuotaHistoryIntTest(SalesPersonRepository salesPersonRepo,
			SalesPersonQuotaHistoryServiceImp salesPersonQuotaHistoryService) {

		this.salesPersonRepo = salesPersonRepo;
		this.salesPersonQuotaHistoryService = salesPersonQuotaHistoryService;
	}

	@Test
	public void save() {

		Salespersonquotahistory spqh = new Salespersonquotahistory();

		Salesperson sp = new Salesperson();
		sp.setBusinessentityid(1);

		spqh.setId(1);
		spqh.setSalesperson(sp);
		spqh.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
		spqh.setSalesquota(new BigDecimal(100));

		this.salesPersonRepo.save(sp);

		Salespersonquotahistory spqhreturn = salesPersonQuotaHistoryService.save(spqh);

		Assertions.assertAll(() -> assertTrue(
				spqh.getId().intValue() == spqhreturn.getId().intValue()),
				() -> assertTrue(spqh.getSalesperson().getBusinessentityid().intValue() == spqhreturn.getSalesperson()
						.getBusinessentityid().intValue()),
				() -> assertTrue(spqh.getModifieddate().compareTo(spqhreturn.getModifieddate()) == 0),
				() -> assertTrue(spqh.getSalesquota().compareTo(spqhreturn.getSalesquota()) == 0));
	}

	@Test
	public void edit() {

		Salespersonquotahistory spqh = new Salespersonquotahistory();

		Salesperson sp = new Salesperson();
		sp.setBusinessentityid(1);

		spqh.setId(1);
		spqh.setSalesperson(sp);
		spqh.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
		spqh.setSalesquota(new BigDecimal(100));

		this.salesPersonRepo.save(sp);
		
		this.salesPersonQuotaHistoryService.save(spqh);

		Salespersonquotahistory spqhEdit = new Salespersonquotahistory();

		spqhEdit.setId(1);
		spqhEdit.setSalesperson(sp);
		spqhEdit.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
		spqhEdit.setSalesquota(new BigDecimal(50));

		Salespersonquotahistory spqhreturn = salesPersonQuotaHistoryService.edit(spqhEdit);

		
		Assertions.assertAll(
				() -> assertTrue(spqh.getId().intValue() == spqhreturn.getId().intValue()),
				() -> assertTrue(spqh.getSalesperson().getBusinessentityid().intValue() == spqhreturn.getSalesperson()
						.getBusinessentityid().intValue()),
				() -> assertFalse(spqh.getModifieddate().compareTo(spqhreturn.getModifieddate()) == 0),
				() -> assertTrue(spqhreturn.getSalesquota().compareTo(new BigDecimal(50)) == 0));
	}
}
