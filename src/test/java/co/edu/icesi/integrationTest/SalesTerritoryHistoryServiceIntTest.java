package co.edu.icesi.integrationTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import co.edu.icesi.dev.uccareapp.transport.TallerunoApplication;
import co.edu.icesi.dev.uccareapp.transport.model.person.Businessentity;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritoryhistory;
import co.edu.icesi.dev.uccareapp.transport.model.sales.SalesterritoryhistoryPK;
import co.edu.icesi.dev.uccareapp.transport.repository.BusinessEntittyRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesPersonRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryRepository;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesTerritoryHistoryServiceImp;

@SpringBootTest
@ContextConfiguration(classes = TallerunoApplication.class)
public class SalesTerritoryHistoryServiceIntTest {

	private SalesTerritoryRepository salesTerritoryRepository;
	private BusinessEntittyRepository businessEntityRepository;
	private SalesPersonRepository salesPersonRepository;

	private SalesTerritoryHistoryServiceImp salesTerritoryHistoryService;

	@Autowired
	public SalesTerritoryHistoryServiceIntTest(SalesTerritoryRepository salesTerritoryRepository,
			BusinessEntittyRepository businessEntityRepository, SalesPersonRepository salesPersonRepository,
			SalesTerritoryHistoryServiceImp salesTerritoryHistoryService) {

		this.salesTerritoryRepository = salesTerritoryRepository;
		this.businessEntityRepository = businessEntityRepository;
		this.salesPersonRepository = salesPersonRepository;
		this.salesTerritoryHistoryService = salesTerritoryHistoryService;
	}

	@Test
	public void save() {
		Salesterritoryhistory sth = new Salesterritoryhistory();

		sth.setId(1);

		sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
		sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

		Salesperson sp = new Salesperson();
		sp.setBusinessentityid(1);

		Salesterritory st = new Salesterritory();
		st.setTerritoryid(1);

		sth.setSalesperson(sp);
		sth.setSalesterritory(st);

		Businessentity be = new Businessentity();
		be.setBusinessentityid(1);

		salesPersonRepository.save(sp);
		businessEntityRepository.save(be);
		salesTerritoryRepository.save(st);

		Salesterritoryhistory sthreturn = salesTerritoryHistoryService.save(sth);

		System.out.println(sthreturn.getId());
		System.out.println(sthreturn.getSalesperson().getBusinessentityid());
		System.out.println(sthreturn.getSalesterritory().getTerritoryid());
		System.out.println(sthreturn.getEnddate());
		
		Assertions.assertAll(
				() -> assertTrue(sth.getId().intValue() == sthreturn.getId().intValue()),
				() -> assertTrue(sth.getSalesperson().getBusinessentityid().intValue() == sthreturn.getSalesperson()
						.getBusinessentityid().intValue()),
				() -> assertTrue(sth.getSalesterritory().getTerritoryid().intValue() == sthreturn.getSalesterritory()
						.getTerritoryid().intValue()),
				() -> assertTrue(sth.getEnddate().compareTo(sthreturn.getEnddate()) == 0));

	}

	@Test
	public void edit() {
		
		Salesterritoryhistory sth = new Salesterritoryhistory();

		sth.setId(1);

		sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
		sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

		Salesperson sp = new Salesperson();
		sp.setBusinessentityid(1);

		Salesterritory st = new Salesterritory();
		st.setTerritoryid(1);

		sth.setSalesperson(sp);
		sth.setSalesterritory(st);

		Businessentity be = new Businessentity();
		be.setBusinessentityid(1);

		salesPersonRepository.save(sp);
		businessEntityRepository.save(be);
		salesTerritoryRepository.save(st);

		salesTerritoryHistoryService.save(sth);

		Salesterritoryhistory sthe = new Salesterritoryhistory();

		sthe.setId(1);
		sthe.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(4)));
		sthe.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(6)));
		sthe.setSalesperson(sp);
		sthe.setSalesterritory(st);
		
		
		Salesterritoryhistory sthedit = salesTerritoryHistoryService.edit(sthe);

		Assertions.assertAll(() -> assertTrue(
				sth.getId().intValue() == sthedit.getId().intValue()),
				() -> assertTrue(sth.getSalesperson().getBusinessentityid().intValue() == sthedit.getSalesperson()
						.getBusinessentityid().intValue()),
				() -> assertTrue(sth.getSalesterritory().getTerritoryid().intValue() == sthedit.getSalesterritory()
						.getTerritoryid().intValue()),
				() -> assertFalse(sth.getEnddate().compareTo(sthedit.getEnddate()) == 0));
	}
}
