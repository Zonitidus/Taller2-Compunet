package co.edu.icesi.dev.uccareapp.transport.service.interfaces;

import java.util.Optional;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritoryhistory;

public interface SalesTerritoyHistoryService {

	public void save(Salesterritoryhistory sth);
	public void edit(Salesterritoryhistory sth);
	public void delete(Integer id);
	public Optional<Salesterritoryhistory> findById(Integer id);
	public Iterable<Salesterritoryhistory> findAll();
	public Iterable<Salesterritoryhistory> findBySalespersonid(Integer salespersonid);
	public Iterable<Salesterritoryhistory> findBySalesterritoryid(Integer salesterritoryid);
}
