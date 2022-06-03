package co.edu.icesi.delegateTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.dev.uccareapp.transport.TallerunoApplication;
import co.edu.icesi.dev.uccareapp.transport.delegate.SalesPersonQuotaHistoryDelegate;
import co.edu.icesi.dev.uccareapp.transport.delegate.SalesTerritoyHistoryDelegate;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritoryhistory;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TallerunoApplication.class)
class SalesTerritoryHistoryDelegateTest {

	@Mock
	private RestTemplate restTemplate;
	
	private SalesTerritoyHistoryDelegate salesTerritoyHistoryDelegate;
	
	@BeforeEach
	public void  Setup1() {
		salesTerritoyHistoryDelegate = new SalesTerritoyHistoryDelegate(restTemplate);
	}
	
	@Test
	void save() {
		Salesterritoryhistory sth = new Salesterritoryhistory();
		sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(10)));
		when(restTemplate.postForObject("http://localhost:8080/api/salesTerritoyHistory/add", sth, Salesterritoryhistory.class)).thenReturn(null);
		salesTerritoyHistoryDelegate.save(sth);
		
		// Verifies behavior happened once
		verify(restTemplate).postForObject("http://localhost:8080/api/salesTerritoyHistory/add", sth, Salesterritoryhistory.class);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
		
	}
	
	@Test
	void edit() {
		Salesterritoryhistory sth = new Salesterritoryhistory();
		sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(10)));
		Mockito.doNothing().when(restTemplate).put("http://localhost:8080/api/salesTerritoyHistory/update", sth);
		
		salesTerritoyHistoryDelegate.edit(sth);
		
		// Verifies behavior happened once
		verify(restTemplate).put("http://localhost:8080/api/salesTerritoyHistory/update", sth);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
	}
	
	@Test
	void delete() {
		int id = 1;
		
		Mockito.doNothing().when(restTemplate).delete("http://localhost:8080/api/salesTerritoyHistory/delete/"+id);
		
		salesTerritoyHistoryDelegate.delete(id);
		
		// Verifies behavior happened once
		verify(restTemplate).delete("http://localhost:8080/api/salesTerritoyHistory/delete/"+id);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);

	}
	
	@Test
	void findByID() {
		Salesterritoryhistory sth = new Salesterritoryhistory();
		sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(10)));
		
		int id = 1;
		
		Mockito.doReturn(sth).when(restTemplate).getForObject("http://localhost:8080/api/salesTerritoyHistory/"+id, Salesterritoryhistory.class);
		
		Salesterritoryhistory r = salesTerritoyHistoryDelegate.findById(id);
		
		assertNotNull(r);
		assertEquals(r.getModifieddate(), sth.getModifieddate());
		// Verifies behavior happened once
		verify(restTemplate).getForObject("http://localhost:8080/api/salesTerritoyHistory/"+id, Salesterritoryhistory.class);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
	}
	
	@Test
	void findAll() {
		Salesterritoryhistory sth = new Salesterritoryhistory();
		sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(10)));
		
		Salesterritoryhistory sth2 = new Salesterritoryhistory();
		sth2.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
		
		ArrayList<Salesterritoryhistory> list = new ArrayList<Salesterritoryhistory>();
		list.add(sth);
		list.add(sth2);
		
		Salesterritoryhistory[] array = {sth,sth2};
		
		Mockito.doReturn(array).when(restTemplate).getForObject("http://localhost:8080/api/salesTerritoyHistory", Salesterritoryhistory[].class);
		
		List<Salesterritoryhistory> r = (List<Salesterritoryhistory>) salesTerritoyHistoryDelegate.findAll();
		
		assertNotNull(r);
		assertEquals(r.get(0), sth);
		assertEquals(r.get(1), sth2);
		// Verifies behavior happened once
		verify(restTemplate).getForObject("http://localhost:8080/api/salesTerritoyHistory", Salesterritoryhistory[].class);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
	}
	
	@Test
	void findBySalespersonid() {
	Salesterritoryhistory sth = new Salesterritoryhistory();
	sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(10)));
	
	Salesterritoryhistory sth2 = new Salesterritoryhistory();
	sth2.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
	
	ArrayList<Salesterritoryhistory> list = new ArrayList<Salesterritoryhistory>();
	list.add(sth);
	list.add(sth2);
	
	Salesterritoryhistory[] array = {sth,sth2};
	Integer salespersonid = 12;
	
	Mockito.doReturn(array).when(restTemplate).getForObject("http://localhost:8080/api/salesTerritoyHistory/findBySalespersonid/"+salespersonid, Salesterritoryhistory[].class);
	
	List<Salesterritoryhistory> r = (List<Salesterritoryhistory>) salesTerritoyHistoryDelegate.findBySalespersonid(salespersonid);
	
	assertNotNull(r);
	assertEquals(r.get(0), sth);
	assertEquals(r.get(1), sth2);
	// Verifies behavior happened once
	verify(restTemplate).getForObject("http://localhost:8080/api/salesTerritoyHistory/findBySalespersonid/"+salespersonid, Salesterritoryhistory[].class);
	
	// asserts that during the test, there are no other
	 verifyNoMoreInteractions(restTemplate);
	}
	
	@Test
	void findBySalesquota() {
		Salesterritoryhistory sth = new Salesterritoryhistory();
		sth.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(10)));
		
		Salesterritoryhistory sth2 = new Salesterritoryhistory();
		sth2.setModifieddate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
		
		ArrayList<Salesterritoryhistory> list = new ArrayList<Salesterritoryhistory>();
		list.add(sth);
		list.add(sth2);
		
		Salesterritoryhistory[] array = {sth,sth2};
		Integer salesterritoryid = 12;
		
		Mockito.doReturn(array).when(restTemplate).getForObject("http://localhost:8080/api/salesTerritoyHistory/findBySalesterritoryid/"+salesterritoryid, Salesterritoryhistory[].class);
		
		List<Salesterritoryhistory> r = (List<Salesterritoryhistory>) salesTerritoyHistoryDelegate.findBySalesterritoryid(salesterritoryid);
		
		assertNotNull(r);
		assertEquals(r.get(0), sth);
		assertEquals(r.get(1), sth2);
		// Verifies behavior happened once
		verify(restTemplate).getForObject("http://localhost:8080/api/salesTerritoyHistory/findBySalesterritoryid/"+salesterritoryid, Salesterritoryhistory[].class);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
	}

}
