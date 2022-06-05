package co.edu.icesi.dev.uccareapp.transport.service.interfaces;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;

public interface SalesPersonService {

	public void save(Salesperson sp);
	public void edit(Salesperson sp);
	public Optional<Salesperson> findById(Integer id);
	public Iterable<Salesperson> findAll();
	
	public Iterable<Salesperson> findByTerritoryid(Integer territoryId);
	public Iterable<Salesperson> findBySalesquota(BigDecimal salesquota);
	public Iterable<Salesperson> findByCommissionpct(BigDecimal commissionpct);
	public Iterable<Salesperson> customQuery(Salesterritory salesterritory, Date minDate, Date maxDate);
}
