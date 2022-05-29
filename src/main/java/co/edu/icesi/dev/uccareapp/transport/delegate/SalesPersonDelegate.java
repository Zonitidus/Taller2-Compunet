package co.edu.icesi.dev.uccareapp.transport.delegate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;

public class SalesPersonDelegate implements ISalesPersonDelegate {

	private RestTemplate restTemplate;

	@Autowired
	public SalesPersonDelegate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public void save(Salesperson sp) {
		// TODO Auto-generated method stub
		this.restTemplate.postForObject("http://localhost:8080/salesperson/add", sp, Salesperson.class);
	}

	@Override
	public void edit(Salesperson sp) {
		// TODO Auto-generated method stub
		this.restTemplate.put("http://localhost:8080//salesperson/update", sp);
	}

	@Override
	public Optional<Salesperson> findById(Integer id) {
		// TODO Auto-generated method stub
		return Optional
				.of(this.restTemplate.getForObject("http://localhost:8080//salesperson/" + id, Salesperson.class));
	}

	@Override
	public Iterable<Salesperson> findAll() {
		// TODO Auto-generated method stub
		return Arrays
				.asList(this.restTemplate.getForObject("http://localhost:8080//salesperson/", Salesperson[].class));
	}

	@Override
	public Iterable<Salesperson> findByTerritoryid(Integer territoryId) {
		// TODO Auto-generated method stub
		return Arrays.asList(this.restTemplate.getForObject(
				"http://localhost:8080//salesperson/findbyterritory/" + territoryId, Salesperson[].class));
	}

	@Override
	public Iterable<Salesperson> findBySalesquota(BigDecimal salesquota) {
		// TODO Auto-generated method stub
		return Arrays.asList(this.restTemplate.getForObject(
				"http://localhost:8080//salesperson/findbysalesquota/" + salesquota, Salesperson[].class));
	}

	@Override
	public Iterable<Salesperson> findByCommissionpct(BigDecimal commissionpct) {
		// TODO Auto-generated method stub
		return Arrays.asList(this.restTemplate.getForObject(
				"http://localhost:8080//salesperson/findbycommissionpct/" + commissionpct, Salesperson[].class));
	}

	@Override
	public Iterable<Salesperson> customQuery(Salesterritory salesterritory, Date minDate, Date maxDate) {
		// TODO Auto-generated method stub
		return Arrays.asList(this.restTemplate.getForObject("http://localhost:8080//salesperson/customquery/"
				+ salesterritory + "/start/" + minDate + "/end/" + maxDate, Salesperson[].class));
	}

}
