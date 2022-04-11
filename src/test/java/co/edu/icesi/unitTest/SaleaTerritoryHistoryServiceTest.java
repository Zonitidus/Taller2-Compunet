package co.edu.icesi.unitTest;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritoryhistory;
import co.edu.icesi.dev.uccareapp.transport.model.sales.SalesterritoryhistoryPK;
import co.edu.icesi.dev.uccareapp.transport.repository.BusinessEntittyRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.EmployeeRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesPersonRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryHistoryRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryRepository;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesPersonServiceImp;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesTerritoryHistoryServiceImp;
import co.edu.icesi.dev.uccareapp.transport.TallerunoApplication;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TallerunoApplication.class)
public class SaleaTerritoryHistoryServiceTest {

	@Mock
	private SalesTerritoryHistoryRepository salesTerritoryHistoryRepository;
	@Mock
	private SalesTerritoryRepository salesTerritoryRepository;
	@Mock
	private BusinessEntittyRepository businessEntityRepository;
	@Mock
	private SalesPersonRepository salesPersonRepository;

	@InjectMocks
	private SalesTerritoryHistoryServiceImp salesTerritoryHistoryService;

	private Optional<Salesterritory> SALESTERRITORY = Optional.of(new Salesterritory());
	private Optional<Businessentity> BUSINESSENTITY = Optional.of(new Businessentity());

	@BeforeEach
	public void setup() {
		this.salesTerritoryHistoryService = new SalesTerritoryHistoryServiceImp(salesTerritoryHistoryRepository,
				salesTerritoryRepository, businessEntityRepository, salesPersonRepository);
	}

	@Nested
	class Save {

		@Test
		@DisplayName("Null object test")
		public void save1() {
			assertThrows(RuntimeException.class, () -> {
				salesTerritoryHistoryService.save(null);
			});
		}

		@Test
		@DisplayName("EndDate greater than current date")
		public void save2() {
			assertThrows(RuntimeException.class, () -> {

				Salesterritoryhistory sth = new Salesterritoryhistory();
				sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().plusDays(5)));
				sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

				sth.setId(10);
				
				Salesperson sp = new Salesperson();
				sp.setBusinessentityid(10);

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);

				sth.setSalesperson(sp);
				sth.setSalesterritory(st);

				salesTerritoryHistoryService.save(sth);
			});
		}

		@Test
		@DisplayName("EndDate less than current init date")
		public void save3() {
			assertThrows(RuntimeException.class, () -> {

				Salesterritoryhistory sth = new Salesterritoryhistory();
				sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
				sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now()));


				sth.setId(10);
				
				Salesperson sp = new Salesperson();
				sp.setBusinessentityid(10);

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);

				sth.setSalesperson(sp);
				sth.setSalesterritory(st);
				
				salesTerritoryHistoryService.save(sth);
			});
		}

		@Test
		@DisplayName("Non-existent businessEntity code")
		public void save4() {
			assertThrows(RuntimeException.class, () -> {

				Salesterritoryhistory sth = new Salesterritoryhistory();
				sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
				sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now()));

				sth.setId(10);
				
				Salesperson sp = new Salesperson();
				sp.setBusinessentityid(10);

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);

				sth.setSalesperson(sp);
				sth.setSalesterritory(st);

				salesTerritoryHistoryService.save(sth);
			});
		}

		@Test
		@DisplayName("Non-existent salesTerritoryHistory code")
		public void save5() {
			assertThrows(RuntimeException.class, () -> {

				Salesterritoryhistory sth = new Salesterritoryhistory();
				sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
				sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now()));

				sth.setId(10);
				
				Salesperson sp = new Salesperson();
				sp.setBusinessentityid(10);

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);

				sth.setSalesperson(sp);
				sth.setSalesterritory(st);

				doReturn(Optional.of(new Businessentity()))
						.when(businessEntityRepository.findById(sth.getSalesperson().getBusinessentityid()));

				salesTerritoryHistoryService.save(sth);
			});
		}

		@Test
		@DisplayName("All attributes within valid ranges")
		public void save6() {

			Salesterritoryhistory sth = new Salesterritoryhistory();
			sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
			sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

			sth.setId(10);
			
			Salesperson sp = new Salesperson();
			sp.setBusinessentityid(10);

			Salesterritory st = new Salesterritory();
			st.setTerritoryid(10);

			sth.setSalesperson(sp);
			sth.setSalesterritory(st);

			lenient().when(businessEntityRepository.findById(sth.getSalesperson().getBusinessentityid()))
					.thenReturn(BUSINESSENTITY);

			lenient().when(salesTerritoryRepository.findById(sth.getSalesterritory().getTerritoryid()))
					.thenReturn(SALESTERRITORY);

			lenient().when(salesTerritoryHistoryRepository.save(sth)).thenReturn(sth);

			Salesterritoryhistory sthreturn = salesTerritoryHistoryService.save(sth);

			assertSame(sth, sthreturn);

			verify(salesTerritoryHistoryRepository).save(sth);
			verify(businessEntityRepository).findById(10);
			verify(salesTerritoryRepository).findById(10);

			verifyNoMoreInteractions(salesTerritoryHistoryRepository);
			verifyNoMoreInteractions(businessEntityRepository);
			verifyNoMoreInteractions(salesTerritoryRepository);
		}

		@Test
		@DisplayName("Null attributes")
		public void save7() {
			Assertions.assertAll(() -> assertThrows(RuntimeException.class, () -> {
				Salesterritoryhistory sth = new Salesterritoryhistory();
				// sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
				sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

				Salesperson sp = new Salesperson();
				sp.setBusinessentityid(10);

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);

				sth.setSalesperson(sp);
				sth.setSalesterritory(st);
				salesTerritoryHistoryService.save(sth);
			}), () -> assertThrows(RuntimeException.class, () -> {
				Salesterritoryhistory sth = new Salesterritoryhistory();
				sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
				// sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

				Salesperson sp = new Salesperson();
				sp.setBusinessentityid(10);

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);

				sth.setSalesperson(sp);
				sth.setSalesterritory(st);
				salesTerritoryHistoryService.save(sth);
			}), () -> assertThrows(RuntimeException.class, () -> {
				Salesterritoryhistory sth = new Salesterritoryhistory();
				sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
				sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

				Salesperson sp = new Salesperson();
				sp.setBusinessentityid(10);

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);

				// sth.setSalesperson(sp);
				sth.setSalesterritory(st);
				salesTerritoryHistoryService.save(sth);
			}), () -> assertThrows(RuntimeException.class, () -> {
				Salesterritoryhistory sth = new Salesterritoryhistory();
				sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
				sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

				Salesperson sp = new Salesperson();
				sp.setBusinessentityid(10);

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);

				sth.setSalesperson(sp);
				salesTerritoryHistoryService.save(sth);
			}));
		}
	}

	@Nested
	class Edit {

		@Test
		@DisplayName("Null object test")
		public void edit1() {
			assertThrows(RuntimeException.class, () -> {
				salesTerritoryHistoryService.edit(null);
			});
		}

		@Test
		@DisplayName("EndDate greater than current date")
		public void edit2() {
			assertThrows(RuntimeException.class, () -> {

				Salesterritoryhistory sth = new Salesterritoryhistory();
				sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().plusDays(5)));
				sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
				
				sth.setId(10);
				
				Salesperson sp = new Salesperson();
				sp.setBusinessentityid(10);

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);

				sth.setSalesperson(sp);
				sth.setSalesterritory(st);

				salesTerritoryHistoryService.edit(sth);
			});
		}

		@Test
		@DisplayName("EndDate less than current init date")
		public void edit3() {
			assertThrows(RuntimeException.class, () -> {

				Salesterritoryhistory sth = new Salesterritoryhistory();
				sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
				sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now()));

				sth.setId(10);
				
				Salesperson sp = new Salesperson();
				sp.setBusinessentityid(10);

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);

				sth.setSalesperson(sp);
				sth.setSalesterritory(st);
				
				salesTerritoryHistoryService.edit(sth);
			});
		}

		@Test
		@DisplayName("Non-existent businessEntity code")
		public void edit4() {
			assertThrows(RuntimeException.class, () -> {

				Salesterritoryhistory sth = new Salesterritoryhistory();
				sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
				sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now()));

				sth.setId(10);
				
				Salesperson sp = new Salesperson();
				sp.setBusinessentityid(10);

				sth.setSalesperson(sp);

				salesTerritoryHistoryService.edit(sth);
			});
		}

		@Test
		@DisplayName("Non-existent salesTerritoryHistory code")
		public void edit5() {
			assertThrows(RuntimeException.class, () -> {

				Salesterritoryhistory sth = new Salesterritoryhistory();
				sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
				sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now()));

				SalesterritoryhistoryPK stpk = new SalesterritoryhistoryPK();
				stpk.setBusinessentityid(10);
				stpk.setStartdate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
				stpk.setTerritoryid(10);

				
				Salesperson sp = new Salesperson();
				sp.setBusinessentityid(10);

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);

				sth.setSalesperson(sp);
				sth.setSalesterritory(st);

				doReturn(Optional.of(new Businessentity()))
						.when(businessEntityRepository.findById(sth.getSalesperson().getBusinessentityid()));

				salesTerritoryHistoryService.edit(sth);
			});
		}

		@Test
		@DisplayName("All attributes within valid ranges")
		public void edit6() {

			Salesterritoryhistory sth = new Salesterritoryhistory();

			sth.setId(10);
			sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
			sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

			Salesperson sp = new Salesperson();
			sp.setBusinessentityid(10);

			Salesterritory st = new Salesterritory();
			st.setTerritoryid(10);

			sth.setSalesperson(sp);
			sth.setSalesterritory(st);

			lenient().when(businessEntityRepository.findById(sth.getSalesperson().getBusinessentityid()))
					.thenReturn(BUSINESSENTITY);

			lenient().when(salesTerritoryRepository.findById(sth.getSalesterritory().getTerritoryid()))
					.thenReturn(SALESTERRITORY);

			lenient().when(salesTerritoryHistoryRepository.save(sth)).thenReturn(sth);

			Salesterritoryhistory sthEdit = new Salesterritoryhistory();
			sthEdit.setId(10);
			sthEdit.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
			sthEdit.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

			Salesperson spe = new Salesperson();
			spe.setBusinessentityid(50);

			Salesterritory ste = new Salesterritory();
			ste.setTerritoryid(50);

			sthEdit.setSalesperson(sp);
			sthEdit.setSalesterritory(st);

			lenient().when(salesTerritoryHistoryRepository.findById(sth.getId())).thenReturn(Optional.of(sthEdit));

			Salesterritoryhistory sthreturn = salesTerritoryHistoryService.edit(sthEdit);

			assertNotSame(sth, sthreturn);

			verify(businessEntityRepository).findById(10);
			verify(salesTerritoryRepository).findById(10);

			verifyNoMoreInteractions(businessEntityRepository);
			verifyNoMoreInteractions(salesTerritoryRepository);
		}
		
		@Test
		@DisplayName("Null attributes")
		public void edit7() {
			Assertions.assertAll(() -> assertThrows(RuntimeException.class, () -> {
				Salesterritoryhistory sth = new Salesterritoryhistory();
				// sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
				sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

				Salesperson sp = new Salesperson();
				sp.setBusinessentityid(10);

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);

				sth.setSalesperson(sp);
				sth.setSalesterritory(st);
				salesTerritoryHistoryService.edit(sth);
			}), () -> assertThrows(RuntimeException.class, () -> {
				Salesterritoryhistory sth = new Salesterritoryhistory();
				sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
				// sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

				Salesperson sp = new Salesperson();
				sp.setBusinessentityid(10);

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);

				sth.setSalesperson(sp);
				sth.setSalesterritory(st);
				salesTerritoryHistoryService.edit(sth);
			}), () -> assertThrows(RuntimeException.class, () -> {
				Salesterritoryhistory sth = new Salesterritoryhistory();
				sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
				sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

				Salesperson sp = new Salesperson();
				sp.setBusinessentityid(10);

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);

				// sth.setSalesperson(sp);
				sth.setSalesterritory(st);
				salesTerritoryHistoryService.edit(sth);
			}), () -> assertThrows(RuntimeException.class, () -> {
				Salesterritoryhistory sth = new Salesterritoryhistory();
				sth.setEnddate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
				sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));

				Salesperson sp = new Salesperson();
				sp.setBusinessentityid(10);

				Salesterritory st = new Salesterritory();
				st.setTerritoryid(10);

				sth.setSalesperson(sp);
				// sth.setSalesterritory(st);
				salesTerritoryHistoryService.edit(sth);
			}));
		}
	}
}
