package org.iesvdm.comercial.servicio;

import java.util.List;
import java.util.Optional;

import org.iesvdm.comercial.dao.ComercialDAO;
import org.iesvdm.comercial.modelo.Comercial;
import org.springframework.stereotype.Service;

@Service
public class ComercialService {
	private final ComercialDAO comercialDAO;

	public ComercialService(ComercialDAO comercialDAO) {
		this.comercialDAO = comercialDAO;
	}

	public void create(Comercial comercial) {
		comercialDAO.create(comercial);
	}

	public List<Comercial> getAll() {
		return comercialDAO.getAll();
	}

	public Optional<Comercial> find(int id) {
		return comercialDAO.find(id);
	}

	public void update(Comercial comercial) {
		comercialDAO.update(comercial);
	}

	public void delete(int id) {
		comercialDAO.delete(id);
	}
}