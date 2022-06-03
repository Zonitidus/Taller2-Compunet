package co.edu.icesi.delegateTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
	
	@Test
	void delete() {
		int id = 1;
		
		Mockito.doNothing().when(restTemplate).delete("http://localhost:8080/api/salesPersonQuotaHistory/delete/"+id);
		
		salesPersonQuotaHistoryDelegate.delete(id);
		
		// Verifies behavior happened once
		verify(restTemplate).delete("http://localhost:8080/api/salesPersonQuotaHistory/delete/"+id);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);

	}
	
	@Test
	void findByID() {
		Salespersonquotahistory spqh = new Salespersonquotahistory();
		spqh.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(10)));
		
		int id = 1;
		
		Mockito.doReturn(spqh).when(restTemplate).getForObject("http://localhost:8080/api/salesPersonQuotaHistory/"+id, Salespersonquotahistory.class);
		
		Salespersonquotahistory r = salesPersonQuotaHistoryDelegate.findByID(id);
		
		assertNotNull(r);
		assertEquals(r.getModifieddate(), spqh.getModifieddate());
		// Verifies behavior happened once
		verify(restTemplate).getForObject("http://localhost:8080/api/salesPersonQuotaHistory/"+id, Salespersonquotahistory.class);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
	}
	
	@Test
	void findAll() {
		Salespersonquotahistory spqh = new Salespersonquotahistory();
		spqh.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(10)));
		
		Salespersonquotahistory spqh2 = new Salespersonquotahistory();
		spqh2.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(3)));
		
		ArrayList<Salespersonquotahistory> list = new ArrayList<Salespersonquotahistory>();
		list.add(spqh);
		list.add(spqh2);
		
		Salespersonquotahistory[] array = {spqh,spqh2};
		
		Mockito.doReturn(array).when(restTemplate).getForObject("http://localhost:8080/api/salesPersonQuotaHistory", Salespersonquotahistory[].class);
		
		List<Salespersonquotahistory> r = (List<Salespersonquotahistory>) salesPersonQuotaHistoryDelegate.findAll();
		
		assertNotNull(r);
		assertEquals(r.get(0), spqh);
		assertEquals(r.get(1), spqh2);
		// Verifies behavior happened once
		verify(restTemplate).getForObject("http://localhost:8080/api/salesPersonQuotaHistory", Salespersonquotahistory[].class);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
	}
	
	@Test
	void findBySalespersonid() {
		Salespersonquotahistory spqh = new Salespersonquotahistory();
		spqh.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(10)));
		
		Salespersonquotahistory spqh2 = new Salespersonquotahistory();
		spqh2.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(3)));
		
		ArrayList<Salespersonquotahistory> list = new ArrayList<Salespersonquotahistory>();
		list.add(spqh);
		list.add(spqh2);
		
		int salespersonid = 1;
		
		Salespersonquotahistory[] array = {spqh,spqh2};
		
		Mockito.doReturn(array).when(restTemplate).getForObject("http://localhost:8080/api/salesPersonQuotaHistory/findBySalesperson/"+salespersonid, Salespersonquotahistory[].class);
		
		List<Salespersonquotahistory> r = (List<Salespersonquotahistory>) salesPersonQuotaHistoryDelegate.findBySalespersonid(1);
		
		assertNotNull(r);
		assertEquals(r.get(0), spqh);
		assertEquals(r.get(1), spqh2);
		// Verifies behavior happened once
		verify(restTemplate).getForObject("http://localhost:8080/api/salesPersonQuotaHistory/findBySalesperson/"+salespersonid, Salespersonquotahistory[].class);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
	}
	
	@Test
	void findBySalesquota() {
		Salespersonquotahistory spqh = new Salespersonquotahistory();
		spqh.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(10)));
		
		Salespersonquotahistory spqh2 = new Salespersonquotahistory();
		spqh2.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(3)));
		
		ArrayList<Salespersonquotahistory> list = new ArrayList<Salespersonquotahistory>();
		list.add(spqh);
		list.add(spqh2);
		
		BigDecimal salesquota = new BigDecimal(1);
		
		Salespersonquotahistory[] array = {spqh,spqh2};
		
		Mockito.doReturn(array).when(restTemplate).getForObject("http://localhost:8080/api/salesPersonQuotaHistory/findBySalesquota/"+salesquota, Salespersonquotahistory[].class);
		
		List<Salespersonquotahistory> r = (List<Salespersonquotahistory>) salesPersonQuotaHistoryDelegate.findBySalesquota(salesquota);
		
		assertNotNull(r);
		assertEquals(r.get(0), spqh);
		assertEquals(r.get(1), spqh2);
		// Verifies behavior happened once
		verify(restTemplate).getForObject("http://localhost:8080/api/salesPersonQuotaHistory/findBySalesquota/"+salesquota, Salespersonquotahistory[].class);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
	}
	

}
