package co.edu.icesi.dev.uccareapp.transport.daos;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.edu.icesi.dev.uccareapp.transport.model.person.Businessentity;

public class BusinessentityDAO implements IBusinessentityDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(Businessentity businessentity) {
		this.entityManager.persist(businessentity);
	}

	@Override
	public Optional<Businessentity> findById(Integer id) {
		return Optional.of(this.entityManager.find(Businessentity.class, id));
	}

	@Override
	public Iterable<Businessentity> findAll() {
		String jpql = "SELECT be FROM Businessentity be";
		return 	this.entityManager.createQuery(jpql).getResultList();
	}

}
