package co.edu.icesi.dev.uccareapp.transport.daos;

import java.util.Arrays;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Currency;

@Repository
@Scope("singleton")
public class CurrencyDAO implements ICurrencyDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(Currency entity) {
		this.entityManager.persist(entity);
	}

	@Override
	public void update(Currency entity) {
		// TODO Auto-generated method stub
		this.entityManager.merge(entity);
	}

	@Override
	public void delete(Currency entity) {
		// TODO Auto-generated method stub
		this.entityManager.remove(this.entityManager.contains(entity) ? entity : this.entityManager.merge(entity));
	}

	@Override
	public Optional<Currency> findById(String id) {
		// TODO Auto-generated method stub
		return Optional.of(this.entityManager.find(Currency.class, id));
	}

	@Override
	public Iterable<Currency> findAll() {
		// TODO Auto-generated method stub
		return this.entityManager.createQuery("SELECT c FROM Currency c").getResultList();
	}

}
