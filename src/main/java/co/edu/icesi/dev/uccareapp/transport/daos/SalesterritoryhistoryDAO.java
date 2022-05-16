package co.edu.icesi.dev.uccareapp.transport.daos;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritoryhistory;

@Repository
@Scope("singleton")
public class SalesterritoryhistoryDAO implements ISalesterritoryHistoryDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(Salesterritoryhistory entity) {
		entityManager.persist(entity);
	}

	@Override
	public void update(Salesterritoryhistory entity) {
		entityManager.merge(entity);
	}

	@Override
	public Optional<Salesterritoryhistory> findById(Integer id) {
		return Optional.of(entityManager.find(Salesterritoryhistory.class, id));
	}

	@Override
	public Iterable<Salesterritoryhistory> findAll() {
		Query query = entityManager.createQuery("SELECT sth FROM Salesterritoryhistory sth");
		return query.getResultList();
	}

	@Override
	public Iterable<Salesterritoryhistory> findBySalespersonid(Integer salespersonid) {
		Query query = entityManager
				.createQuery("SELECT sth FROM Salesterritoryhistory sth WHERE sth.salesperson.businessentityid = :salespersonid");
		query.setParameter("salespersonid", salespersonid);
		return query.getResultList();
	}

	@Override
	public Iterable<Salesterritoryhistory> findBySalesterritoryid(Integer salesterritoryid) {
		Query query = entityManager
				.createQuery("SELECT sth FROM Salesterritoryhistory sth WHERE sth.salesterritory.territoryid = :salesterritoryid");
		query.setParameter("salesterritoryid", salesterritoryid);
		return query.getResultList();
	}

}
