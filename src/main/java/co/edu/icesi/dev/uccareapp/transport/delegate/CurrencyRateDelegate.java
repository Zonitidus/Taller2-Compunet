package co.edu.icesi.dev.uccareapp.transport.delegate;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Currencyrate;

public class CurrencyRateDelegate implements ICurrencyRateDelegate{

private RestTemplate restTemplate;
	
	@Autowired
	public CurrencyRateDelegate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Override
	public void save(Currencyrate spqh) {
		// TODO Auto-generated method stub
		restTemplate.postForObject("http://localhost:8080/currencyRate/add", spqh, Currencyrate.class);
	}

	@Override
	public void edit(Currencyrate spqh) {
		// TODO Auto-generated method stub
		restTemplate.put("http://localhost:8080/currencyRate/update", spqh);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		restTemplate.delete("http://localhost:8080/currencyRate/delete/"+id);
	}

	@Override
	public Currencyrate findByID(Integer id) {
		// TODO Auto-generated method stub
		return restTemplate.getForObject("http://localhost:8080/currencyRate/"+id, Currencyrate.class);
	}

	@Override
	public Iterable<Currencyrate> findAll() {
		// TODO Auto-generated method stub
		Currencyrate[] cr = restTemplate.getForObject("http://localhost:8080/currencyRate", Currencyrate[].class);
		return Arrays.asList(cr);
	}

}
