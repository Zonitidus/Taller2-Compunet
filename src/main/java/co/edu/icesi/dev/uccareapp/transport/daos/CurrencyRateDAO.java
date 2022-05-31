package co.edu.icesi.dev.uccareapp.transport.daos;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Currencyrate;

@Repository
@Scope("singleton")
public class CurrencyRateDAO implements ICurrencyRateDAO{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(Currencyrate entity) {
		// TODO Auto-generated method stub
		entityManager.persist(entity);
	}

	@Override
	public void update(Currencyrate entity) {
		// TODO Auto-generated method stub
		entityManager.merge(entity);
	}

	@Override
	public void delete(Currencyrate entity) {
		// TODO Auto-generated method stub
		entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
	}

	@Override
	public Optional<Currencyrate> findById(Integer id) {
		// TODO Auto-generated method stub
		return Optional.of(entityManager.find(Currencyrate.class, id));
	}

	@Override
	public Iterable<Currencyrate> findAll() {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery("SELECT cr FROM Currencyrate cr");
		return query.getResultList();
	}

}
