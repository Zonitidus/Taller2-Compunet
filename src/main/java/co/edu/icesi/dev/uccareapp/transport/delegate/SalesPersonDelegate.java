package co.edu.icesi.dev.uccareapp.transport.delegate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;

@Component
public class SalesPersonDelegate implements ISalesPersonDelegate {

	private RestTemplate restTemplate;

	@Autowired
	public SalesPersonDelegate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public void save(Salesperson sp) {
		// TODO Auto-generated method stub
		this.restTemplate.postForObject("http://localhost:8080/api/salesperson/add", sp, Salesperson.class);
	}

	@Override
	public void edit(Salesperson sp) {
		// TODO Auto-generated method stub
		this.restTemplate.put("http://localhost:8080/api/salesperson/update", sp);
	}

	@Override
	public Optional<Salesperson> findById(Integer id) {
		// TODO Auto-generated method stub
		return Optional
				.of(this.restTemplate.getForObject("http://localhost:8080/api/salesperson/" + id, Salesperson.class));
	}

	@Override
	public Iterable<Salesperson> findAll() {
		// TODO Auto-generated method stub
		return Arrays
				.asList(this.restTemplate.getForObject("http://localhost:8080/api/salesperson/", Salesperson[].class));
	}

	@Override
	public Iterable<Salesperson> findByTerritoryid(Integer territoryId) {
		// TODO Auto-generated method stub
		return Arrays.asList(this.restTemplate.getForObject(
				"http://localhost:8080/api/salesperson/findbyterritory/" + territoryId, Salesperson[].class));
	}

	@Override
	public Iterable<Salesperson> findBySalesquota(BigDecimal salesquota) {
		// TODO Auto-generated method stub
		return Arrays.asList(this.restTemplate.getForObject(
				"http://localhost:8080/api/salesperson/findbysalesquota/" + salesquota, Salesperson[].class));
	}

	@Override
	public Iterable<Salesperson> findByCommissionpct(BigDecimal commissionpct) {
		// TODO Auto-generated method stub
		return Arrays.asList(this.restTemplate.getForObject(
				"http://localhost:8080/api/salesperson/findbycommissionpct/" + commissionpct, Salesperson[].class));
	}

	@Override
	public Iterable<Salesperson> customQuery(Salesterritory salesterritory, Date minDate, Date maxDate) {

		Timestamp strMinDate = new Timestamp(minDate.getTime());
		Timestamp strMaxDate = new Timestamp(maxDate.getTime());

		return Arrays.asList(this.restTemplate.getForObject("http://localhost:8080/api/salesperson/customquery/"
				+ salesterritory.getTerritoryid() + "/" + strMinDate + "/" + strMaxDate, Salesperson[].class));
	}

}
