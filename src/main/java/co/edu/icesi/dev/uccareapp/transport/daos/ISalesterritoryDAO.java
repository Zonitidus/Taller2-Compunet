package co.edu.icesi.dev.uccareapp.transport.daos;

import java.util.Optional;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;

public interface ISalesterritoryDAO {

	public void save(Salesterritory entity);
	public void update(Salesterritory entity);
	public Optional<Salesterritory> findById(Integer id);
	public Iterable<Salesterritory> findAll();
	
	public Iterable<Salesterritory> customQuery();
}
