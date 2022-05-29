package co.edu.icesi.dev.uccareapp.transport.delegate;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;

public class SalesTerritoryDelegate implements ISalesTerritoryDelegate {

	private RestTemplate restTemplate;

	@Autowired
	public SalesTerritoryDelegate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public void save(Salesterritory st) {
		this.restTemplate.postForObject("http://localhost:8080/salesterritory/add", st, Salesterritory.class);
	}

	@Override
	public void edit(Salesterritory st) {
		this.restTemplate.put("http://localhost:8080/salesterritory/update", st);

	}

	@Override
	public Optional<Salesterritory> findById(Integer id) {
		// TODO Auto-generated method stub
		return Optional
				.of(this.restTemplate.getForObject("http://localhost:8080/salesterritory/" + id, Salesterritory.class));
	}

	@Override
	public Iterable<Salesterritory> findAll() {
		// TODO Auto-generated method stub
		return Arrays.asList(
				this.restTemplate.getForObject("http://localhost:8080/salesterritory/", Salesterritory[].class));
	}

	@Override
	public Iterable<Salesterritory> customQuery() {
		// TODO Auto-generated method stub
		return Arrays.asList(this.restTemplate.getForObject("http://localhost:8080/salesterritory/customquery",
				Salesterritory[].class));
	}

}
