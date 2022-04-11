package co.edu.icesi.dev.uccareapp.transport.service.interfaces;

import java.util.Optional;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;

public interface SalesPersonService {

	public Salesperson save(Salesperson sp);
	public Salesperson edit(Salesperson sp);
	public Optional<Salesperson> findById(Integer id);
	public Iterable<Salesperson> findAll();
}
