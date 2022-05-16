package co.edu.icesi.dev.uccareapp.transport.daos;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.edu.icesi.dev.uccareapp.transport.model.person.Businessentity;
import co.edu.icesi.dev.uccareapp.transport.model.person.Countryregion;

public class CountryRegionDAO implements ICountryRegionDAO{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(Countryregion countryregion) {
		this.entityManager.persist(countryregion);
	}

	@Override
	public Optional<Countryregion> findById(String countryregioncode) {
		return Optional.of(this.entityManager.find(Countryregion.class, countryregioncode));
	}

	@Override
	public Iterable<Countryregion> findAll() {
		String jpql = "SELECT cr FROM Countryregion cr";
		return 	entityManager.createQuery(jpql).getResultList();
	}

}
