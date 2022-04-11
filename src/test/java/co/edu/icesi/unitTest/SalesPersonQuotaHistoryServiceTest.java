package co.edu.icesi.unitTest;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salespersonquotahistory;
import co.edu.icesi.dev.uccareapp.transport.model.sales.SalespersonquotahistoryPK;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.model.sales.SalesterritoryhistoryPK;
import co.edu.icesi.dev.uccareapp.transport.repository.BusinessEntittyRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.EmployeeRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesPersonQuotaHistoryRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesPersonRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryRepository;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesPersonQuotaHistoryServiceImp;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesPersonServiceImp;
import co.edu.icesi.dev.uccareapp.transport.TallerunoApplication;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TallerunoApplication.class)
public class SalesPersonQuotaHistoryServiceTest {

	@Mock
	private SalesPersonQuotaHistoryRepository salesPersonQuotaHistoryRepo;
	@Mock
	private SalesPersonRepository salesPersonRepo;

	@InjectMocks
	private SalesPersonQuotaHistoryServiceImp salesPersonQuotaHistoryService;

	@BeforeEach
	public void setup() {
		this.salesPersonQuotaHistoryService = new SalesPersonQuotaHistoryServiceImp(salesPersonQuotaHistoryRepo,
				salesPersonRepo);
	}

	private Optional<Salesperson> SALESPERSON = Optional.of(new Salesperson());
	private Optional<Salespersonquotahistory> SALESPERSONQUOTAHISTORY = Optional.of(new Salespersonquotahistory());

	@Nested
	class Save {

		@Test
		@DisplayName("Null object test")
		public void save1() {
			assertThrows(RuntimeException.class, () -> {
				salesPersonQuotaHistoryService.save(null);
			});
		}

		@Test
		@DisplayName("ModifiedDate pass current date")
		public void save2() {

			assertThrows(RuntimeException.class, () -> {
				Salespersonquotahistory spqh = new Salespersonquotahistory();
				
				
				Salesperson sp = new Salesperson();
				sp.setBusinessentityid(10);
				
				spqh.setId(10);
				spqh.setSalesperson(sp);
				spqh.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
				spqh.setSalesquota(new BigDecimal(100));
				salesPersonQuotaHistoryService.save(spqh);
			});
		}

		@Test
		@DisplayName("Negative sales quota")
		public void save3() {

			assertThrows(RuntimeException.class, () -> {
				Salespersonquotahistory spqh = new Salespersonquotahistory();
				
				Salesperson sp = new Salesperson();
				sp.setBusinessentityid(10);
				
				spqh.setId(10);
				spqh.setSalesperson(sp);
				spqh.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
				spqh.setSalesquota(new BigDecimal(-5));

				salesPersonQuotaHistoryService.save(spqh);
			});
		}

		@Test
		@DisplayName("All attributes within valid range")
		public void save4() {

			Salespersonquotahistory spqh = new Salespersonquotahistory();

			Salesperson sp = new Salesperson();
			sp.setBusinessentityid(10);

			spqh.setId(10);
			spqh.setSalesperson(sp);
			spqh.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
			spqh.setSalesquota(new BigDecimal(100));

			when(salesPersonRepo.findById(10)).thenReturn(SALESPERSON);
			when(salesPersonQuotaHistoryRepo.save(spqh)).thenReturn(spqh);

			Salespersonquotahistory spqhreturn = salesPersonQuotaHistoryService.save(spqh);

			assertSame(spqh, spqhreturn);

			verify(salesPersonQuotaHistoryRepo).save(spqh);
			verify(salesPersonRepo).findById(10);

			verifyNoMoreInteractions(salesPersonRepo);
			verifyNoMoreInteractions(salesPersonQuotaHistoryRepo);
		}

		@Test
		@DisplayName("Non-existent salesPerson")
		public void save5() {
			assertThrows(RuntimeException.class, () -> {

				Salespersonquotahistory spqh = new Salespersonquotahistory();

				Salesperson sp = new Salesperson();
				sp.setBusinessentityid(10);

				spqh.setId(10);
				spqh.setSalesperson(sp);
				spqh.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
				spqh.setSalesquota(new BigDecimal(100));

				lenient().when(salesPersonQuotaHistoryRepo.save(spqh)).thenReturn(spqh);

				Salespersonquotahistory spqhreturn = salesPersonQuotaHistoryService.save(spqh);

			});
		}
	}

	@Nested
	class Edit {

		@Test
		@DisplayName("Null object test")
		public void edit1() {
			assertThrows(RuntimeException.class, () -> {
				salesPersonQuotaHistoryService.edit(null);
			});
		}

		@Test
		@DisplayName("ModifiedDate pass current date")
		public void edit2() {

			assertThrows(RuntimeException.class, () -> {

				Salespersonquotahistory spqh = new Salespersonquotahistory();

				Salesperson sp = new Salesperson();
				sp.setBusinessentityid(10);

				spqh.setId(10);
				spqh.setSalesperson(sp);
				spqh.setModifieddate(Timestamp.valueOf(LocalDateTime.now().plusDays(5)));
				spqh.setSalesquota(new BigDecimal(100));

				salesPersonQuotaHistoryService.edit(spqh);
			});
		}

		@Test
		@DisplayName("Negative sales quota")
		public void edit3() {

			assertThrows(RuntimeException.class, () -> {

				Salespersonquotahistory spqh = new Salespersonquotahistory();

				Salesperson sp = new Salesperson();
				sp.setBusinessentityid(10);

				spqh.setId(10);
				spqh.setSalesperson(sp);
				spqh.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
				spqh.setSalesquota(new BigDecimal(-5));

				salesPersonQuotaHistoryService.edit(spqh);
			});
		}

		@Test
		@DisplayName("All attributes within valid range")
		public void edit4() {

			Salespersonquotahistory spqh = new Salespersonquotahistory();

			Salesperson sp = new Salesperson();
			sp.setBusinessentityid(10);

			spqh.setId(10);
			spqh.setSalesperson(sp);
			spqh.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
			spqh.setSalesquota(new BigDecimal(100));

			lenient().when(salesPersonRepo.findById(10)).thenReturn(SALESPERSON);
			lenient().when(salesPersonQuotaHistoryRepo.findById(spqh.getId())).thenReturn(SALESPERSONQUOTAHISTORY);
			lenient().when(salesPersonQuotaHistoryRepo.save(spqh)).thenReturn(spqh);

			Salespersonquotahistory spqhreturn = salesPersonQuotaHistoryService.edit(spqh);

			assertNotSame(spqh, spqhreturn);

			verify(salesPersonRepo).findById(10);
			verifyNoMoreInteractions(salesPersonRepo);
		}

		@Test
		@DisplayName("Non-existent salesPerson")
		public void edit5() {
			assertThrows(RuntimeException.class, () -> {

				Salespersonquotahistory spqh = new Salespersonquotahistory();

				Salesperson sp = new Salesperson();
				sp.setBusinessentityid(10);

				spqh.setId(10);
				spqh.setSalesperson(sp);
				spqh.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
				spqh.setSalesquota(new BigDecimal(100));

				lenient().when(salesPersonQuotaHistoryRepo.save(spqh)).thenReturn(spqh);

				Salespersonquotahistory spqhreturn = salesPersonQuotaHistoryService.edit(spqh);

			});
		}
	}
}