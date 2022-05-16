package co.edu.icesi.dev.uccareapp.transport.daos;

import java.util.Optional;

import co.edu.icesi.dev.uccareapp.transport.model.person.Countryregion;

public interface ICountryRegionDAO {

	public void save(Countryregion countryregion);
	public Optional<Countryregion> findById(String countryregioncode);
	public  Iterable<Countryregion> findAll();
}
