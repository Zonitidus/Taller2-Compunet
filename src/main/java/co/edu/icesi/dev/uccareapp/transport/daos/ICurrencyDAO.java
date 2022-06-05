package co.edu.icesi.dev.uccareapp.transport.daos;

import java.util.Optional;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Currency;

public interface ICurrencyDAO {
	
	public void save(Currency entity);

	public void update(Currency entity);

	public void delete(Currency entity);

	public Optional<Currency> findById(String id);

	public Iterable<Currency> findAll();
}
