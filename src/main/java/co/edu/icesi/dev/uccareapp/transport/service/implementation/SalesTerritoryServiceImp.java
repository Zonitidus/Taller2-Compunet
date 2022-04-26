package co.edu.icesi.dev.uccareapp.transport.service.implementation;

import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.repository.CountryRegionRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryRepository;
import co.edu.icesi.dev.uccareapp.transport.service.interfaces.SalesTerritoryService;

@Service
public class SalesTerritoryServiceImp implements SalesTerritoryService{

	private CountryRegionRepository coutrRegionRepo;
	private SalesTerritoryRepository salesTerritoryRepo;
	
	public SalesTerritoryServiceImp(CountryRegionRepository coutrRegionRepo,
			SalesTerritoryRepository salesTerritoryRepo) {
		super();
		this.coutrRegionRepo = coutrRegionRepo;
		this.salesTerritoryRepo = salesTerritoryRepo;
	}

	@Override
	public Salesterritory save(Salesterritory st) {
		
		if(st == null)
			throw new RuntimeException("Cannot save a null Salesterritory object.");
		
		if(st.getName() == null || st.getCountryregioncode() == null)
			throw new RuntimeException("Cannot save an object with null attributes.");
		
		if(st.getName().length() < 5)
			throw new RuntimeException("Name must have at least 5 characters.");
		
		if(this.coutrRegionRepo.findById(st.getCountryregioncode()).isEmpty())
			throw new RuntimeException("Invalid Country-Region code.");
		
		return this.salesTerritoryRepo.save(st);
	}

	@Override
	public Salesterritory edit(Salesterritory st) {
		
		if(st == null)
			throw new RuntimeException("Cannot save a null Salesterritory object.");
		
		if(st.getName() == null || st.getCountryregioncode() == null)
			throw new RuntimeException("Cannot save an object with null attributes.");
		
		if(st.getName().length() < 5)
			throw new RuntimeException("Name must have at least 5 characters.");
		
		if(this.coutrRegionRepo.findById(st.getCountryregioncode()).isEmpty())
			throw new RuntimeException("Invalid Country-Region code.");
		
		
		Salesterritory stModified = this.salesTerritoryRepo.findById(st.getTerritoryid()).get();
		
		stModified.setCostlastyear(st.getCostlastyear());
		stModified.setCostytd(st.getCostytd());
		stModified.setCountryregioncode(st.getCountryregioncode());
		stModified.setCustomers(st.getCustomers());
		stModified.setModifieddate(st.getModifieddate());
		stModified.setName(st.getName());
		stModified.setRowguid(st.getRowguid());
		stModified.setSalesGroup(st.getSalesGroup());
		stModified.setSaleslastyear(st.getSaleslastyear());
		stModified.setSalesorderheaders(st.getSalesorderheaders());
		stModified.setSalespersons(st.getSalespersons());
		stModified.setSalesterritoryhistories(st.getSalesterritoryhistories());
		stModified.setSalesytd(st.getSalesytd());
		
		Salesterritory sav = salesTerritoryRepo.save(stModified);
		
		System.out.println(sav);
		
		return sav;
	}
	
	public Iterable<Salesterritory> findAll() {
		return this.salesTerritoryRepo.findAll();
	}
	
	public Optional<Salesterritory> findById(Integer id){
		return this.salesTerritoryRepo.findById(id);
	}

}
