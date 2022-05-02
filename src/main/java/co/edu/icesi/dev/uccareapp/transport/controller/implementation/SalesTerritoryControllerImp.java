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

import co.edu.icesi.dev.uccareapp.transport.controller.interfaces.SalesTerritoryController;
import co.edu.icesi.dev.uccareapp.transport.model.person.Countryregion;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salespersonquotahistory;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.repository.CountryRegionRepository;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesTerritoryServiceImp;

@Controller
public class SalesTerritoryControllerImp implements SalesTerritoryController {

	@Autowired
	SalesTerritoryServiceImp salesTerritoryServiceImp;
	@Autowired
	CountryRegionRepository countryRegionRepo;
	
	
	@GetMapping("/salesterritory/add")
	public String addSalesPerson(Model model) {

		model.addAttribute("salesterritory", new Salesterritory());
		model.addAttribute("countryregioncodes", countryRegionRepo.findAll());

		return "salesterritory/add-salesterritory";
	}

	@GetMapping("/salesterritory/")
	public String indexSalesPerson(Model model) {
		model.addAttribute("salesterritories", salesTerritoryServiceImp.findAll());

		return "salesterritory/index";
	}

	@PostMapping("/salesterritory/add")
	public String saveUser(@Validated @ModelAttribute Salesterritory st, BindingResult bindingResult,
			Model model, @RequestParam(value = "action", required = true) String action) {

		if (!action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("countryregioncodes", countryRegionRepo.findAll());
				System.out.println("****************fffffffffffffff" + "*");
				return "/salesterritory/add-salesterritory";
			}
			st = salesTerritoryServiceImp.save(st);
			model.addAttribute("id", st.getTerritoryid());
		}
		return "redirect:/salesterritory/";
	}

	@GetMapping("/salesterritory/edit/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

		Optional<Salesterritory> user = salesTerritoryServiceImp.findById(id);

		if (user == null)
			throw new IllegalArgumentException("Invalid user Id:" + id);
		model.addAttribute("salesterritory", user.get());
		model.addAttribute("countryregioncodes", countryRegionRepo.findAll());

		return "salesterritory/update-salesterritory";
	}

	@PostMapping("/salesterritory/edit/{id}")
	public String updateUser(@Validated @ModelAttribute Salesterritory st, BindingResult bindingResult, Model model,
			@PathVariable("id") Integer id,
			@RequestParam(value = "action", required = true) String action) {
		if (action != null && !action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("countryregioncodes", countryRegionRepo.findAll());
				return "/salesterritory/update-salesterritory";
			}

			Salesterritory u = st;
			u.setTerritoryid(id);

			salesTerritoryServiceImp.edit(u);
			model.addAttribute("salesterritories", salesTerritoryServiceImp.findAll());
		}
		return "redirect:/salesterritory/";
	}
	
	@GetMapping("/salesterritory/show-countryregion/{id}")
	public String showSalesterritory(@PathVariable("id") String id, Model model) {
		
		Optional<Countryregion> user = this.countryRegionRepo.findById(id);

		if (user == null)
			throw new IllegalArgumentException("Invalid user Id:" + id);
		
		ArrayList<Countryregion> sts = new ArrayList<Countryregion>();
		sts.add(user.get());
		
		model.addAttribute("countryregioncodes", sts);

		return "info/countryregioncode-info";
	}
}
