package co.edu.icesi.dev.uccareapp.transport.daos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;

@Repository
@Scope("singleton")
public class SalespersonDAO implements ISalespersonDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void save(Salesperson entity) {
		entityManager.persist(entity);
	}

	@Override
	public void update(Salesperson entity) {
		entityManager.merge(entity);
	}

	@Override
	public Optional<Salesperson> findById(Integer businessentityid) {

		return Optional.of(entityManager.find(Salesperson.class, businessentityid));
	}

	@Override
	public Iterable<Salesperson> findAll() {
		Query query = entityManager.createQuery("SELECT sp FROM Salesperson sp");
		return query.getResultList();
	}

	@Override
	public Iterable<Salesperson> findByTerritoryid(Integer territoryId) {
		Query query = entityManager
				.createQuery("SELECT sp FROM Salesperson sp WHERE sp.salesterritory.territoryid = :territoryid");
		query.setParameter("territoryid", territoryId);
		return query.getResultList();
	}

	@Override
	public Iterable<Salesperson> findBySalesquota(BigDecimal salesquota) {
		Query query = entityManager.createQuery("SELECT sp FROM Salesperson sp WHERE sp.salesquota = :salesquota");
		query.setParameter("salesquota", salesquota);
		return query.getResultList();
	}

	@Override
	public Iterable<Salesperson> findByCommissionpct(BigDecimal commissionpct) {
		Query query = entityManager.createQuery("SELECT sp FROM Salesperson sp WHERE sp.commissionpct = :commissionpct");
		query.setParameter("commissionpct", commissionpct);
		return query.getResultList();
	}

	@Override
	public Iterable<Salesperson> customQuery(Salesterritory salesterritory, Date minDate, Date maxDate) {
		/*
		 * La(s) personas vendedoras (s) con sus datos y la cantidad de territorios de
		 * venta que ha tenido (incluyendo el actual), ordenados por cuota de ventas.
		 * 
		 * Recibe como parámetro un territorio de ventas, un rango de fechas
		 * 
		 * Retornan todas las personas vendedoras que cumplen con tener al menos un
		 * encabezado de orden de compra para el territorio en el rango de fechas
		 * establecido
		 */

		Query query = entityManager.createQuery(
				"SELECT sp FROM Salesperson sp "
				+ "WHERE (SELECT COUNT(sth) FROM Salesterritoryhistory sth "
				+ "WHERE sth MEMBER OF sp.salespterritoryhisotries "
				+ "AND sth.startdate>=minDate "
				+ "AND sth.enddate>=maxDate) >= 1 "
				+ "AND stId = sp.salesterritory.territoryid"
				+ "ORDER BY sp.salesquota ASC");

		query.setParameter("stId", salesterritory.getTerritoryid());
		query.setParameter("minDate", minDate);
		query.setParameter("maxDate", maxDate);

		return query.getResultList();
	}
}
