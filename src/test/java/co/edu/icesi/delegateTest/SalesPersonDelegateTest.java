package co.edu.icesi.delegateTest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.dev.uccareapp.transport.TallerunoApplication;
import co.edu.icesi.dev.uccareapp.transport.delegate.SalesPersonDelegate;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TallerunoApplication.class)
public class SalesPersonDelegateTest {

	@Mock
	private RestTemplate restTemplate;
	
	private SalesPersonDelegate salesPersonDelegate;
	
	@BeforeEach
	public void  Setup1() {
		salesPersonDelegate = new SalesPersonDelegate(restTemplate);
	}
	
	@Test
	void save() {
		
		Salesperson sp = new Salesperson();
		sp.setCommissionpct(new BigDecimal(0.5));
		sp.setSalesquota(new BigDecimal(100));

		Salesterritory st = new Salesterritory();
		st.setTerritoryid(1);
		sp.setSalesterritory(st);
		
		when(restTemplate.postForObject("http://localhost:8080/api/salesperson/add", sp, Salesperson.class)).thenReturn(null);
		
		assertDoesNotThrow(() -> this.salesPersonDelegate.save(sp));
		
		// Verifies behavior happened once
		verify(restTemplate).postForObject("http://localhost:8080/api/salesperson/add", sp, Salesperson.class);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
	}
	
	@Test
	void edit() {
		
		Salesperson sp = new Salesperson();
		sp.setCommissionpct(new BigDecimal(0.5));
		sp.setSalesquota(new BigDecimal(100));

		Salesterritory st = new Salesterritory();
		st.setTerritoryid(1);
		sp.setSalesterritory(st);
		
		Mockito.doNothing().when(restTemplate).put("http://localhost:8080/api/salesperson/update", sp);
		
		this.salesPersonDelegate.edit(sp);
		
		// Verifies behavior happened once
		verify(restTemplate).put("http://localhost:8080/api/salesperson/update", sp);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
	}
	
	@Test
	void findByID() {
		Salesperson sp = new Salesperson();
		sp.setCommissionpct(new BigDecimal(0.5));
		sp.setSalesquota(new BigDecimal(100));

		Salesterritory st = new Salesterritory();
		st.setTerritoryid(1);
		sp.setSalesterritory(st);
		
		int id = 1;
		
		Mockito.doReturn(sp).when(restTemplate).getForObject("http://localhost:8080/api/salesperson/"+id, Salesperson.class);
		
		Salesperson r = salesPersonDelegate.findById(id).get();
		
		assertNotNull(r);
		assertEquals(r.getCommissionpct(), sp.getCommissionpct());
		assertEquals(r.getSalesquota(), sp.getSalesquota());
		assertEquals(r.getSalesterritory().getTerritoryid(), sp.getSalesterritory().getTerritoryid());
		// Verifies behavior happened once
		verify(restTemplate).getForObject("http://localhost:8080/api/salesperson/"+id, Salesperson.class);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
	}
	
	@Test
	void findAll() {
		
		Salesterritory st = new Salesterritory();
		st.setTerritoryid(1);
		
		Salesperson sp = new Salesperson();
		sp.setCommissionpct(new BigDecimal(0.5));
		sp.setSalesquota(new BigDecimal(100));
		sp.setSalesterritory(st);
		
		Salesperson sp2 = new Salesperson();
		sp2.setCommissionpct(new BigDecimal(0.75));
		sp2.setSalesquota(new BigDecimal(1500));
		sp2.setSalesterritory(st);
		
		ArrayList<Salesperson> list = new ArrayList<Salesperson>();
		list.add(sp);
		list.add(sp2);
		
		Salesperson[] array = {sp,sp2};
		
		Mockito.doReturn(array).when(restTemplate).getForObject("http://localhost:8080/api/salesperson/", Salesperson[].class);
		
		List<Salesperson> r = (List<Salesperson>) salesPersonDelegate.findAll();
		
		assertNotNull(r);
		assertEquals(r.get(0), sp);
		assertEquals(r.get(1), sp2);
		// Verifies behavior happened once
		verify(restTemplate).getForObject("http://localhost:8080/api/salesperson/", Salesperson[].class);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
	}
	
	@Test
	void findBySalesterritoryid() {
		
		Salesterritory st = new Salesterritory();
		st.setTerritoryid(1);
		
		Salesperson sp = new Salesperson();
		sp.setCommissionpct(new BigDecimal(0.5));
		sp.setSalesquota(new BigDecimal(100));
		sp.setSalesterritory(st);
		
		Salesperson sp2 = new Salesperson();
		sp2.setCommissionpct(new BigDecimal(0.75));
		sp2.setSalesquota(new BigDecimal(1500));
		sp2.setSalesterritory(st);
		
		ArrayList<Salesperson> list = new ArrayList<Salesperson>();
		list.add(sp);
		list.add(sp2);
		
		Salesperson[] array = {sp,sp2};
		Integer salesterritoryid = 1;
		
		Mockito.doReturn(array).when(restTemplate).getForObject("http://localhost:8080/api/salesperson/findbyterritory/"+salesterritoryid, Salesperson[].class);
		
		List<Salesperson> r = (List<Salesperson>) salesPersonDelegate.findByTerritoryid(salesterritoryid);
		
		assertNotNull(r);
		assertEquals(r.get(0), sp);
		assertEquals(r.get(1), sp2);
		// Verifies behavior happened once
		verify(restTemplate).getForObject("http://localhost:8080/api/salesperson/findbyterritory/"+salesterritoryid, Salesperson[].class);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
	}
	
	@Test
	void findBySalesquota() {
		
		Salesterritory st = new Salesterritory();
		st.setTerritoryid(1);
		
		Salesperson sp = new Salesperson();
		sp.setCommissionpct(new BigDecimal(0.5));
		sp.setSalesquota(new BigDecimal(100));
		sp.setSalesterritory(st);
		
		Salesperson sp2 = new Salesperson();
		sp2.setCommissionpct(new BigDecimal(0.75));
		sp2.setSalesquota(new BigDecimal(1500));
		sp2.setSalesterritory(st);
		
		ArrayList<Salesperson> list = new ArrayList<Salesperson>();
		list.add(sp);
		list.add(sp2);
		
		Salesperson[] array = {sp,sp2};
		BigDecimal salesquota = new BigDecimal(1);
		
		
		Mockito.doReturn(array).when(restTemplate).getForObject("http://localhost:8080/api/salesperson/findbysalesquota/"+salesquota, Salesperson[].class);
		
		List<Salesperson> r = (List<Salesperson>) salesPersonDelegate.findBySalesquota(salesquota);
		
		assertNotNull(r);
		assertEquals(r.get(0), sp);
		assertEquals(r.get(1), sp2);
		// Verifies behavior happened once
		verify(restTemplate).getForObject("http://localhost:8080/api/salesperson/findbysalesquota/"+salesquota, Salesperson[].class);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
	}
	
	@Test
	void findByCommisionpct() {
		
		Salesterritory st = new Salesterritory();
		st.setTerritoryid(1);
		
		Salesperson sp = new Salesperson();
		sp.setCommissionpct(new BigDecimal(0.5));
		sp.setSalesquota(new BigDecimal(100));
		sp.setSalesterritory(st);
		
		Salesperson sp2 = new Salesperson();
		sp2.setCommissionpct(new BigDecimal(0.75));
		sp2.setSalesquota(new BigDecimal(1500));
		sp2.setSalesterritory(st);
		
		ArrayList<Salesperson> list = new ArrayList<Salesperson>();
		list.add(sp);
		list.add(sp2);
		
		Salesperson[] array = {sp,sp2};
		BigDecimal commission = new BigDecimal(7);
		
		
		Mockito.doReturn(array).when(restTemplate).getForObject("http://localhost:8080/api/salesperson/findbycommissionpct/"+commission, Salesperson[].class);
		
		List<Salesperson> r = (List<Salesperson>) salesPersonDelegate.findByCommissionpct(commission);
		
		assertNotNull(r);
		assertEquals(r.get(0), sp);
		assertEquals(r.get(1), sp2);
		// Verifies behavior happened once
		verify(restTemplate).getForObject("http://localhost:8080/api/salesperson/findbycommissionpct/"+commission, Salesperson[].class);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
	}
	
	@Test
	void customQuery() {
		
		Salesterritory st = new Salesterritory();
		st.setTerritoryid(1);
		
		Salesperson sp = new Salesperson();
		sp.setCommissionpct(new BigDecimal(0.5));
		sp.setSalesquota(new BigDecimal(100));
		sp.setSalesterritory(st);
		
		Salesperson sp2 = new Salesperson();
		sp2.setCommissionpct(new BigDecimal(0.75));
		sp2.setSalesquota(new BigDecimal(1500));
		sp2.setSalesterritory(st);
		
		ArrayList<Salesperson> list = new ArrayList<Salesperson>();
		list.add(sp);
		list.add(sp2);
		
		Salesperson[] array = {sp,sp2};
		
		Date min = Timestamp.valueOf(LocalDateTime.now().minusDays(11));
		Date max = Timestamp.valueOf(LocalDateTime.now().minusDays(5));
		
		Mockito.doReturn(array).when(restTemplate).getForObject("http://localhost:8080/api/salesperson/customquery/"
				+ st + "/start/" + min + "/end/" + max, Salesperson[].class);
			
		List<Salesperson> r = (List<Salesperson>) salesPersonDelegate.customQuery(st, min, max);
		
		assertNotNull(r);
		assertEquals(r.get(0), sp);
		assertEquals(r.get(1), sp2);
		// Verifies behavior happened once
		verify(restTemplate).getForObject("http://localhost:8080/api/salesperson/customquery/"
				+ st + "/start/" + min + "/end/" + max, Salesperson[].class);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
	}
}
