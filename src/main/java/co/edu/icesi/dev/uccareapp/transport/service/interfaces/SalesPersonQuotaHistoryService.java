package co.edu.icesi.dev.uccareapp.transport.service.interfaces;

import java.math.BigDecimal;
import java.util.Optional;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salespersonquotahistory;

public interface SalesPersonQuotaHistoryService {

	public void save(Salespersonquotahistory spqh);
	public void edit(Salespersonquotahistory spqh); 
	public void delete(Integer id);
	public Optional<Salespersonquotahistory> findByID(Integer id);
	public Iterable<Salespersonquotahistory> findAll();
	public Iterable<Salespersonquotahistory> findBySalespersonid(Integer salespersonid);
	public Iterable<Salespersonquotahistory> findBySalesquota(BigDecimal salesquota);
}
