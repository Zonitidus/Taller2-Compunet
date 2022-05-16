package co.edu.icesi.dev.uccareapp.transport.daos;

import java.math.BigDecimal;
import java.util.Optional;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritoryhistory;

public interface ISalesterritoryHistoryDAO {

	public void save(Salesterritoryhistory entity);
	public void update(Salesterritoryhistory entity);
	public Optional<Salesterritoryhistory> findById(Integer id);
	public Iterable<Salesterritoryhistory> findAll();
	
	public Iterable<Salesterritoryhistory> findBySalespersonid(Integer salespersonid);
	public Iterable<Salesterritoryhistory> findBySalesterritoryid(Integer salesterritoryid);
}
