package co.edu.icesi.dev.uccareapp.transport.web;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salespersonquotahistory;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesPersonQuotaHistoryServiceImp;
import co.edu.icesi.dev.uccareapp.transport.service.interfaces.SalesPersonQuotaHistoryService;

@RestController
public class SalesPersonQuotaHistoryRestController {

	@Autowired
	private SalesPersonQuotaHistoryService salesPersonQuotaHistoryServiceImp;
	
	@GetMapping("/api/salesPersonQuotaHistory")
	public Iterable<Salespersonquotahistory> getSalespersonquotahistory(){
		return salesPersonQuotaHistoryServiceImp.findAll();
	}
	
	@GetMapping("/api/salesPersonQuotaHistory/{id}")
	public Salespersonquotahistory getSalespersonquotahistory(@PathVariable Integer id) {
		return salesPersonQuotaHistoryServiceImp.findByID(id).get();
	}
	
	@PostMapping("/api/salesPersonQuotaHistory/add")
	public void addSalesPersonQuotaHistory(@RequestBody Salespersonquotahistory spqh) {
		salesPersonQuotaHistoryServiceImp.save(spqh);
	}
	
	@PutMapping("/api/salesPersonQuotaHistory/update")
	public void updateSalespersonquotahistory(@RequestBody Salespersonquotahistory spqh) {
		salesPersonQuotaHistoryServiceImp.edit(spqh);
	}
	
	@GetMapping("/api/salesPersonQuotaHistory/findBySalesperson/{id}")
	public Iterable<Salespersonquotahistory> findBySalespersonid(@PathVariable Integer id){
		return salesPersonQuotaHistoryServiceImp.findBySalespersonid(id);
	}
	
	@GetMapping("/api/salesPersonQuotaHistory/findBySalesquota/{id}")
	public Iterable<Salespersonquotahistory> findBySalesquota(BigDecimal salesquota){
		return salesPersonQuotaHistoryServiceImp.findBySalesquota(salesquota);
	}
	
	@DeleteMapping("/api/salesPersonQuotaHistory/delete/{id}")
	public void deleteSalesPersonQuotaHistory(@PathVariable Integer id) {
		salesPersonQuotaHistoryServiceImp.delete(id);
	}
	
}
