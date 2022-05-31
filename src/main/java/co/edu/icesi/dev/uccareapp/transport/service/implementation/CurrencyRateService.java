package co.edu.icesi.dev.uccareapp.transport.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.icesi.dev.uccareapp.transport.daos.CurrencyRateDAO;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Currencyrate;
import co.edu.icesi.dev.uccareapp.transport.service.interfaces.ICurrencyRateService;

public class CurrencyRateService implements ICurrencyRateService{

	@Autowired
	private CurrencyRateDAO currencyRateDAO;
	
	@Override
	public void save(Currencyrate spqh) {
		// TODO Auto-generated method stub
		currencyRateDAO.save(spqh);
	}

	@Override
	public void edit(Currencyrate spqh) {
		// TODO Auto-generated method stub
		currencyRateDAO.update(spqh);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub}
		Currencyrate cr = currencyRateDAO.findById(id).get();
		currencyRateDAO.delete(cr);
	}

	@Override
	public Optional<Currencyrate> findByID(Integer id) {
		// TODO Auto-generated method stub
		return currencyRateDAO.findById(id);
	}

	@Override
	public Iterable<Currencyrate> findAll() {
		// TODO Auto-generated method stub
		return currencyRateDAO.findAll();
	}

}
