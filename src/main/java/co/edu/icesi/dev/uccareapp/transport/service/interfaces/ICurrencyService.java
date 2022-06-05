package co.edu.icesi.dev.uccareapp.transport.service.interfaces;

import java.util.Optional;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Currency;

public interface ICurrencyService {

	public void save(Currency c);
	public void edit(Currency c); 
	public void delete(String id);
	public Optional<Currency> findByID(String id);
	public Iterable<Currency> findAll();
}
