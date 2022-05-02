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

	SalesPersonRepository salesPersonRepo;
	SalesPersonQuotaHistoryRepository salesPersonQuotaHistoryRepo;
	SalesPersonQuotaHistoryServiceImp salesPersonQuotaHistoryService;

	@Autowired
	public SalesPersonQuotaHistoryControllerImp(SalesPersonRepository salesPersonRepo,
			SalesPersonQuotaHistoryRepository salesPersonQuotaHistoryRepo,
			SalesPersonQuotaHistoryServiceImp salesPersonQuotaHistoryService) {
		super();
		this.salesPersonRepo = salesPersonRepo;
		this.salesPersonQuotaHistoryRepo = salesPersonQuotaHistoryRepo;
		this.salesPersonQuotaHistoryService = salesPersonQuotaHistoryService;
	}

	@GetMapping("/salespersonquotahistory/add")
	public String addSalesPerson(Model model) {

		model.addAttribute("salespersonquotahistory", new Salespersonquotahistory());
		model.addAttribute("salespersons", salesPersonRepo.findAll());

		return "salespersonquotahistory/add-salespersonquotahistory";
	}

	@GetMapping("/salespersonquotahistory/")
	public String indexSalesPerson(Model model) {
		model.addAttribute("salespersonquotahistories", salesPersonQuotaHistoryRepo.findAll());

		return "salespersonquotahistory/index";
	}

	@PostMapping("/salespersonquotahistory/add")
	public String saveUser(@Validated @ModelAttribute Salespersonquotahistory spqh, BindingResult bindingResult,
			Model model, @RequestParam(value = "action", required = true) String action) {

		if (!action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("salespersons", salesPersonRepo.findAll());
				System.out.println("****************fffffffffffffff" + "*");
				return "/salespersonquotahistory/add-salespersonquotahistory";
			}
			
			System.out.println(spqh.getSalesperson().getBusinessentityid());
			System.out.println(spqh.getSalesperson().getSalesquota());
			
			
			spqh = salesPersonQuotaHistoryService.save(spqh);
			model.addAttribute("id", spqh.getId());
		}
		return "redirect:/salespersonquotahistory/";
	}

	@GetMapping("/salespersonquotahistory/edit/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

		Optional<Salespersonquotahistory> user = salesPersonQuotaHistoryRepo.findById(id);

		if (user == null)
			throw new IllegalArgumentException("Invalid user Id:" + id);
		model.addAttribute("salespersonquotahistory", user.get());
		model.addAttribute("salespersons", salesPersonRepo.findAll());

		return "salespersonquotahistory/update-salespersonquotahistory";
	}

	@PostMapping("/salespersonquotahistory/edit/{id}")
	public String updateUser(@Validated @ModelAttribute Salespersonquotahistory spqh, BindingResult bindingResult, Model model,
			@PathVariable("id") Integer id,
			@RequestParam(value = "action", required = true) String action) {
		if (action != null && !action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("salespersons", salesPersonRepo.findAll());
				return "/salespersonquotahistory/update-salespersonquotahistory";
			}

			Salespersonquotahistory u = spqh;
			u.setId(id);

			salesPersonQuotaHistoryService.edit(u);
			model.addAttribute("salespersonquotahistories", salesPersonQuotaHistoryRepo.findAll());
		}
		return "redirect:/salespersonquotahistory/";
	}
}
