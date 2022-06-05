package co.edu.icesi.dev.uccareapp.transport.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.dev.uccareapp.transport.daos.CurrencyDAO;
import co.edu.icesi.dev.uccareapp.transport.model.sales.Currency;
import co.edu.icesi.dev.uccareapp.transport.service.interfaces.ICurrencyService;

@Service
@Transactional
public class CurrencyService implements ICurrencyService {

	@Autowired
	private CurrencyDAO currencydao;
	
	@Override
	public void save(Currency c) {
		// TODO Auto-generated method stub
		this.currencydao.save(c);
	}

	@Override
	public void edit(Currency c) {
		// TODO Auto-generated method stub
		this.currencydao.update(c);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		Currency toDel = this.currencydao.findById(id).get();
		this.currencydao.delete(toDel);
	}

	@Override
	public Optional<Currency> findByID(String id) {
		// TODO Auto-generated method stub
		return this.currencydao.findById(id);
	}

	@Override
	public Iterable<Currency> findAll() {
		// TODO Auto-generated method stub
		return this.currencydao.findAll();
	}

}
