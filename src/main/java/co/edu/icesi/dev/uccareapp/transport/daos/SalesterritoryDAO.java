package co.edu.icesi.dev.uccareapp.transport.daos;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;

@Repository
@Scope("singleton")
public class SalesterritoryDAO implements ISalesterritoryDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(Salesterritory entity) {
		entityManager.persist(entity);
	}

	@Override
	public void update(Salesterritory entity) {
		entityManager.merge(entity);
	}

	@Override
	public Optional<Salesterritory> findById(Integer id) {
		return Optional.of(entityManager.find(Salesterritory.class, id));
	}

	@Override
	public Iterable<Salesterritory> findAll() {
		Query query = entityManager.createQuery("SELECT st FROM Salesterritory st");
		return query.getResultList();
	}

	@Override
	public Iterable<Salesterritory> customQuery() {

		/*Mostrar el listado territorios de ventas para los territorios que tienen al menos dos 
		personas vendedoras con un total de cuota superior a 10000 USD.*/
		
		Query query = entityManager.createQuery(
				"SELECT st FROM Salesterritory st "
				+ "WHERE (SELECT COUNT(sp) FROM Salesperson sp "
				+ "WHERE sp MEMBER OF st.salespersons AND sp.salesquota>10000) > 1) >= 2");
		return query.getResultList();
	}

}
