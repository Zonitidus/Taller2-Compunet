package co.edu.icesi.dev.uccareapp.transport.delegate;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salespersonquotahistory;

@Component
public class SalesPersonQuotaHistoryDelegate implements ISalesPersonQuotaHistoryDelegate{

	private RestTemplate restTemplate;
	
	@Autowired
	public SalesPersonQuotaHistoryDelegate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Override
	public Salespersonquotahistory save(Salespersonquotahistory spqh) {
		// TODO Auto-generated method stub
		return restTemplate.postForObject("http://localhost:8080/salesPersonQuotaHistory/add", spqh, Salespersonquotahistory.class);
	}

	@Override
	public void edit(Salespersonquotahistory spqh) {
		// TODO Auto-generated method stub
		restTemplate.put("http://localhost:8080//salesPersonQuotaHistory/update", spqh);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		restTemplate.delete("http://localhost:8080/salesPersonQuotaHistory/delete/"+id);
	}

	@Override
	public Salespersonquotahistory findByID(Integer id) {
		// TODO Auto-generated method stub
		return restTemplate.getForObject("http://localhost:8080/salesPersonQuotaHistory/"+id, Salespersonquotahistory.class);
	}

	@Override
	public Iterable<Salespersonquotahistory> findAll() {
		// TODO Auto-generated method stub
		Salespersonquotahistory[] spqh = restTemplate.getForObject("http://localhost:8080/salesPersonQuotaHistory", Salespersonquotahistory[].class);
		return Arrays.asList(spqh);
	}

	@Override
	public Iterable<Salespersonquotahistory> findBySalespersonid(Integer salespersonid) {
		// TODO Auto-generated method stub
		Salespersonquotahistory[] spqh = restTemplate.getForObject("http://localhost:8080/salesPersonQuotaHistory/findBySalesperson/"+salespersonid, Salespersonquotahistory[].class);
		return Arrays.asList(spqh);
	}

	@Override
	public Iterable<Salespersonquotahistory> findBySalesquota(BigDecimal salesquota) {
		// TODO Auto-generated method stub
		Salespersonquotahistory[] spqh = restTemplate.getForObject("http://localhost:8080/salesPersonQuotaHistory/findBySalesquota/"+salesquota, Salespersonquotahistory[].class);
		return Arrays.asList(spqh);
	}

}
