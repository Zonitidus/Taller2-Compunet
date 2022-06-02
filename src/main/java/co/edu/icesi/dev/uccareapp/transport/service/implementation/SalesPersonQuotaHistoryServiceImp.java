package co.edu.icesi.dev.uccareapp.transport.service.implementation;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.dev.uccareapp.transport.daos.SalespersonquotahistoryDAO;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salespersonquotahistory;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesPersonQuotaHistoryRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesPersonRepository;
import co.edu.icesi.dev.uccareapp.transport.service.interfaces.SalesPersonQuotaHistoryService;

@Service
@Transactional
public class SalesPersonQuotaHistoryServiceImp implements SalesPersonQuotaHistoryService {

	private SalesPersonRepository salesPersonRepo;
	private SalespersonquotahistoryDAO salespersonquotahistoryDAO;

	@Autowired
	public SalesPersonQuotaHistoryServiceImp(SalesPersonRepository salesPersonRepo,SalespersonquotahistoryDAO salespersonquotahistoryDAO) {
		this.salesPersonRepo = salesPersonRepo;
		this.salespersonquotahistoryDAO = salespersonquotahistoryDAO;
	}

	@Override
	public void save(Salespersonquotahistory spqh) {

		if (spqh == null)
			throw new RuntimeException("Cannot save a null Salespersonquotahistory object.");

		if (spqh.getModifieddate() == null || spqh.getSalesperson() == null
				|| spqh.getSalesquota() == null)
			throw new RuntimeException("Cannot save a Salespersonquotahistory object with null attributes.");

			if (spqh.getModifieddate().compareTo(Timestamp.valueOf(LocalDateTime.now())) > 0)
				throw new RuntimeException("modifiedDate must be lesser than the current date.");

		if (spqh.getSalesquota().compareTo(new BigDecimal(0)) < 0)
			throw new RuntimeException("Sales quota must be greter than zero;");

		if (this.salesPersonRepo.findById(spqh.getSalesperson().getBusinessentityid()).isEmpty())
			throw new RuntimeException("Non-existent businessEntityId");

		salespersonquotahistoryDAO.save(spqh);
	}

	@Override
	public void edit(Salespersonquotahistory spqh) {

		if (spqh == null)
			throw new RuntimeException("Cannot save a null Salespersonquotahistory object.");
		
		if (spqh.getId() == null || spqh.getModifieddate() == null || spqh.getSalesperson() == null
				|| spqh.getSalesquota() == null)
			throw new RuntimeException("Cannot save a Salespersonquotahistory object with null attributes.");

		if (spqh.getModifieddate().compareTo(Timestamp.valueOf(LocalDateTime.now())) > 0)
			throw new RuntimeException("modifiedDate must be lesser than the current date.");

		if (spqh.getSalesquota().compareTo(new BigDecimal(0)) < 0)
			throw new RuntimeException("Sales quota must be greter than zero;");

		if (this.salesPersonRepo.findById(spqh.getSalesperson().getBusinessentityid()).isEmpty())
			throw new RuntimeException("Non-existent businessEntityId");

		Salespersonquotahistory spqhModified = this.salespersonquotahistoryDAO.findById(spqh.getId()).get();

		spqhModified.setModifieddate(spqh.getModifieddate());
		spqhModified.setRowguid(spqh.getRowguid());
		spqhModified.setSalesperson(spqh.getSalesperson());
		spqhModified.setSalesquota(spqh.getSalesquota());

		salespersonquotahistoryDAO.save(spqhModified);
	}

	@Override
	public Optional<Salespersonquotahistory> findByID(Integer id) {
		// TODO Auto-generated method stub
		return salespersonquotahistoryDAO.findById(id);
	}

	@Override
	public Iterable<Salespersonquotahistory> findAll() {
		// TODO Auto-generated method stub
		return salespersonquotahistoryDAO.findAll();
	}

	@Override
	public Iterable<Salespersonquotahistory> findBySalespersonid(Integer salespersonid) {
		// TODO Auto-generated method stub
		return salespersonquotahistoryDAO.findBySalespersonid(salespersonid);
	}

	@Override
	public Iterable<Salespersonquotahistory> findBySalesquota(BigDecimal salesquota) {
		// TODO Auto-generated method stub
		return salespersonquotahistoryDAO.findBySalesquota(salesquota);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		Salespersonquotahistory spqh = salespersonquotahistoryDAO.findById(id).get();
		salespersonquotahistoryDAO.delete(spqh);
	}

}
