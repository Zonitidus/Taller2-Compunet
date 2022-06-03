package co.edu.icesi.dev.uccareapp.transport.controller.implementation;

import java.util.ArrayList;
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

import co.edu.icesi.dev.uccareapp.transport.controller.interfaces.SalesTerritoryHistoryController;
import co.edu.icesi.dev.uccareapp.transport.delegate.SalesPersonDelegate;
import co.edu.icesi.dev.uccareapp.transport.delegate.SalesTerritoryDelegate;
import co.edu.icesi.dev.uccareapp.transport.delegate.SalesTerritoyHistoryDelegate;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritoryhistory;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryRepository;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesPersonServiceImp;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesTerritoryHistoryServiceImp;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesTerritoryServiceImp;

@Controller
public class SalesTerritoryHistoryControllerImp implements SalesTerritoryHistoryController {

	@Autowired
	SalesTerritoyHistoryDelegate salesTerritoyHistoryDelegate;
	@Autowired
	SalesTerritoryRepository salesTerritoryRepository;
	@Autowired
	SalesPersonDelegate salesPersonDelegate;
	@Autowired
	SalesTerritoryDelegate salesTerritoryDelegate;

	@GetMapping("/salesterritoryhistory/add")
	public String addSalesTerritoryHistory(Model model) {

		model.addAttribute("salesterritoryhistory", new Salesterritoryhistory());

		model.addAttribute("salesterritories", this.salesTerritoryDelegate.findAll());
		model.addAttribute("salespersons", this.salesPersonDelegate.findAll());

		return "salesterritoryhistory/add-salesterritoryhistory";
	}

	@GetMapping("/salesterritoryhistory/")
	public String indexSalesPerson(Model model) {
		model.addAttribute("salesterritoryhistories", this.salesTerritoyHistoryDelegate.findAll());

		return "salesterritoryhistory/index";
	}

	@PostMapping("/salesterritoryhistory/add")
	public String saveUser(@Validated @ModelAttribute Salesterritoryhistory sth, BindingResult bindingResult,
			Model model, @RequestParam(value = "action", required = true) String action) {

		
		if (!action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				System.out.println("****************fffffffffffffff" + "*");

				model.addAttribute("salesterritories", this.salesTerritoryDelegate.findAll());
				model.addAttribute("salespersons", this.salesPersonDelegate.findAll());

				return "/salesterritoryhistory/add-salesterritoryhistory";
			}

			if (sth.getModifieddate().compareTo(sth.getEnddate()) >= 0) {
				model.addAttribute("invalidDate", true);
				
				model.addAttribute("salesterritories", this.salesTerritoryDelegate.findAll());
				model.addAttribute("salespersons", this.salesPersonDelegate.findAll());
				
				return "/salesterritoryhistory/add-salesterritoryhistory";
			}
			System.out.println(sth.getEnddate());
			System.out.println(sth.getModifieddate());
			System.out.println(sth.getSalesperson());
			System.out.println(sth.getSalesperson().getBusinessentityid());
			System.out.println(sth.getSalesterritory().getTerritoryid());
			this.salesTerritoyHistoryDelegate.save(sth);
		}
		return "redirect:/salesterritoryhistory/";
	}

	@GetMapping("/salesterritoryhistory/edit/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Optional<Salesterritoryhistory> user = Optional.of(this.salesTerritoyHistoryDelegate.findById(id));

		if (user == null)
			throw new IllegalArgumentException("Invalid user Id:" + id);
		model.addAttribute("saleterritoryhistory", user.get());

		model.addAttribute("salesterritories", this.salesTerritoryDelegate.findAll());
		model.addAttribute("salespersons", this.salesPersonDelegate.findAll());

		return "salesterritoryhistory/update-salesterritoryhistory";
	}

	@PostMapping("/salesterritoryhistory/edit/{id}")
	public String updateUser(@Validated @ModelAttribute Salesterritoryhistory sth, BindingResult bindingResult,
			Model model, @PathVariable("id") Integer id,
			@RequestParam(value = "action", required = true) String action) {
		if (action != null && !action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("salesterritories", this.salesTerritoryDelegate.findAll());
				model.addAttribute("salespersons", this.salesPersonDelegate.findAll());

				return "/salesterritoryhistory/update-salesterritoryhistory";
			}

			Salesterritoryhistory salesp = sth;
			salesp.setId(id);

			this.salesTerritoyHistoryDelegate.edit(salesp);
			model.addAttribute("salesterritories", this.salesTerritoyHistoryDelegate.findAll());
		}
		return "redirect:/salesterritoryhistory/";
	}

	@GetMapping("/salesterritoryhistory/show-salesperson/{id}")
	public String showSalesperson(@PathVariable("id") Integer id, Model model) {

		Optional<Salesperson> user = this.salesPersonDelegate.findById(id);

		if (user == null)
			throw new IllegalArgumentException("Invalid user Id:" + id);

		ArrayList<Salesperson> sps = new ArrayList<Salesperson>();
		sps.add(user.get());

		model.addAttribute("salespersons", sps);

		return "info/salesperson-info";
	}

	@GetMapping("/salesterritoryhistory/show-salesterritory/{id}")
	public String showSalesterritory(@PathVariable("id") Integer id, Model model) {

		Optional<Salesterritory> user = this.salesTerritoryDelegate.findById(id);

		if (user == null)
			throw new IllegalArgumentException("Invalid user Id:" + id);

		ArrayList<Salesterritory> sts = new ArrayList<Salesterritory>();
		sts.add(user.get());

		model.addAttribute("salesterritories", sts);

		return "info/salesterritory-info";
	}

}
