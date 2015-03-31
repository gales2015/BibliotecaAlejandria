package org.alexandrialibrary.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DefaultController extends AbstractController {
	
	/**
	 * Muestra la página de inicio
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		logger.info("Iniciamos default/index [GET]");
		
		return "default/index";
	}
	
}
