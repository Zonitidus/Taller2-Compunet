package co.edu.icesi.dev.uccareapp.transport.daos;

import java.math.BigDecimal;
import java.util.Optional;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salespersonquotahistory;

public interface ISalespersonquotahistoryDAO {

	public void save(Salespersonquotahistory entity);
	public void update(Salespersonquotahistory entity);
	public Optional<Salespersonquotahistory> findById(Integer id);
	public Iterable<Salespersonquotahistory> findAll();
	
	public Iterable<Salespersonquotahistory> findBySalespersonid(Integer salespersonid);
	public Iterable<Salespersonquotahistory> findBySalesquota(BigDecimal salesquota);
}
