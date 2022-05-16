package co.edu.icesi.dev.uccareapp.transport.daos;

import java.util.Optional;

import co.edu.icesi.dev.uccareapp.transport.model.person.Businessentity;

public interface IBusinessentityDAO {

	public void save(Businessentity businessentity);
	public Optional<Businessentity> findById(Integer id);
	public  Iterable<Businessentity> findAll();
}
