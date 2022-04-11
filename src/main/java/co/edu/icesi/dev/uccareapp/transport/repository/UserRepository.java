package co.edu.icesi.dev.uccareapp.transport.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.edu.icesi.dev.uccareapp.transport.model.UserApp;
import co.edu.icesi.dev.uccareapp.transport.model.UserType;

public interface UserRepository extends CrudRepository<UserApp, Long> {

	List<UserApp> findByName(String name);
	
	List<UserApp> findByType(UserType patient);
	
	List<UserApp> findByUsername(String username);

}
