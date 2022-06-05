package co.edu.icesi.dev.uccareapp.transport.delegate;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Currency;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Currency;

@Component
public class CurrencyDelegate implements ICurrencyDelegate {

	private RestTemplate restTemplate;

	@Autowired
	public CurrencyDelegate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Override
	public void save(Currency c) {
		// TODO Auto-generated method stub
		this.restTemplate.postForObject("http://localhost:8080/api/currency/add", c, Currency.class);
	}

	@Override
	public void edit(Currency c) {
		// TODO Auto-generated method stub
		this.restTemplate.put("http://localhost:8080/api/currency/update", c);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		this.restTemplate.delete("http://localhost:8080/api/currency/delete/"+id);
	}

	@Override
	public Currency findByID(String id) {
		// TODO Auto-generated method stub
		return this.restTemplate.getForObject("http://localhost:8080/api/currency/"+id, Currency.class);
	}

	@Override
	public Iterable<Currency> findAll() {
		// TODO Auto-generated method stub
		Currency[] cr = this.restTemplate.getForObject("http://localhost:8080/api/currency", Currency[].class);
		return Arrays.asList(cr);
	}

}
