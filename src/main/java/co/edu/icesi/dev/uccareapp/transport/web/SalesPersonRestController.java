package co.edu.icesi.dev.uccareapp.transport.web;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
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
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesTerritoryServiceImp;

@RestController
public class SalesPersonRestController {

	@Autowired
	private SalesPersonServiceImp salespersonservice;
	
	@Autowired
	private SalesTerritoryServiceImp salesterritoryservice;

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

	@GetMapping("/api/salesperson/customquery/{salesterritoryid}/{minDate}/{maxDate}")
	public Iterable<Salesperson> customQuery(@PathVariable("salesterritoryid") String salesterritoryid,
			@PathVariable("minDate") String minDate, @PathVariable("maxDate") String maxDate) throws ParseException {

		System.out.println("Está ENTRANDO al rest --->\n\tSalesterritoryid="+salesterritoryid+"\n\tminDate="+minDate+"\n\tmaxdate="+maxDate);
		
		
		Salesterritory salesterritory = this.salesterritoryservice.findById(Integer.parseInt(salesterritoryid)).get();
		
		Timestamp minDateParsed = new Timestamp(0);
		Timestamp maxDateParsed = new Timestamp(0);
		
		try {
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		    
		    Date parsedDateMin = dateFormat.parse(minDate);
		    Date parsedDateMax = dateFormat.parse(maxDate);
		    
		    minDateParsed = new java.sql.Timestamp(parsedDateMin.getTime());
		    maxDateParsed = new java.sql.Timestamp(parsedDateMax.getTime());
		    
		    
		} catch(Exception e) { 

		}
		
		System.out.println("Está SALIENDO del rest --->\n\tSalesterritoryid="+salesterritoryid+"\n\tminDate="+minDateParsed+"\n\tmaxdate="+maxDateParsed);
		
		
		
		return this.salespersonservice.customQuery(salesterritory, minDateParsed, maxDateParsed);
	}
}
