package co.edu.icesi.dev.uccareapp.transport.service.implementation;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salespersonquotahistory;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesPersonQuotaHistoryRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesPersonRepository;
import co.edu.icesi.dev.uccareapp.transport.service.interfaces.SalesPersonQuotaHistoryService;

@Service
public class SalesPersonQuotaHistoryServiceImp implements SalesPersonQuotaHistoryService {

	private SalesPersonQuotaHistoryRepository salesPersonQuotaHistoryRepo;
	private SalesPersonRepository salesPersonRepo;

	public SalesPersonQuotaHistoryServiceImp(SalesPersonQuotaHistoryRepository salesPersonQuotaHistoryRepo,
			SalesPersonRepository salesPersonRepo) {

		this.salesPersonQuotaHistoryRepo = salesPersonQuotaHistoryRepo;
		this.salesPersonRepo = salesPersonRepo;
	}

	@Override
	public Salespersonquotahistory save(Salespersonquotahistory spqh) {

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

		return this.salesPersonQuotaHistoryRepo.save(spqh);
	}

	@Override
	public Salespersonquotahistory edit(Salespersonquotahistory spqh) {

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

		Salespersonquotahistory spqhModified = this.salesPersonQuotaHistoryRepo.findById(spqh.getId()).get();

		spqhModified.setModifieddate(spqh.getModifieddate());
		spqhModified.setRowguid(spqh.getRowguid());
		spqhModified.setSalesperson(spqh.getSalesperson());
		spqhModified.setSalesquota(spqh.getSalesquota());

		return this.salesPersonQuotaHistoryRepo.save(spqhModified);
	}

}
