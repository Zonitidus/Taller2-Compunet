package co.edu.icesi.dev.uccareapp.transport.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.icesi.dev.uccareapp.transport.model.sales.Currencyrate;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.CurrencyRateService;

@RestController
public class CurrencyRateRestController {
	
	@Autowired
	private CurrencyRateService currencyRateService;
	
	@GetMapping("/currencyRate")
	public Iterable<Currencyrate> getCurrencyrate(){
		return currencyRateService.findAll();
	}
	
	@GetMapping("/currencyRate/{id}")
	public Currencyrate getCurrencyrate(@PathVariable Integer id) {
		return currencyRateService.findByID(id).get();
	}
	
	@PostMapping("/currencyRate/add")
	public void addCurrencyrate(@RequestBody Currencyrate spqh) {
		currencyRateService.save(spqh);
	}
	
	@PutMapping("/currencyRate/update")
	public void editCurrencyrate(@RequestBody Currencyrate spqh) {
		currencyRateService.edit(spqh);
	}
	
	@DeleteMapping("currencyRate/delete/{id}")
	public void deleteCurrencyrate(@PathVariable Integer id) {
		currencyRateService.delete(id);
	}
}
