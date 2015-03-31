package org.alexandrialibrary.spring.controller;

import java.util.List;

import org.alexandrialibrary.spring.bean.Ejemplar;
import org.alexandrialibrary.spring.dao.EjemplarDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/ejemplar")
public class EjemplarController extends AbstractController {

	@Autowired
	private EjemplarDAO ejemplarDAO;

	/**
	 * Listado de ejemplars
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		logger.info("Iniciamos ejemplar/index [GET]");
		List<Ejemplar> ejemplars = ejemplarDAO.getAllEjemplar();
		model.addAttribute("ejemplars", ejemplars);

		logger.info("Pasamos el listado de ejemplars a la vista.");
		return "ejemplar/index";
	}

	/**
	 * Ver un ejemplar concreto [GET]
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ver/{id}", method = RequestMethod.GET)
	public String ver(@PathVariable Long id, Model model) {
		logger.info("Iniciamos ejemplar/ver/{id} [GET]");

		Ejemplar ejemplar = ejemplarDAO.getEjemplar(id);
		model.addAttribute("ejemplar", ejemplar);

		return "ejemplar/ver";
	}

}
