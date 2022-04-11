package co.edu.icesi.dev.uccareapp.transport.service.implementation;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.repository.BusinessEntittyRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.EmployeeRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesPersonRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryHistoryRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryRepository;
import co.edu.icesi.dev.uccareapp.transport.service.interfaces.SalesPersonService;

@Service
public class SalesPersonServiceImp implements SalesPersonService {

	private BusinessEntittyRepository businessEntityRepo;
	private EmployeeRepository employeeRepo;
	private SalesPersonRepository salesPersonRepo;
	private SalesTerritoryRepository salesTerritoryRepo;
	
	
	public SalesPersonServiceImp(BusinessEntittyRepository businessEntityRepo, EmployeeRepository employeeRepo,
			SalesPersonRepository salesPersonRepo, SalesTerritoryRepository salesTerritoryRepo) {
		
		this.businessEntityRepo = businessEntityRepo;
		this.employeeRepo = employeeRepo;
		this.salesPersonRepo = salesPersonRepo;
		this.salesTerritoryRepo = salesTerritoryRepo;
	}

	@Override
	public Salesperson save(Salesperson sp){
		
		if(sp == null) 
			throw new RuntimeException("Cannot save a null Salesperson object.");
		
		if(sp.getCommissionpct() == null || sp.getSalesquota() == null || 
				sp.getSalesterritory() == null || sp.getBusinessentityid() == null)
			throw new RuntimeException("Cannot save a Salesperson object with null attributes.");
		
		if(sp.getCommissionpct().compareTo(new BigDecimal(0)) < 0 || sp.getCommissionpct().compareTo(new BigDecimal(1)) > 0)
			throw new RuntimeException("Commision attribute must be in 0-1 range.");
		
		if(sp.getSalesquota().compareTo(new BigDecimal(0)) <= 0)
			throw new RuntimeException("salesQuota must be greater than zero.");
		
		if(this.businessEntityRepo.findById(sp.getBusinessentityid()).isEmpty())
			throw new RuntimeException("Non-existent businessEntityId");

		if(this.salesTerritoryRepo.findById(sp.getSalesterritory().getTerritoryid()).isEmpty())
			throw new RuntimeException("Non-existent SalesterritoryId");
		
		return this.salesPersonRepo.save(sp);
	}

	@Override
	public Salesperson edit(Salesperson sp) {
		
		if(sp == null) 
			throw new RuntimeException("Cannot save a null Salesperson object.");
		
		if(sp.getCommissionpct().compareTo(new BigDecimal(0)) < 0 || sp.getCommissionpct().compareTo(new BigDecimal(1)) > 0)
			throw new RuntimeException("Commision attribute must be in 0-1 range.");
		
		if(sp.getCommissionpct().compareTo(new BigDecimal(0)) < 0 || sp.getCommissionpct().compareTo(new BigDecimal(1)) > 0)
			throw new RuntimeException("Commision attribute must be in 0-1 range.");
		
		if(sp.getSalesquota().compareTo(new BigDecimal(0)) <= 0)
			throw new RuntimeException("salesQuota must be greater than zero.");
		
		if(this.businessEntityRepo.findById(sp.getBusinessentityid()).isEmpty())
			throw new RuntimeException("Non-existent businessEntityId");

		if(this.salesTerritoryRepo.findById(sp.getSalesterritory().getTerritoryid()).isEmpty())
			throw new RuntimeException("Non-existent SalesterritoryId");
		
		Salesperson spModified = this.salesPersonRepo.findById(sp.getBusinessentityid()).get();
		
		spModified.setBonus(sp.getBonus());
		spModified.setCommissionpct(sp.getCommissionpct());
		spModified.setModifieddate(sp.getModifieddate());
		spModified.setRowguid(sp.getRowguid());
		spModified.setSaleslastyear(sp.getSaleslastyear());
		spModified.setSalesorderheaders(sp.getSalesorderheaders());
		spModified.setSalespersonquotahistories(sp.getSalespersonquotahistories());
		spModified.setSalesquota(sp.getSalesquota());
		spModified.setSalesterritory(sp.getSalesterritory());
		spModified.setSalesterritoryhistories(sp.getSalesterritoryhistories());
		spModified.setSalesytd(sp.getSalesytd());
		spModified.setStores(sp.getStores());
		
		return this.salesPersonRepo.save(spModified);
	}

	@Override
	public Optional<Salesperson> findById(Integer id) {
		return this.salesPersonRepo.findById(id);
	}

	@Override
	public Iterable<Salesperson> findAll() {
		return this.salesPersonRepo.findAll();
	}

}