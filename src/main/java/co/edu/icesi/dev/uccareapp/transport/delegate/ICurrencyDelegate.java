package co.edu.icesi.dev.uccareapp.transport.delegate;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Currency;

public interface ICurrencyDelegate {

	public void save(Currency c);
	public void edit(Currency c); 
	public void delete(String id);
	public Currency findByID(String id);
	public Iterable<Currency> findAll();
}
