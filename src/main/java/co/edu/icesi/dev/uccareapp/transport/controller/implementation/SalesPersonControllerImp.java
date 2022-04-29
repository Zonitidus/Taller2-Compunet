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

import co.edu.icesi.dev.uccareapp.transport.controller.interfaces.SalesPersonController;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.repository.BusinessEntittyRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryRepository;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesPersonServiceImp;

@Controller
public class SalesPersonControllerImp implements SalesPersonController {

	SalesPersonServiceImp salesPersonService;
	BusinessEntittyRepository businessEntityRepository;
	SalesTerritoryRepository salesTerritoryRepository;

	@Autowired
	public SalesPersonControllerImp(SalesPersonServiceImp salesPersonService,
			BusinessEntittyRepository businessEntityRepository, SalesTerritoryRepository salesTerritoryRepository) {
		this.salesPersonService = salesPersonService;
		this.businessEntityRepository = businessEntityRepository;
		this.salesTerritoryRepository = salesTerritoryRepository;
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "/login";
	}

	@GetMapping("/salesperson/add")
	public String addSalesPerson(Model model) {

		model.addAttribute("salesperson", new Salesperson());
		model.addAttribute("businessentities", businessEntityRepository.findAll());
		model.addAttribute("salesterritories", salesTerritoryRepository.findAll());
		
		return "salesperson/add-salesperson";
	}

	@GetMapping("/salesperson/")
	public String indexSalesPerson(Model model) {
		model.addAttribute("salespersons", salesPersonService.findAll());
		
		return "salesperson/index";
	}

	@PostMapping("/salesperson/add")
	public String saveUser(@Validated @ModelAttribute Salesperson sp, BindingResult bindingResult, Model model,
			@RequestParam(value = "action", required = true) String action) {

		if (!action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				System.out.println("****************fffffffffffffff" + "*");
				return "/salesperson/add";
			}
			sp = salesPersonService.save(sp);
			System.out.println("**********" + sp.getBusinessentityid());
			model.addAttribute("id", sp.getBusinessentityid());
		}
		return "redirect:/salesperson/";
	}

	@GetMapping("/salesperson/edit/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Optional<Salesperson> user = salesPersonService.findById(id);
		
		if (user == null)
			throw new IllegalArgumentException("Invalid user Id:" + id);
		model.addAttribute("salesperson", user.get());
		model.addAttribute("salesterritories", salesTerritoryRepository.findAll());
		
		return "salesperson/update-salesperson";
	}

	@PostMapping("/salesperson/edit/{id}")
	public String updateUser(@Validated @ModelAttribute Salesperson sp, BindingResult bindingResult, Model model,
			@PathVariable("id") Integer businessentityid, @RequestParam(value = "action", required = true) String action) {
		if (action != null && !action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				return "/salesperson/update-salesperson";
			}
			
			
			Salesperson salesp = sp;
			salesp.setBusinessentityid(businessentityid);
			
			salesPersonService.edit(salesp);
			model.addAttribute("salespersons", salesPersonService.findAll());
		}
		return "redirect:/salesperson/";
	}
	
	@GetMapping("/salesperson/show-salesterritory/{id}")
	public String showSalesterritory(@PathVariable("id") Integer id, Model model) {
		
		Optional<Salesterritory> user = this.salesTerritoryRepository.findById(id);

		if (user == null)
			throw new IllegalArgumentException("Invalid user Id:" + id);
		
		ArrayList<Salesterritory> sts = new ArrayList<Salesterritory>();
		sts.add(user.get());
		
		model.addAttribute("salesterritories", sts);

		return "info/salesterritory-info";
	}
}
