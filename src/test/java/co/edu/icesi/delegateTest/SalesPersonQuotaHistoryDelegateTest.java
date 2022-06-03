package co.edu.icesi.delegateTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.dev.uccareapp.transport.TallerunoApplication;
import co.edu.icesi.dev.uccareapp.transport.delegate.SalesPersonQuotaHistoryDelegate;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salespersonquotahistory;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TallerunoApplication.class)
class SalesPersonQuotaHistoryDelegateTest {

	@Mock
	private RestTemplate restTemplate;
	
	private SalesPersonQuotaHistoryDelegate salesPersonQuotaHistoryDelegate;
	
	@BeforeEach
	public void  Setup1() {
		salesPersonQuotaHistoryDelegate = new SalesPersonQuotaHistoryDelegate(restTemplate);
	}
	
	@Test
	void save() {
		Salespersonquotahistory spqh = new Salespersonquotahistory();
		spqh.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(10)));
		
		when(restTemplate.postForObject("http://localhost:8080/api/salesPersonQuotaHistory/add", spqh, Salespersonquotahistory.class)).thenReturn(null);
		
		salesPersonQuotaHistoryDelegate.save(spqh);
		
		// Verifies behavior happened once
		verify(restTemplate).postForObject("http://localhost:8080/api/salesPersonQuotaHistory/add", spqh, Salespersonquotahistory.class);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
		
	}
	
	@Test
	void edit() {
		Salespersonquotahistory spqh = new Salespersonquotahistory();
		spqh.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(10)));
		Mockito.doNothing().when(restTemplate).put("http://localhost:8080/api/salesPersonQuotaHistory/update", spqh);
		
		salesPersonQuotaHistoryDelegate.edit(spqh);
		
		// Verifies behavior happened once
		verify(restTemplate).put("http://localhost:8080/api/salesPersonQuotaHistory/update", spqh);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
	}
	
//	@Test
//	void delete() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	void findByID() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	void findAll() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	void findBySalespersonid() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	void findBySalesquota() {
//		fail("Not yet implemented");
//	}
//	

}
