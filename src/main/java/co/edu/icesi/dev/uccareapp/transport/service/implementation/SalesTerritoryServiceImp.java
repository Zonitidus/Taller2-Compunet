package co.edu.icesi.dev.uccareapp.transport.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.dev.uccareapp.transport.daos.SalesterritoryDAO;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.repository.CountryRegionRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryRepository;
import co.edu.icesi.dev.uccareapp.transport.service.interfaces.SalesTerritoryService;

@Service
@Transactional
public class SalesTerritoryServiceImp implements SalesTerritoryService{

	private CountryRegionRepository coutrRegionRepo;
	private SalesTerritoryRepository salesTerritoryRepo;
	
	private SalesterritoryDAO salesterritorydao;
	
	@Autowired
	public SalesTerritoryServiceImp(CountryRegionRepository coutrRegionRepo,
			SalesTerritoryRepository salesTerritoryRepo, SalesterritoryDAO salesterritorydao) {
		super();
		this.coutrRegionRepo = coutrRegionRepo;
		this.salesTerritoryRepo = salesTerritoryRepo;
		this.salesterritorydao = salesterritorydao;
	}

	@Override
	public void save(Salesterritory st) {
		
		if(st == null)
			throw new RuntimeException("Cannot save a null Salesterritory object.");
		
		if(st.getName() == null || st.getCountryregioncode() == null)
			throw new RuntimeException("Cannot save an object with null attributes.");
		
		if(st.getName().length() < 5)
			throw new RuntimeException("Name must have at least 5 characters.");
		
		if(this.coutrRegionRepo.findById(st.getCountryregioncode()).isEmpty())
			throw new RuntimeException("Invalid Country-Region code.");
		
		this.salesterritorydao.save(st);
	}

	@Override
	public void edit(Salesterritory st) {
		
		if(st == null)
			throw new RuntimeException("Cannot save a null Salesterritory object.");
		
		if(st.getName() == null || st.getCountryregioncode() == null)
			throw new RuntimeException("Cannot save an object with null attributes.");
		
		if(st.getName().length() < 5)
			throw new RuntimeException("Name must have at least 5 characters.");
		
		if(this.coutrRegionRepo.findById(st.getCountryregioncode()).isEmpty())
			throw new RuntimeException("Invalid Country-Region code.");
		
		this.salesterritorydao.update(st);
		
		/*
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
		
		return sav;*/
	}
	
	@Override
	public Iterable<Salesterritory> findAll() {
		return this.salesterritorydao.findAll();
	}
	
	@Override
	public Optional<Salesterritory> findById(Integer id){
		return this.salesterritorydao.findById(id);
	}

	@Override
	public Iterable<Salesterritory> customQuery() {
		// TODO Auto-generated method stub
		return this.salesterritorydao.customQuery();
	}

}
