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

import co.edu.icesi.dev.uccareapp.transport.controller.interfaces.SalesQuotaHistoryController;
import co.edu.icesi.dev.uccareapp.transport.delegate.SalesPersonDelegate;
import co.edu.icesi.dev.uccareapp.transport.delegate.SalesPersonQuotaHistoryDelegate;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salespersonquotahistory;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.repository.BusinessEntittyRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesPersonQuotaHistoryRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesPersonRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryRepository;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesPersonQuotaHistoryServiceImp;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesPersonServiceImp;

@Controller
public class SalesPersonQuotaHistoryControllerImp implements SalesQuotaHistoryController {

	@Autowired
	SalesPersonDelegate salesPersonDelegate;
	@Autowired
	SalesPersonQuotaHistoryDelegate salesPersonQuotaHistoryDelegate;


	@GetMapping("/salespersonquotahistory/add")
	public String addSalesPerson(Model model) {

		model.addAttribute("salespersonquotahistory", new Salespersonquotahistory());
		model.addAttribute("salespersons", salesPersonDelegate.findAll());

		return "salespersonquotahistory/add-salespersonquotahistory";
	}

	@GetMapping("/salespersonquotahistory/")
	public String indexSalesPerson(Model model) {
		model.addAttribute("salespersonquotahistories", salesPersonQuotaHistoryDelegate.findAll());

		return "salespersonquotahistory/index";
	}

	@PostMapping("/salespersonquotahistory/add")
	public String saveUser(@Validated @ModelAttribute Salespersonquotahistory spqh, BindingResult bindingResult,
			Model model, @RequestParam(value = "action", required = true) String action) {

		if (!action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("salespersons", salesPersonDelegate.findAll());
				System.out.println("****************fffffffffffffff" + "*");
				return "/salespersonquotahistory/add-salespersonquotahistory";
			}
			
			System.out.println(spqh.getSalesperson().getBusinessentityid());
			System.out.println(spqh.getSalesperson().getSalesquota());
			
			
			salesPersonQuotaHistoryDelegate.save(spqh);
		}
		return "redirect:/salespersonquotahistory/";
	}

	@GetMapping("/salespersonquotahistory/edit/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

		Optional<Salespersonquotahistory> user = Optional.of(salesPersonQuotaHistoryDelegate.findByID(id));

		if (user == null)
			throw new IllegalArgumentException("Invalid user Id:" + id);
		model.addAttribute("salespersonquotahistory", user.get());
		model.addAttribute("salespersons", salesPersonDelegate.findAll());

		return "salespersonquotahistory/update-salespersonquotahistory";
	}

	@PostMapping("/salespersonquotahistory/edit/{id}")
	public String updateUser(@Validated @ModelAttribute Salespersonquotahistory spqh, BindingResult bindingResult, Model model,
			@PathVariable("id") Integer id,
			@RequestParam(value = "action", required = true) String action) {
		if (action != null && !action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("salespersons", salesPersonDelegate.findAll());
				return "/salespersonquotahistory/update-salespersonquotahistory";
			}

			Salespersonquotahistory u = spqh;
			u.setId(id);

			salesPersonQuotaHistoryDelegate.edit(u);
			model.addAttribute("salespersonquotahistories", salesPersonQuotaHistoryDelegate.findAll());
		}
		return "redirect:/salespersonquotahistory/";
	}
}
