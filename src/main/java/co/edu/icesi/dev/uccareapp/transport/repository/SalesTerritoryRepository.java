package co.edu.icesi.dev.uccareapp.transport.repository;

import org.springframework.data.repository.CrudRepository;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import org.springframework.stereotype.Repository;

public interface SalesTerritoryRepository extends CrudRepository<Salesterritory, Integer>{

}
