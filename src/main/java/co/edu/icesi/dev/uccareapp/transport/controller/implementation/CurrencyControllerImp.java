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

import co.edu.icesi.dev.uccareapp.transport.delegate.CurrencyDelegate;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Currency;

@Controller
public class CurrencyControllerImp {

	@Autowired
	private CurrencyDelegate currencyDelegate;

	@GetMapping("/currency/add")
	public String addSalesPerson(Model model) {
		model.addAttribute("currency", new Currency());

		return "currency/add-currency";
	}

	@GetMapping("/currency/")
	public String indexSalesPerson(Model model) {
		model.addAttribute("currencies", currencyDelegate.findAll());

		return "currency/index";
	}

	@PostMapping("/currency/add")
	public String saveUser(@Validated @ModelAttribute Currency spqh, BindingResult bindingResult, Model model,
			@RequestParam(value = "action", required = true) String action) {

		if (!action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				return "currency/add-currency";
			}

			currencyDelegate.save(spqh);
		}
		return "redirect:/currency/";
	}

	@GetMapping("/currency/edit/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) {

		Optional<Currency> user = Optional.of(currencyDelegate.findByID(id));

		if (user.isEmpty())
			throw new IllegalArgumentException("Invalid user Id:" + id);
		model.addAttribute("currency", user.get());
		return "currency/update-currency";
	}

	@PostMapping("/currency/edit/{id}")
	public String updateUser(@Validated @ModelAttribute Currency spqh, BindingResult bindingResult, Model model,
			@RequestParam(value = "action", required = true) String action) {
		if (action != null && !action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				return "currency/update-currency";
			}
			currencyDelegate.edit(spqh);
		}
		return "redirect:/currency/";
	}
}
