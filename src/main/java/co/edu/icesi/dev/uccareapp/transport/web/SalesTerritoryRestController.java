package co.edu.icesi.dev.uccareapp.transport.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesTerritoryServiceImp;

@RestController
public class SalesTerritoryRestController {

	@Autowired
	public SalesTerritoryServiceImp salesterritoryservice;
	
	@PostMapping("/salesterritory/add")
	public void save(@RequestBody Salesterritory st) {
		this.salesterritoryservice.save(st);
	}
	@PutMapping("/salesterritory/update")
	public void edit(@RequestBody Salesterritory st) {
		this.salesterritoryservice.edit(st);
	}
	
	@GetMapping("/salesterritory/{id}")
	public Optional<Salesterritory> findById(@PathVariable("id") Integer id){
		return this.findById(id);
	}
	
	@GetMapping("/salesterritory/")
	public Iterable<Salesterritory> findAll(){
		return this.findAll();
	}
	
	@GetMapping("/salesterritory/customquery")
	public Iterable<Salesterritory> customQuery(){
		return this.salesterritoryservice.customQuery();
	}
}
