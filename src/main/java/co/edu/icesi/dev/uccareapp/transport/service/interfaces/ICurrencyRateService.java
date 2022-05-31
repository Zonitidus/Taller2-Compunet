package co.edu.icesi.dev.uccareapp.transport.service.interfaces;

import java.util.Optional;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Currencyrate;


public interface ICurrencyRateService {
	public void save(Currencyrate spqh);
	public void edit(Currencyrate spqh); 
	public void delete(Integer id);
	public Optional<Currencyrate> findByID(Integer id);
	public Iterable<Currencyrate> findAll();
}
