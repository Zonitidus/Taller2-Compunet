package co.edu.icesi.dev.uccareapp.transport.repository;

import org.springframework.data.repository.CrudRepository;

import co.edu.icesi.dev.uccareapp.transport.model.person.Businessentity;
import org.springframework.stereotype.Repository;

public interface BusinessEntittyRepository extends CrudRepository<Businessentity, Integer>{

}
