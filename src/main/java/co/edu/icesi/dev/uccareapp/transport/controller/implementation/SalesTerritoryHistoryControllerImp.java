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
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritoryhistory;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesPersonServiceImp;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesTerritoryHistoryServiceImp;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesTerritoryServiceImp;

@Controller
public class SalesTerritoryHistoryControllerImp implements SalesTerritoryHistoryController {

	@Autowired
	SalesTerritoryHistoryServiceImp salesTerritoryHistoryService;
	@Autowired
	SalesPersonServiceImp salesPersonService;
	@Autowired
	SalesTerritoryServiceImp salesTerritoryService;

	@GetMapping("/salesterritoryhistory/add")
	public String addSalesTerritoryHistory(Model model) {

		model.addAttribute("salesterritoryhistory", new Salesterritoryhistory());

		model.addAttribute("salesterritories", this.salesTerritoryService.findAll());
		model.addAttribute("salespersons", this.salesPersonService.findAll());

		return "salesterritoryhistory/add-salesterritoryhistory";
	}

	@GetMapping("/salesterritoryhistory/")
	public String indexSalesPerson(Model model) {
		model.addAttribute("salesterritoryhistories", this.salesTerritoryHistoryService.findAll());

		return "salesterritoryhistory/index";
	}

	@PostMapping("/salesterritoryhistory/add")
	public String saveUser(@Validated @ModelAttribute Salesterritoryhistory sth, BindingResult bindingResult,
			Model model, @RequestParam(value = "action", required = true) String action) {

		if (!action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				System.out.println("****************fffffffffffffff" + "*");
				return "/salesterritoryhistory/add";
			}
			sth = this.salesTerritoryHistoryService.save(sth);
			System.out.println("Id" + sth.getId());

			System.out.println("Salesperson" + sth.getSalesperson());

			System.out.println("Salesterritory" + sth.getSalesterritory());

			model.addAttribute("id", sth.getId());
		}
		return "redirect:/salesterritoryhistory/";
	}

	@GetMapping("/salesterritoryhistory/edit/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Optional<Salesterritoryhistory> user = this.salesTerritoryHistoryService.findById(id);

		if (user == null)
			throw new IllegalArgumentException("Invalid user Id:" + id);
		model.addAttribute("saleterritoryhistory", user.get());

		model.addAttribute("salesterritories", this.salesTerritoryService.findAll());
		model.addAttribute("salespersons", this.salesPersonService.findAll());

		return "salesterritoryhistory/update-salesterritoryhistory";
	}

	@PostMapping("/salesterritoryhistory/edit/{id}")
	public String updateUser(@Validated @ModelAttribute Salesterritoryhistory sth, BindingResult bindingResult,
			Model model, @PathVariable("id") Integer id,
			@RequestParam(value = "action", required = true) String action) {
		if (action != null && !action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				return "/salesterritoryhistory/update-salesterritoryhistory";
			}

			Salesterritoryhistory salesp = sth;
			salesp.setId(id);

			this.salesTerritoryHistoryService.edit(salesp);
			model.addAttribute("salesterritories", this.salesTerritoryHistoryService.findAll());
		}
		return "redirect:/salesterritoryhistory/";
	}

	@GetMapping("/salesterritoryhistory/show-salesperson/{id}")
	public String showSalesperson(@PathVariable("id") Integer id, Model model) {
		
		Optional<Salesperson> user = this.salesPersonService.findById(id);

		if (user == null)
			throw new IllegalArgumentException("Invalid user Id:" + id);
		
		ArrayList<Salesperson> sps = new ArrayList<Salesperson>();
		sps.add(user.get());
		
		model.addAttribute("salespersons", sps);

		return "info/salesperson-info";
	}
	
	@GetMapping("/salesterritoryhistory/show-salesterritory/{id}")
	public String showSalesterritory(@PathVariable("id") Integer id, Model model) {
		
		Optional<Salesterritory> user = this.salesTerritoryService.findById(id);

		if (user == null)
			throw new IllegalArgumentException("Invalid user Id:" + id);
		
		ArrayList<Salesterritory> sts = new ArrayList<Salesterritory>();
		sts.add(user.get());
		
		model.addAttribute("salesterritories", sts);

		return "info/salesterritory-info";
	}

}