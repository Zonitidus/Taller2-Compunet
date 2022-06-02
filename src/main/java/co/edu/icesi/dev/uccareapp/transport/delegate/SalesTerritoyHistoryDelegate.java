package co.edu.icesi.dev.uccareapp.transport.delegate;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritoryhistory;

@Component
public class SalesTerritoyHistoryDelegate implements ISalesTerritoyHistoryDelegate{

	private RestTemplate restTemplate;
	
	@Autowired
	public SalesTerritoyHistoryDelegate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Override
	public void save(Salesterritoryhistory sth) {
		// TODO Auto-generated method stub
		restTemplate.postForObject("http://localhost:8080/api/salesTerritoyHistory/add", sth, Salesterritoryhistory.class);
	}

	@Override
	public void edit(Salesterritoryhistory sth) {
		// TODO Auto-generated method stub
		restTemplate.put("http://localhost:8080/api/salesTerritoyHistory/update", sth);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		restTemplate.delete("http://localhost:8080/api/salesTerritoyHistory/delete/"+id);
	}

	@Override
	public Salesterritoryhistory findById(Integer id) {
		// TODO Auto-generated method stub
		return restTemplate.getForObject("http://localhost:8080/api/salesTerritoyHistory/"+id, Salesterritoryhistory.class);
	}

	@Override
	public Iterable<Salesterritoryhistory> findAll() {
		// TODO Auto-generated method stub
		Salesterritoryhistory[] sth = restTemplate.getForObject("http://localhost:8080/api/salesTerritoyHistory", Salesterritoryhistory[].class);
		return Arrays.asList(sth);
	}

	@Override
	public Iterable<Salesterritoryhistory> findBySalespersonid(Integer salespersonid) {
		// TODO Auto-generated method stub
		Salesterritoryhistory[] sth = restTemplate.getForObject("http://localhost:8080/api/salesTerritoyHistory/findBySalespersonid/"+salespersonid, Salesterritoryhistory[].class);
		return Arrays.asList(sth);
	}

	@Override
	public Iterable<Salesterritoryhistory> findBySalesterritoryid(Integer salesterritoryid) {
		// TODO Auto-generated method stub
		Salesterritoryhistory[] sth = restTemplate.getForObject("http://localhost:8080/api/salesTerritoyHistory/findBySalesterritoryid/"+salesterritoryid, Salesterritoryhistory[].class);
		return Arrays.asList(sth);
	}

}
