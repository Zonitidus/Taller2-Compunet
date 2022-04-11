package co.edu.icesi.unitTest;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
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
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.repository.BusinessEntittyRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.EmployeeRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesPersonRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryRepository;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesPersonServiceImp;
import co.edu.icesi.dev.uccareapp.transport.TallerunoApplication;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TallerunoApplication.class)
public class SalesPersonServiceTest {

	@Mock
	private BusinessEntittyRepository businessEntityRepo;
	@Mock
	private EmployeeRepository employeeRepo;
	@Mock
	private SalesPersonRepository salesPersonRepo;
	@Mock
	private SalesTerritoryRepository salesTerritoryRepo;

	@InjectMocks
	private SalesPersonServiceImp salesPersonService;

	private Optional<Salesterritory> SALESTERRITORY = Optional.of(new Salesterritory());
	private Optional<Salesperson> SALESPERSON = Optional.of(new Salesperson());
	private Optional<Businessentity> BUSINESSENTITY = Optional.of(new Businessentity());

	@BeforeEach
	public void setup() {
		this.salesPersonService = new SalesPersonServiceImp(businessEntityRepo, employeeRepo, salesPersonRepo,
				salesTerritoryRepo);
	}

	@Nested
	class Save {

		@Test
		@DisplayName("Null object test")
		public void save1() {
			assertThrows(RuntimeException.class, () -> {
				salesPersonService.save(null);
			});
		}

		@Test
		@DisplayName("Commision attribute off range left")
		public void save2() {
			assertThrows(RuntimeException.class, () -> {
				Salesperson sp = new Salesperson();
				sp.setCommissionpct(new BigDecimal(-0.5));
				sp.setBusinessentityid(10);
				sp.setSalesquota(new BigDecimal(100));

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);
				sp.setSalesterritory(st);

				salesPersonService.save(sp);
			});
		}

		@Test
		@DisplayName("Commision attribute off range right")
		public void save3() {

			assertThrows(RuntimeException.class, () -> {
				Salesperson sp = new Salesperson();
				sp.setCommissionpct(new BigDecimal(1.7));
				sp.setBusinessentityid(10);
				sp.setSalesquota(new BigDecimal(100));

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);
				sp.setSalesterritory(st);
				salesPersonService.save(sp);
			});
		}

		@Test
		@DisplayName("All attributes within valid range")
		public void save4() {

			Salesperson sp = new Salesperson();
			sp.setCommissionpct(new BigDecimal(0.5));
			sp.setBusinessentityid(10);
			sp.setSalesquota(new BigDecimal(100));

			Salesterritory st = new Salesterritory();
			st.setTerritoryid(10);
			sp.setSalesterritory(st);

			when(businessEntityRepo.findById(sp.getBusinessentityid())).thenReturn(BUSINESSENTITY);
			when(salesTerritoryRepo.findById(sp.getSalesterritory().getTerritoryid()))
					.thenReturn(Optional.of(new Salesterritory()));
			when(salesPersonRepo.save(sp)).thenReturn(sp);

			Salesperson spreturn = salesPersonService.save(sp);

			assertSame(sp, spreturn);

			verify(salesPersonRepo).save(sp);
			verify(businessEntityRepo).findById(sp.getBusinessentityid());
			verify(salesTerritoryRepo).findById(sp.getSalesterritory().getTerritoryid());

			verifyNoMoreInteractions(salesPersonRepo);
			verifyNoMoreInteractions(businessEntityRepo);
			verifyNoMoreInteractions(salesTerritoryRepo);
		}

		@Test
		@DisplayName("Negative quote attribute")
		public void save5() {
			assertThrows(RuntimeException.class, () -> {
				Salesperson sp = new Salesperson();
				sp.setCommissionpct(new BigDecimal(0.5));
				sp.setBusinessentityid(10);
				sp.setSalesquota(new BigDecimal(-3));

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);
				sp.setSalesterritory(st);
				salesPersonService.save(sp);
			});
		}

		@Test
		@DisplayName("Non-existent businessEntity")
		public void save6() {

			assertThrows(RuntimeException.class, () -> {

				Salesperson sp = new Salesperson();
				sp.setSalesquota(new BigDecimal(100));
				sp.setBusinessentityid(50);
				sp.setCommissionpct(new BigDecimal(0.5));

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);
				sp.setSalesterritory(st);

				lenient().when(salesTerritoryRepo.findById(sp.getSalesterritory().getTerritoryid()))
						.thenReturn(Optional.of(new Salesterritory()));
				lenient().when(salesPersonRepo.save(sp)).thenReturn(sp);

				Salesperson spreturn = salesPersonService.save(sp);

			});
		}

		@Test
		@DisplayName("Non-existent territory")
		public void save7() {

			assertThrows(RuntimeException.class, () -> {

				Salesperson sp = new Salesperson();
				sp.setSalesquota(new BigDecimal(100));
				sp.setBusinessentityid(50);
				sp.setCommissionpct(new BigDecimal(0.5));

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);
				sp.setSalesterritory(st);

				when(businessEntityRepo.findById(sp.getBusinessentityid())).thenReturn(BUSINESSENTITY);
				lenient().when(salesPersonRepo.save(sp)).thenReturn(sp);

				Salesperson spreturn = salesPersonService.save(sp);

			});
		}

		@Test
		@DisplayName("Null attributes")
		public void save8() {

			Assertions.assertAll(() -> assertThrows(RuntimeException.class, () -> {
				Salesperson sp = new Salesperson();
				// sp.setCommissionpct(new BigDecimal(0.5));
				sp.setBusinessentityid(10);
				sp.setSalesquota(new BigDecimal(-3));

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);
				sp.setSalesterritory(st);
				salesPersonService.save(sp);
			}), () -> assertThrows(RuntimeException.class, () -> {
				Salesperson sp = new Salesperson();
				sp.setCommissionpct(new BigDecimal(0.5));
				// sp.setBusinessentityid(10);
				sp.setSalesquota(new BigDecimal(-3));

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);
				sp.setSalesterritory(st);
				salesPersonService.save(sp);
			}), () -> assertThrows(RuntimeException.class, () -> {
				Salesperson sp = new Salesperson();
				sp.setCommissionpct(new BigDecimal(0.5));
				sp.setBusinessentityid(10);
				// sp.setSalesquota(new BigDecimal(-3));

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);
				sp.setSalesterritory(st);
				salesPersonService.save(sp);
			}), () -> assertThrows(RuntimeException.class, () -> {
				Salesperson sp = new Salesperson();
				sp.setCommissionpct(new BigDecimal(0.5));
				sp.setBusinessentityid(10);
				sp.setSalesquota(new BigDecimal(-3));

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);
				// sp.setSalesterritory(st);
				salesPersonService.save(sp);
			}));
		}

	}

	@Nested
	class Edit {

		@Test
		@DisplayName("Null object test")
		public void edit1() {
			assertThrows(RuntimeException.class, () -> {
				salesPersonService.edit(null);
			});
		}

		@Test
		@DisplayName("Commision attribute off range left")
		public void edit2() {
			assertThrows(RuntimeException.class, () -> {
				Salesperson sp = new Salesperson();
				sp.setCommissionpct(new BigDecimal(-0.5));
				sp.setBusinessentityid(10);
				sp.setSalesquota(new BigDecimal(100));

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);
				sp.setSalesterritory(st);
				salesPersonService.edit(sp);
			});
		}

		@Test
		@DisplayName("Commision attribute off range right")
		public void edit3() {

			assertThrows(RuntimeException.class, () -> {
				Salesperson sp = new Salesperson();
				sp.setCommissionpct(new BigDecimal(1.7));
				sp.setBusinessentityid(10);
				sp.setSalesquota(new BigDecimal(100));

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);
				sp.setSalesterritory(st);
				salesPersonService.edit(sp);
			});
		}

		@Test
		@DisplayName("Commision attribute on range")
		public void edit4() {

			Salesperson sp = new Salesperson();
			sp.setCommissionpct(new BigDecimal(0.5));
			sp.setSalesquota(new BigDecimal(0.2));
			sp.setBusinessentityid(10);

			Salesterritory st = new Salesterritory();
			st.setTerritoryid(10);
			sp.setSalesterritory(st);

			when(salesPersonRepo.findById(sp.getBusinessentityid())).thenReturn(SALESPERSON);
			when(businessEntityRepo.findById(sp.getBusinessentityid())).thenReturn(BUSINESSENTITY);
			when(salesTerritoryRepo.findById(sp.getSalesterritory().getTerritoryid())).thenReturn(SALESTERRITORY);

			lenient().when(salesPersonRepo.save(sp)).thenReturn(sp);

			Salesperson spreturn = salesPersonService.edit(sp);

			assertNotSame(sp, spreturn);

			verify(salesPersonRepo).findById(sp.getBusinessentityid());
			verify(businessEntityRepo).findById(sp.getBusinessentityid());
			verify(salesTerritoryRepo).findById(sp.getSalesterritory().getTerritoryid());

			verifyNoMoreInteractions(businessEntityRepo);
			verifyNoMoreInteractions(salesTerritoryRepo);
		}

		@Test
		@DisplayName("Negative quote attribute")
		public void edit5() {
			assertThrows(RuntimeException.class, () -> {
				Salesperson sp = new Salesperson();
				sp.setCommissionpct(new BigDecimal(0.5));
				sp.setBusinessentityid(10);
				sp.setSalesquota(new BigDecimal(-3));

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);
				sp.setSalesterritory(st);
				salesPersonService.edit(sp);
			});
		}

		@Test
		@DisplayName("Non-existent businessEntity")
		public void edit6() {

			assertThrows(RuntimeException.class, () -> {

				Salesperson sp = new Salesperson();
				sp.setSalesquota(new BigDecimal(100));
				sp.setBusinessentityid(50);
				sp.setCommissionpct(new BigDecimal(0.5));

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);
				sp.setSalesterritory(st);

				lenient().when(salesTerritoryRepo.findById(sp.getSalesterritory().getTerritoryid()))
						.thenReturn(Optional.of(new Salesterritory()));
				lenient().when(salesPersonRepo.save(sp)).thenReturn(sp);

				Salesperson spreturn = salesPersonService.edit(sp);

			});
		}

		@Test
		@DisplayName("Non-existent territory")
		public void edit7() {

			assertThrows(RuntimeException.class, () -> {

				Salesperson sp = new Salesperson();
				sp.setSalesquota(new BigDecimal(100));
				sp.setBusinessentityid(50);
				sp.setCommissionpct(new BigDecimal(0.5));

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);
				sp.setSalesterritory(st);

				when(businessEntityRepo.findById(sp.getBusinessentityid())).thenReturn(BUSINESSENTITY);
				lenient().when(salesPersonRepo.save(sp)).thenReturn(sp);

				Salesperson spreturn = salesPersonService.edit(sp);

			});
		}
		
		@Test
		@DisplayName("Null attributes")
		public void edit8() {

			Assertions.assertAll(() -> assertThrows(RuntimeException.class, () -> {
				Salesperson sp = new Salesperson();
				// sp.setCommissionpct(new BigDecimal(0.5));
				sp.setBusinessentityid(10);
				sp.setSalesquota(new BigDecimal(-3));

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);
				sp.setSalesterritory(st);
				salesPersonService.edit(sp);
			}), () -> assertThrows(RuntimeException.class, () -> {
				Salesperson sp = new Salesperson();
				sp.setCommissionpct(new BigDecimal(0.5));
				// sp.setBusinessentityid(10);
				sp.setSalesquota(new BigDecimal(-3));

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);
				sp.setSalesterritory(st);
				salesPersonService.edit(sp);
			}), () -> assertThrows(RuntimeException.class, () -> {
				Salesperson sp = new Salesperson();
				sp.setCommissionpct(new BigDecimal(0.5));
				sp.setBusinessentityid(10);
				// sp.setSalesquota(new BigDecimal(-3));

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);
				sp.setSalesterritory(st);
				salesPersonService.edit(sp);
			}), () -> assertThrows(RuntimeException.class, () -> {
				Salesperson sp = new Salesperson();
				sp.setCommissionpct(new BigDecimal(0.5));
				sp.setBusinessentityid(10);
				sp.setSalesquota(new BigDecimal(-3));

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);
				// sp.setSalesterritory(st);
				salesPersonService.edit(sp);
			}));
		}
	}

}
