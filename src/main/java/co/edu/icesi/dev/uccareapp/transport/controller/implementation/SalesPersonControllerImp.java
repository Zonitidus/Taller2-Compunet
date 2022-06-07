package co.edu.icesi.dev.uccareapp.transport.controller.implementation;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
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
import co.edu.icesi.dev.uccareapp.transport.delegate.SalesPersonDelegate;
import co.edu.icesi.dev.uccareapp.transport.delegate.SalesTerritoryDelegate;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesperson;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritory;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Salesterritoryhistory;
import co.edu.icesi.dev.uccareapp.transport.repository.BusinessEntittyRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.SalesTerritoryRepository;
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesPersonServiceImp;

@Controller
public class SalesPersonControllerImp implements SalesPersonController {
	
	@Autowired
	SalesPersonDelegate salespersondelegate;
	@Autowired
	SalesTerritoryDelegate salesterritorydelegate;
	@Autowired
	BusinessEntittyRepository businesentitEntittyRepository;

	@GetMapping("/login")
	public String login(Model model) {
		return "/login";
	}

	@GetMapping("/salesperson/add")
	public String addSalesPerson(Model model) {

		model.addAttribute("salesperson", new Salesperson());
		model.addAttribute("businessentities", businesentitEntittyRepository.findAll());
		model.addAttribute("salesterritories", salesterritorydelegate.findAll());
		
		return "salesperson/add-salesperson";
	}

	@GetMapping("/salesperson/")
	public String indexSalesPerson(Model model) {
		model.addAttribute("salespersons", salespersondelegate.findAll());
		
		return "salesperson/index";
	}

	@PostMapping("/salesperson/add")
	public String saveUser(@Validated @ModelAttribute Salesperson sp, BindingResult bindingResult, Model model,
			@RequestParam(value = "action", required = true) String action) {

		if (!action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				
				model.addAttribute("businessentities", businesentitEntittyRepository.findAll());
				model.addAttribute("salesterritories", salesterritorydelegate.findAll());
				
				System.out.println("****************fffffffffffffff" + "*");
				return "/salesperson/add-salesperson";
			}
			salespersondelegate.save(sp);
			System.out.println("**********" + sp.getBusinessentityid());
			model.addAttribute("id", sp.getBusinessentityid());
		}
		return "redirect:/salesperson/";
	}

	@GetMapping("/salesperson/edit/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Optional<Salesperson> user = salespersondelegate.findById(id);
		
		if (user == null)
			throw new IllegalArgumentException("Invalid user Id:" + id);
		model.addAttribute("salesperson", user.get());
		model.addAttribute("salesterritories", salesterritorydelegate.findAll());
		
		return "salesperson/update-salesperson";
	}

	@PostMapping("/salesperson/edit/{id}")
	public String updateUser(@Validated @ModelAttribute Salesperson sp, BindingResult bindingResult, Model model,
			@PathVariable("id") Integer businessentityid, @RequestParam(value = "action", required = true) String action) {
		if (action != null && !action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				
				model.addAttribute("businessentities", businesentitEntittyRepository.findAll());
				model.addAttribute("salesterritories", salesterritorydelegate.findAll());
				
				return "/salesperson/update-salesperson";
			}
			
			
			Salesperson salesp = sp;
			salesp.setBusinessentityid(businessentityid);
			
			salespersondelegate.edit(salesp);
			model.addAttribute("salespersons", salespersondelegate.findAll());
		}
		return "redirect:/salesperson/";
	}
	
	@GetMapping("/salesperson/show-salesterritory/{id}")
	public String showSalesterritory(@PathVariable("id") Integer id, Model model) {
		
		Optional<Salesterritory> user = this.salesterritorydelegate.findById(id);

		if (user == null)
			throw new IllegalArgumentException("Invalid user Id:" + id);
		
		ArrayList<Salesterritory> sts = new ArrayList<Salesterritory>();
		sts.add(user.get());
		
		model.addAttribute("salesterritories", sts);

		return "info/salesterritory-info";
	}
	
	@GetMapping("/salesperson/customquery")
	public String salesPersonCustomQueryGet(Model model) {
		
		model.addAttribute("salesterritories", this.salesterritorydelegate.findAll());
		
		model.addAttribute("salesterritoryhistory", new Salesterritoryhistory());
		model.addAttribute("minDate", new Date());
		model.addAttribute("maxDate", new Date());

		return "salesperson/customquery";
	}
	
	@PostMapping("/salesperson/customquery")
	public String saveUser(@Validated @ModelAttribute Salesterritoryhistory sth, BindingResult bindingResult,
			Model model, @RequestParam(value = "action", required = true) String action) {

		if (!action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				
				model.addAttribute("salesterritories", this.salesterritorydelegate.findAll());
				
				System.out.println("****************fffffffffffffff" + "*");
				return "/salesperson/customquery";
			}
			
			if (sth.getModifieddate().compareTo(sth.getEnddate()) >= 0) {
				model.addAttribute("invalidDate", true);
				
				model.addAttribute("salesterritories", this.salesterritorydelegate.findAll());
				
				/*model.addAttribute("salesterritoryhistory", new Salesterritoryhistory());
				model.addAttribute("minDate", new Date());
				model.addAttribute("maxDate", new Date());
				*/
				return "/salesperson/customquery";
			}
			
			Salesterritory st = sth.getSalesterritory();
			Date minDate = sth.getModifieddate();
			Date maxDate = sth.getEnddate();
			
			System.out.println(st.getTerritoryid());
			System.out.println(minDate);
			System.out.println(maxDate);
			
			/*st = this.salesterritorydelegate.findById(1).get();
			minDate = Timestamp.valueOf(LocalDateTime.now().minusDays(20));
			maxDate = Timestamp.valueOf(LocalDateTime.now().minusDays(0));*/
			
			
			Iterable<Salesperson> spmap = this.salespersondelegate.customQuery(st, minDate, maxDate);
			
			/*Object[] sps = spmap.keySet().toArray();
			
			ArrayList<Salesperson> salespersons = new ArrayList<Salesperson>();
			
			for(int i = 0; i < sps.length; i++) {
				
				Salesperson sptemp = ((Salesperson) sps[i]);
				salespersons.add(sptemp);
				
				System.out.println("========\n"+"Salesperson: "+sptemp.getBusinessentityid()+"\n");
				System.out.println("Count: "+spmap.get(sptemp));
				System.out.println("\n========\n");
			}*/
			
			model.addAttribute("salespersons", spmap);
		}
		return "salesperson/customquery-result";
	}

}
