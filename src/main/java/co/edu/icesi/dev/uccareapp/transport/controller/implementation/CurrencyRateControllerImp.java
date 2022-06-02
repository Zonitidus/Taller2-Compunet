package co.edu.icesi.dev.uccareapp.transport.controller.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.dev.uccareapp.transport.delegate.CurrencyRateDelegate;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Currencyrate;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salespersonquotahistory;

@Controller
public class CurrencyRateControllerImp {

	@Autowired
	private CurrencyRateDelegate currencyRateDelegate;
	

	@GetMapping("/currencyrate/add")
	public String addSalesPerson(Model model) {
		model.addAttribute("currencyrate", new Currencyrate());
		
		return "currencyrate/add-currencyrate";
	}

	@GetMapping("/currencyrate/")
	public String indexSalesPerson(Model model) {
		model.addAttribute("currencyrates", currencyRateDelegate.findAll());

		return "currencyrate/index";
	}

	@PostMapping("/currencyrate/add")
	public String saveUser(@Validated @ModelAttribute Currencyrate spqh, BindingResult bindingResult,
			Model model, @RequestParam(value = "action", required = true) String action) {

		if (!action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				return "currencyrate/add-currencyrate";
			}
			
			currencyRateDelegate.save(spqh);
		}
		return "redirect:/currencyrate/";
	}

	@GetMapping("/currencyrate/edit/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

		Optional<Currencyrate> user = Optional.of(currencyRateDelegate.findByID(id));

		if (user.isEmpty())
			throw new IllegalArgumentException("Invalid user Id:" + id);
		model.addAttribute("currencyrate", user.get());
		return "currencyrate/update-currencyrate";
	}

	@PostMapping("/currencyrate/edit/{id}")
	public String updateUser(@Validated @ModelAttribute Currencyrate spqh, BindingResult bindingResult, Model model,
			@RequestParam(value = "action", required = true) String action) {
		if (action != null && !action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				return "currencyrate/update-currencyrate";
			}
			currencyRateDelegate.edit(spqh);
		}
		return "redirect:/currencyrate/";
	}
}
