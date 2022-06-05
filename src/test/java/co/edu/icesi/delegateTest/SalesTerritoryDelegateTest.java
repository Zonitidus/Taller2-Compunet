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
import co.edu.icesi.dev.uccareapp.transport.delegate.SalesTerritoryDelegate;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TallerunoApplication.class)
public class SalesTerritoryDelegateTest {

	@Mock
	private RestTemplate restTemplate;
	
	private SalesTerritoryDelegate salesTerritoryDelegate;
	
	@BeforeEach
	public void  Setup1() {
		salesTerritoryDelegate = new SalesTerritoryDelegate(restTemplate);
	}
	
	@Test
	void save() {
		

		Salesterritory st = new Salesterritory();
		st.setName("London");
		st.setCountryregioncode("UK");
		
		when(restTemplate.postForObject("http://localhost:8080/api/salesterritory/add", st, Salesterritory.class)).thenReturn(null);
		
		assertDoesNotThrow(() -> this.salesTerritoryDelegate.save(st));
		
		// Verifies behavior happened once
		verify(restTemplate).postForObject("http://localhost:8080/api/salesterritory/add", st, Salesterritory.class);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
	}
	
	@Test
	void edit() {
		
		Salesterritory st = new Salesterritory();
		st.setName("London");
		st.setCountryregioncode("UK");
		
		Mockito.doNothing().when(restTemplate).put("http://localhost:8080/api/salesterritory/update", st);
		
		this.salesTerritoryDelegate.edit(st);
		
		// Verifies behavior happened once
		verify(restTemplate).put("http://localhost:8080/api/salesterritory/update", st);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
	}
	
	@Test
	void findByID() {

		Salesterritory st = new Salesterritory();
		st.setName("London");
		st.setCountryregioncode("UK");
		
		int id = 1;
		
		Mockito.doReturn(st).when(restTemplate).getForObject("http://localhost:8080/api/salesterritory/"+id, Salesterritory.class);
		
		Salesterritory r = salesTerritoryDelegate.findById(id).get();
		
		assertNotNull(r);
		assertEquals(r.getName(), st.getName());
		assertEquals(r.getCountryregioncode(), st.getCountryregioncode());
		
		// Verifies behavior happened once
		verify(restTemplate).getForObject("http://localhost:8080/api/salesterritory/"+id, Salesterritory.class);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
	}
	
	@Test
	void findAll() {
		
		Salesterritory st = new Salesterritory();
		st.setName("London");
		st.setCountryregioncode("UK");
		
		Salesterritory st2 = new Salesterritory();
		st.setName("Miami");
		st.setCountryregioncode("US");
		
		ArrayList<Salesterritory> list = new ArrayList<Salesterritory>();
		list.add(st);
		list.add(st2);
		
		Salesterritory[] array = {st,st2};
		
		Mockito.doReturn(array).when(restTemplate).getForObject("http://localhost:8080/api/salesterritory/", Salesterritory[].class);
		
		List<Salesterritory> r = (List<Salesterritory>) salesTerritoryDelegate.findAll();
		
		assertNotNull(r);
		assertEquals(r.get(0), st);
		assertEquals(r.get(1), st2);
		// Verifies behavior happened once
		verify(restTemplate).getForObject("http://localhost:8080/api/salesterritory/", Salesterritory[].class);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
	}
	
	@Test
	void customQuery() {
		
		//1
		Salesterritory st = new Salesterritory();
		st.setName("London");
		st.setCountryregioncode("UK");
		
		//2
		Salesterritory st2 = new Salesterritory();
		st2.setName("Miami");
		st2.setCountryregioncode("US");
		
		//3
		Salesterritory st3 = new Salesterritory();
		st3.setName("Tampa");
		st3.setCountryregioncode("US");
		
		Salesperson sp = new Salesperson();
		sp.setCommissionpct(new BigDecimal(0.5));
		sp.setSalesquota(new BigDecimal(1500));
		sp.setSalesterritory(st);
		
		Salesperson sp2 = new Salesperson();
		sp2.setCommissionpct(new BigDecimal(0.75));
		sp2.setSalesquota(new BigDecimal(500));
		sp2.setSalesterritory(st);
		
		Salesperson sp3 = new Salesperson();
		sp3.setCommissionpct(new BigDecimal(0.75));
		sp3.setSalesquota(new BigDecimal(1500));
		sp3.setSalesterritory(st3);
		
		ArrayList<Salesterritory> list = new ArrayList<Salesterritory>();
		list.add(st);
		list.add(st2);
		
		Salesterritory[] array = {st,st3};
		
		Mockito.doReturn(array).when(restTemplate).getForObject(
				"http://localhost:8080/api/salesterritory/customquery/", Salesterritory[].class);
			
		List<Salesterritory> r = (List<Salesterritory>) salesTerritoryDelegate.customQuery();
		
		assertNotNull(r);
		assertEquals(r.get(0), st);
		assertEquals(r.get(1), st3);
		// Verifies behavior happened once
		verify(restTemplate).getForObject(
				"http://localhost:8080/api/salesterritory/customquery/", Salesterritory[].class);
		
		// asserts that during the test, there are no other
		 verifyNoMoreInteractions(restTemplate);
	}
}