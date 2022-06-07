package co.edu.icesi.dev.uccareapp.transport.daos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;

@Repository
@Scope("singleton")
public class SalespersonDAO implements ISalespersonDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
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
		Query query = entityManager
				.createQuery("SELECT sp FROM Salesperson sp WHERE sp.commissionpct = :commissionpct");
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

		String jpql = "SELECT sp,(SIZE(sp.salesterritoryhistories)) AS sthcount "
				+ "FROM Salesperson sp, Salesterritoryhistory sth "
				+ "WHERE sth MEMBER OF sp.salesterritoryhistories " + "AND sp.salesterritory.territoryid= :stId "
				+ "AND sth.modifieddate>= :startdate " + "AND sth.enddate<= :enddate "
				+ "GROUP BY sp.businessentityid " + "ORDER BY sp.salesquota";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		query.setParameter("stId", salesterritory.getTerritoryid());
		query.setParameter("startdate", minDate);
		query.setParameter("enddate", maxDate);

		Map<Salesperson, Integer> pre = query.getResultList().stream()
		.collect(
			    Collectors.toMap(
			        ob -> ((Salesperson) ob[0]),
			        ob -> ((Integer) ob[1])
			    )
			);
		
		
		Object[] sps = pre.keySet().toArray();
		
		ArrayList<Salesperson> salespersons = new ArrayList<Salesperson>();
		
		for(int i = 0; i < sps.length; i++) {
			
			Salesperson sptemp = ((Salesperson) sps[i]);
			sptemp.setCustomQueryCount(pre.get(sptemp));
			salespersons.add(sptemp);
		}
		
		
		return salespersons;
	}
}
