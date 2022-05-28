package co.edu.icesi.dev.uccareapp.transport.delegate;

import java.math.BigDecimal;
import java.util.Optional;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salespersonquotahistory;

public interface ISalesPersonQuotaHistoryDelegate {
	public void save(Salespersonquotahistory spqh);
	public void edit(Salespersonquotahistory spqh); 
	public void delete(Integer id);
	public Salespersonquotahistory findByID(Integer id);
	public Iterable<Salespersonquotahistory> findAll();
	public Iterable<Salespersonquotahistory> findBySalespersonid(Integer salespersonid);
	public Iterable<Salespersonquotahistory> findBySalesquota(BigDecimal salesquota);
}
