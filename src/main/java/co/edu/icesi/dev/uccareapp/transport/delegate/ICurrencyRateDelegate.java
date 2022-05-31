package co.edu.icesi.dev.uccareapp.transport.delegate;

import java.util.Optional;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Currencyrate;

public interface ICurrencyRateDelegate {
	public void save(Currencyrate spqh);
	public void edit(Currencyrate spqh); 
	public void delete(Integer id);
	public Currencyrate findByID(Integer id);
	public Iterable<Currencyrate> findAll();
}
