package co.edu.icesi.dev.uccareapp.transport.controller.implementation;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Size;

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
import co.edu.icesi.dev.uccareapp.transport.service.implementation.SalesPersonServiceImp;

@Controller
public class SalesPersonControllerImp implements SalesPersonController {

	SalesPersonServiceImp salesPersonService;

	@Autowired
	public SalesPersonControllerImp(SalesPersonServiceImp salesPersonService) {
		this.salesPersonService = salesPersonService;
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		return "/login";
	}

	@GetMapping("/salesperson/add")
	public String addSalesPerson(Model model) {
		model.addAttribute("salesPerson", new Salesperson());
		return "salesperson/add";
	}

	@GetMapping("/salesperson/")
	public String indexSalesPerson(Model model) {
		model.addAttribute("salesPersons", salesPersonService.findAll());
		return "salesperson/index";
	}

	@PostMapping("/salesperson/add")
	public String saveUser(@Validated @ModelAttribute Salesperson sp, BindingResult bindingResult,
			Model model, @RequestParam(value = "action", required = true) String action) {

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
		model.addAttribute("Salesperson", user.get());
		return "salesperson/update-salesperson";
	}

	@PostMapping("/salesperson/edit/{id}")
	public String updateUser(@Validated @ModelAttribute Salesperson sp,
			BindingResult bindingResult, Model model, @PathVariable("id") Integer id,
			@RequestParam(value = "action", required = true) String action, String newPassword) {
		if (action != null && !action.equals("Cancel")) {
			
			if (bindingResult.hasErrors()) {
				return "/salesperson/update-salesperson";
			}

			Salesperson u = salesPersonService.findById(sp.getBusinessentityid()).get();

			salesPersonService.save(u);
			model.addAttribute("salespersons", salesPersonService.findAll());
		}
		return "redirect:/users/";
	}
}
