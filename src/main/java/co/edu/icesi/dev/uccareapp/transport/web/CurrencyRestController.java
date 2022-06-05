package co.edu.icesi.dev.uccareapp.transport.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Currency;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.CurrencyService;

@RestController
public class CurrencyRestController {

	@Autowired
	private CurrencyService currencyService;
	
	@GetMapping("/api/currency")
	public Iterable<Currency> getCurrency(){
		return currencyService.findAll();
	}
	
	@GetMapping("/api/currency/{id}")
	public Currency getCurrency(@PathVariable String id) {
		return currencyService.findByID(id).get();
	}
	
	@PostMapping("/api/currency/add")
	public void addCurrency(@RequestBody Currency spqh) {
		currencyService.save(spqh);
	}
	
	@PutMapping("/api/currency/update")
	public void editCurrency(@RequestBody Currency spqh) {
		currencyService.edit(spqh);
	}
	
	@DeleteMapping("/api/currency/delete/{id}")
	public void deleteCurrency(@PathVariable String id) {
		currencyService.delete(id);
	}
}
