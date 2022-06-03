package co.edu.icesi.dev.uccareapp.transport.service.implementation;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.dev.uccareapp.transport.daos.ISalesterritoryHistoryDAO;
import co.edu.icesi.dev.uccareapp.transport.daos.SalesterritoryhistoryDAO;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritoryhistory;
import co.edu.icesi.dev.uccareapp.transport.repository.BusinessEntittyRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesPersonRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryHistoryRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryRepository;
import co.edu.icesi.dev.uccareapp.transport.service.interfaces.SalesTerritoyHistoryService;

@Service
@Transactional
public class SalesTerritoryHistoryServiceImp implements SalesTerritoyHistoryService {

	private SalesTerritoryRepository salesTerritoryRepository;
	private BusinessEntittyRepository businessEntityRepository;
	private ISalesterritoryHistoryDAO salesterritoryhistoryDAO;

	@Autowired
	public SalesTerritoryHistoryServiceImp(SalesTerritoryRepository salesTerritoryRepository,
			BusinessEntittyRepository businessEntityRepository,ISalesterritoryHistoryDAO salesterritoryhistoryDAO) {

		this.salesTerritoryRepository = salesTerritoryRepository;
		this.businessEntityRepository = businessEntityRepository;
		this.salesterritoryhistoryDAO = salesterritoryhistoryDAO;
	}

	@Override
	public void save(Salesterritoryhistory sth) {
		System.out.println("entra al servicio");
		if (sth == null)
			throw new RuntimeException("Cannot save a null saveSalesTerritoryHistory object.");
		
		if (sth.getEnddate() == null || sth.getModifieddate() == null
				|| sth.getSalesperson() == null || sth.getSalesterritory() == null)
			throw new RuntimeException("Cannot save a saveSalesTerritoryHistory object with null attributes.");

		if (sth.getEnddate().compareTo(Timestamp.valueOf(LocalDateTime.now())) > 0)
			throw new RuntimeException("End date must be less or equals to the current date");

		if (sth.getModifieddate().compareTo(sth.getEnddate()) >= 0)
			throw new RuntimeException("Init date must be less than end date");

		System.out.println("aa"+sth.getSalesperson().getBusinessentityid());
		System.out.println("bb"+sth.getSalesterritory().getTerritoryid());
		
		System.out.println(sth.getSalesterritory().getTerritoryid());
		System.out.println(sth.getSalesperson().getBusinessentityid());
		if (this.businessEntityRepository.findById(sth.getSalesperson().getBusinessentityid()).isEmpty())
			throw new RuntimeException("Invalid BusinessEntityId");

		if (this.salesTerritoryRepository.findById(sth.getSalesterritory().getTerritoryid()).isEmpty())
			throw new RuntimeException("Invalid associated SalesTerritory");
		System.out.println("entra al servicio");
		salesterritoryhistoryDAO.save(sth);
	}

	@Override
	public void edit(Salesterritoryhistory sth) {

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

		if (this.salesterritoryhistoryDAO.findById(sth.getId()).isEmpty())
			throw new RuntimeException("Non-existent salesTerritoryHistory to edit");

		Salesterritoryhistory sthModified = this.salesterritoryhistoryDAO.findById(sth.getId()).get();

		sthModified.setEnddate(sth.getEnddate());
		sthModified.setModifieddate(sth.getModifieddate());
		sthModified.setRowguid(sth.getRowguid());
		sthModified.setSalesperson(sth.getSalesperson());
		sthModified.setSalesterritory(sth.getSalesterritory());

		salesterritoryhistoryDAO.save(sthModified);
	}
	
	public Iterable<Salesterritoryhistory> findAll(){
		return salesterritoryhistoryDAO.findAll();
	}

	public Optional<Salesterritoryhistory> findById(Integer id){
		return salesterritoryhistoryDAO.findById(id);
	}

	@Override
	public Iterable<Salesterritoryhistory> findBySalespersonid(Integer salespersonid) {
		// TODO Auto-generated method stub
		return salesterritoryhistoryDAO.findBySalespersonid(salespersonid);
	}

	@Override
	public Iterable<Salesterritoryhistory> findBySalesterritoryid(Integer salesterritoryid) {
		// TODO Auto-generated method stub
		return salesterritoryhistoryDAO.findBySalesterritoryid(salesterritoryid);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		Salesterritoryhistory sth = salesterritoryhistoryDAO.findById(id).get();
		salesterritoryhistoryDAO.delete(sth);
	}
}
