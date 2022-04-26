package co.edu.icesi.dev.uccareapp.transport.service.implementation;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritoryhistory;
import co.edu.icesi.dev.uccareapp.transport.repository.BusinessEntittyRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesPersonRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryHistoryRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryRepository;
import co.edu.icesi.dev.uccareapp.transport.service.interfaces.SalesTerritoyHistoryService;

@Service
public class SalesTerritoryHistoryServiceImp implements SalesTerritoyHistoryService {

	private SalesTerritoryHistoryRepository salesTerritoryHistoryRepository;
	private SalesTerritoryRepository salesTerritoryRepository;
	private BusinessEntittyRepository businessEntityRepository;
	private SalesPersonRepository salesPersonRepository;

	@Autowired
	public SalesTerritoryHistoryServiceImp(SalesTerritoryHistoryRepository salesTerritoryHistoryRepository,
			SalesTerritoryRepository salesTerritoryRepository, BusinessEntittyRepository businessEntityRepository,
			SalesPersonRepository salesPersonRepository) {

		this.salesTerritoryHistoryRepository = salesTerritoryHistoryRepository;
		this.salesTerritoryRepository = salesTerritoryRepository;
		this.businessEntityRepository = businessEntityRepository;
		this.salesPersonRepository = salesPersonRepository;
	}

	@Override
	public Salesterritoryhistory save(Salesterritoryhistory sth) {

		if (sth == null)
			throw new RuntimeException("Cannot save a null saveSalesTerritoryHistory object.");
		
		if (sth.getEnddate() == null || sth.getModifieddate() == null
				|| sth.getSalesperson() == null || sth.getSalesterritory() == null)
			throw new RuntimeException("Cannot save a saveSalesTerritoryHistory object with null attributes.");

		if (sth.getEnddate().compareTo(Timestamp.valueOf(LocalDateTime.now())) > 0)
			throw new RuntimeException("End date must be less or equals to the current date");

		if (sth.getModifieddate().compareTo(sth.getEnddate()) >= 0)
			throw new RuntimeException("Init date must be less than end date");

		if (this.businessEntityRepository.findById(sth.getSalesperson().getBusinessentityid()).isEmpty())
			throw new RuntimeException("Invalid BusinessEntityId");

		if (this.salesTerritoryRepository.findById(sth.getSalesterritory().getTerritoryid()).isEmpty())
			throw new RuntimeException("Invalid associated SalesTerritory");

		return this.salesTerritoryHistoryRepository.save(sth);
	}

	@Override
	public Salesterritoryhistory edit(Salesterritoryhistory sth) {

		if (sth == null)
			throw new RuntimeException("Cannot save a null saveSalesTerritoryHistory object.");

		if (sth.getId() == null || sth.getEnddate() == null || sth.getModifieddate() == null
				|| sth.getSalesperson() == null || sth.getSalesterritory() == null)
			throw new RuntimeException("Cannot save a saveSalesTerritoryHistory object with null attributes.");

			if (sth.getEnddate().compareTo(Timestamp.valueOf(LocalDateTime.now())) > 0)
				throw new RuntimeException("End date must be less or equals to the current date");

		if (sth.getModifieddate().compareTo(sth.getEnddate()) >= 0)
			throw new RuntimeException("Init date must be lesser than end date");

		if (this.businessEntityRepository.findById(sth.getSalesperson().getBusinessentityid()).isEmpty())
			throw new RuntimeException("Invalid BusinessEntityId");

		if (this.salesTerritoryRepository.findById(sth.getSalesterritory().getTerritoryid()).isEmpty())
			throw new RuntimeException("Invalid associated SalesTerritory");

		if (this.salesTerritoryHistoryRepository.findById(sth.getId()).isEmpty())
			throw new RuntimeException("Non-existent salesTerritoryHistory to edit");

		Salesterritoryhistory sthModified = this.salesTerritoryHistoryRepository.findById(sth.getId()).get();

		sthModified.setEnddate(sth.getEnddate());
		sthModified.setModifieddate(sth.getModifieddate());
		sthModified.setRowguid(sth.getRowguid());
		sthModified.setSalesperson(sth.getSalesperson());
		sthModified.setSalesterritory(sth.getSalesterritory());

		return this.salesTerritoryHistoryRepository.save(sthModified);
	}

}
