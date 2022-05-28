package co.edu.icesi.dev.uccareapp.transport.daos;

import java.math.BigDecimal;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salespersonquotahistory;

@Repository
@Scope("singleton")
public class SalespersonquotahistoryDAO implements ISalespersonquotahistoryDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(Salespersonquotahistory entity) {
		entityManager.persist(entity);
	}

	@Override
	public void update(Salespersonquotahistory entity) {
		entityManager.merge(entity);
	}

	@Override
	public Optional<Salespersonquotahistory> findById(Integer id) {
		return Optional.of(entityManager.find(Salespersonquotahistory.class, id));
	}

	@Override
	public Iterable<Salespersonquotahistory> findAll() {
		Query query = entityManager.createQuery("SELECT spqh FROM Salespersonquotahistory spqh");
		return query.getResultList();
	}

	@Override
	public Iterable<Salespersonquotahistory> findBySalespersonid(Integer salespersonid) {
		Query query = entityManager
				.createQuery("SELECT spqh FROM Salespersonquotahistory spqh WHERE spqh.salesperson.businessentityid = :salespersonid");
		query.setParameter("salespersonid", salespersonid);
		return query.getResultList();
	}

	@Override
	public Iterable<Salespersonquotahistory> findBySalesquota(BigDecimal salesquota) {
		Query query = entityManager
				.createQuery("SELECT spqh FROM Salespersonquotahistory spqh WHERE spqh.salesquota = :salesquota");
		query.setParameter("salesquota", salesquota);
		return query.getResultList();
	}

	@Override
	public void delete(Salespersonquotahistory entity) {
		// TODO Auto-generated method stub
		entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
	}

}
