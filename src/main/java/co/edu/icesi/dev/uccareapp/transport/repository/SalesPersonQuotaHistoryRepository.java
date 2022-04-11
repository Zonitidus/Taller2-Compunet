package co.edu.icesi.dev.uccareapp.transport.repository;

import org.springframework.data.repository.CrudRepository;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salespersonquotahistory;
import co.edu.icesi.dev.uccareapp.transport.model.sales.SalespersonquotahistoryPK;
import org.springframework.stereotype.Repository;

public interface SalesPersonQuotaHistoryRepository extends CrudRepository<Salespersonquotahistory, Integer>{

}
