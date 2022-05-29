package co.edu.icesi.dev.uccareapp.transport.service.interfaces;

import java.util.Optional;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;

public interface SalesTerritoryService {

	public Salesterritory save(Salesterritory st);
	public Salesterritory edit(Salesterritory st);
	
	public Optional<Salesterritory> findById(Integer id);
	public Iterable<Salesterritory> findAll();
	public Iterable<Salesterritory> customQuery();
}
