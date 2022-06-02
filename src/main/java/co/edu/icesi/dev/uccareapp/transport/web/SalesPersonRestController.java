package co.edu.icesi.dev.uccareapp.transport.web;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesPersonServiceImp;

@RestController
public class SalesPersonRestController {

	@Autowired
	private SalesPersonServiceImp salespersonservice;

	@PostMapping("/api/salesperson/add")
	public void addSalesPersonQuotaHistory(@RequestBody Salesperson sp) {
		this.salespersonservice.save(sp);
	}

	@PutMapping("/api/salesperson/update")
	public void updateSalespersonquotahistory(@RequestBody Salesperson sp) {
		this.salespersonservice.edit(sp);
	}

	@GetMapping("/api/salesperson/{id}")
	public Optional<Salesperson> findById(@PathVariable("id") Integer id) {
		// TODO Auto-generated method stub
		return this.salespersonservice.findById(id);
	}

	@GetMapping("/api/salesperson/")
	public Iterable<Salesperson> findAll() {
		// TODO Auto-generated method stub
		return this.salespersonservice.findAll();
	}

	@GetMapping("/api/salesperson/findbyterritory/{territoryid}")
	public Iterable<Salesperson> findByTerritoryid(@PathVariable("territoryid") Integer territoryId) {
		// TODO Auto-generated method stub
		return this.salespersonservice.findByTerritoryid(territoryId);
	}

	@GetMapping("/api/salesperson/findbysalesquota/{salesquota}")
	public Iterable<Salesperson> findBySalesquota(@PathVariable("salesquota") BigDecimal salesquota) {
		// TODO Auto-generated method stub
		return this.salespersonservice.findBySalesquota(salesquota);
	}

	@GetMapping("/api/salesperson/findbycommissionpct/{commissionpct}")
	public Iterable<Salesperson> findByCommissionpct(@PathVariable("commissionpct") BigDecimal commissionpct) {
		// TODO Auto-generated method stub
		return this.salespersonservice.findByCommissionpct(commissionpct);
	}

	@GetMapping("/api/salesperson/customquery/salesterritory/start/minDate/end/maxDate")
	public Iterable<Salesperson> customQuery(@RequestParam("salesterritory") Salesterritory salesterritory,
			@RequestParam("minDate") Date minDate, @RequestParam("maxDate") Date maxDate) {

		return this.salespersonservice.customQuery(salesterritory, minDate, maxDate);
	}
}
