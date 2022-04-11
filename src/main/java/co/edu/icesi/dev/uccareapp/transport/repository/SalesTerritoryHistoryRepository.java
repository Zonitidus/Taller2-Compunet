package co.edu.icesi.dev.uccareapp.transport.repository;

import org.springframework.data.repository.CrudRepository;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritoryhistory;
import co.edu.icesi.dev.uccareapp.transport.model.sales.SalesterritoryhistoryPK;

import org.springframework.stereotype.Repository;

public interface SalesTerritoryHistoryRepository extends CrudRepository<Salesterritoryhistory, Integer>{

}
