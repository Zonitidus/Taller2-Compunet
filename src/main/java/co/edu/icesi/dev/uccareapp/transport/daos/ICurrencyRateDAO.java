package co.edu.icesi.dev.uccareapp.transport.daos;

import java.util.Optional;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Currencyrate;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salespersonquotahistory;

public interface ICurrencyRateDAO {
	public void save(Currencyrate entity);
	public void update(Currencyrate entity);
	public void delete(Currencyrate entity);
	public Optional<Currencyrate> findById(Integer id);
	public Iterable<Currencyrate> findAll();
}
