package co.edu.icesi.dev.uccareapp.transport.repository;

import org.springframework.data.repository.CrudRepository;

import co.edu.icesi.dev.uccareapp.transport.model.hr.Employee;
import org.springframework.stereotype.Repository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>{

}
