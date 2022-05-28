package co.edu.icesi.dev.uccareapp.transport.delegate;

import java.util.Optional;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritoryhistory;

public interface ISalesTerritoyHistoryDelegate {
	public void save(Salesterritoryhistory sth);
	public void edit(Salesterritoryhistory sth);
	public void delete(Integer id);
	public Salesterritoryhistory findById(Integer id);
	public Iterable<Salesterritoryhistory> findAll();
	public Iterable<Salesterritoryhistory> findBySalespersonid(Integer salespersonid);
	public Iterable<Salesterritoryhistory> findBySalesterritoryid(Integer salesterritoryid);
}
