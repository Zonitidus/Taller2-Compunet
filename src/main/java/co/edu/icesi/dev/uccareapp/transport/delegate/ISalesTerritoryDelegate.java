package co.edu.icesi.dev.uccareapp.transport.delegate;

import java.util.Optional;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;

public interface ISalesTerritoryDelegate {

	public void save(Salesterritory st);
	public void edit(Salesterritory st);
	
	public Optional<Salesterritory> findById(Integer id);
	public Iterable<Salesterritory> findAll();
	public Iterable<Salesterritory> customQuery();
}
