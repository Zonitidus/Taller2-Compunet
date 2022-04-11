package co.edu.icesi.dev.uccareapp.transport.repository;

import org.springframework.data.repository.CrudRepository;

import co.edu.icesi.dev.uccareapp.transport.model.person.Countryregion;
import org.springframework.stereotype.Repository;

public interface CountryRegionRepository extends CrudRepository<Countryregion, String>{

}
