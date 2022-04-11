package co.edu.icesi.integrationTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesPersonServiceImp;
import co.edu.icesi.dev.uccareapp.transport.TallerunoApplication;
import co.edu.icesi.dev.uccareapp.transport.model.person.Businessentity;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.repository.BusinessEntittyRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesPersonRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryRepository;

@SpringBootTest
@ContextConfiguration(classes = TallerunoApplication.class)
public class SalesPersonServiceIntTest {

	private BusinessEntittyRepository businessEntityRepo;
	private SalesPersonRepository salesPersonRepo;
	private SalesTerritoryRepository salesTerritoryRepo;

	private SalesPersonServiceImp salesPersonService;

	@Autowired
	public SalesPersonServiceIntTest(BusinessEntittyRepository businessEntityRepo,
			SalesTerritoryRepository salesTerritoryRepo, SalesPersonServiceImp salesPersonService) {

		this.businessEntityRepo = businessEntityRepo;
		this.salesTerritoryRepo = salesTerritoryRepo;
		this.salesPersonService = salesPersonService;
	}

	@Test
	public void save() {

		Salesperson sp = new Salesperson();
		sp.setCommissionpct(new BigDecimal(0.5));
		sp.setBusinessentityid(1);
		sp.setSalesquota(new BigDecimal(100));

		Salesterritory st = new Salesterritory();
		st.setTerritoryid(1);
		sp.setSalesterritory(st);

		Businessentity be = new Businessentity();
		be.setBusinessentityid(1);

		businessEntityRepo.save(be);
		salesTerritoryRepo.save(st);

		Salesperson spreturn = salesPersonService.save(sp);

		Assertions.assertAll(() -> assertTrue(sp.getCommissionpct().compareTo(spreturn.getCommissionpct()) == 0),
				() -> assertTrue(sp.getSalesquota().compareTo(spreturn.getSalesquota()) == 0),
				() -> assertTrue(sp.getBusinessentityid().compareTo(spreturn.getBusinessentityid()) == 0),
				() -> assertTrue(sp.getSalesterritory().getTerritoryid()
						.compareTo(spreturn.getSalesterritory().getTerritoryid()) == 0));

	}

	@Test
	public void edit() {

		Salesperson sp = new Salesperson();
		sp.setCommissionpct(new BigDecimal(0.5));
		sp.setBusinessentityid(1);
		sp.setSalesquota(new BigDecimal(100));

		Salesterritory st = new Salesterritory();
		st.setTerritoryid(1);
		sp.setSalesterritory(st);

		Businessentity be = new Businessentity();
		be.setBusinessentityid(1);

		businessEntityRepo.save(be);
		salesTerritoryRepo.save(st);

		salesPersonService.save(sp);

		Salesperson sp2 = new Salesperson();
		sp2.setCommissionpct(new BigDecimal(0.5));
		sp2.setBusinessentityid(1);
		sp2.setSalesquota(new BigDecimal(50));

		Salesterritory st2 = new Salesterritory();
		st2.setTerritoryid(1);
		sp2.setSalesterritory(st2);

		Businessentity be2 = new Businessentity();
		be2.setBusinessentityid(1);

		Salesperson spreturn = salesPersonService.edit(sp2);
		
		Assertions.assertAll(() -> assertTrue(sp2.getCommissionpct().compareTo(spreturn.getCommissionpct()) == 0),
				() -> assertTrue(sp2.getSalesquota().compareTo(new BigDecimal(50)) == 0),
				() -> assertTrue(sp2.getBusinessentityid().compareTo(spreturn.getBusinessentityid()) == 0),
				() -> assertTrue(sp2.getSalesterritory().getTerritoryid()
						.compareTo(spreturn.getSalesterritory().getTerritoryid()) == 0));
	}
}
