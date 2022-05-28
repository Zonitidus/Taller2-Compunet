package co.edu.icesi.dev.uccareapp.transport.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritoryhistory;
import co.edu.icesi.dev.uccareapp.transport.service.interfaces.SalesTerritoyHistoryService;

@RestController
public class salesTerritoryHistoryRestController {

	@Autowired
	private SalesTerritoyHistoryService salesTerritoyHistoryService;
	
	@PostMapping("/salesTerritoyHistory/add")
	public void getSalesTerritoyHistory(@RequestBody Salesterritoryhistory sth) {
		salesTerritoyHistoryService.save(sth);
	}
	
	@PutMapping("/salesTerritoyHistory/update")
	public void updatealesTerritoyHistory(@RequestBody Salesterritoryhistory sth) {
		salesTerritoyHistoryService.edit(sth);
	}
	
	@GetMapping("/salesTerritoyHistory/{id}")
	public Salesterritoryhistory findById(Integer id){
		return salesTerritoyHistoryService.findById(id).get();
	}
	
	@GetMapping("/salesTerritoyHistory")
	public Iterable<Salesterritoryhistory> findAll(){
		return salesTerritoyHistoryService.findAll();
	}

	@GetMapping("/salesTerritoyHistory/findBySalespersonid/{id}")
	public Iterable<Salesterritoryhistory> findBySalespersonid(@PathVariable Integer salespersonid){
		return salesTerritoyHistoryService.findBySalespersonid(salespersonid);
	}

	@GetMapping("/salesTerritoyHistory/findBySalesterritoryid/{id}")
	public Iterable<Salesterritoryhistory> findBySalesterritoryid(@PathVariable Integer salesterritoryid){
		return salesTerritoyHistoryService.findBySalesterritoryid(salesterritoryid);
	}
	
	@DeleteMapping("/salesTerritoyHistory/delete/{id}")
	public void deleteSalesTerritoyHistory(Integer id) {
		salesTerritoyHistoryService.delete(id);
	}
}
