package org.alexandrialibrary.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DefaultController extends AbstractController {
	
	/**
	 * Muestra la página de Inicio
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		logger.info("Iniciamos default/index [GET]");
		
		return "default/index";
	}
	
	/**
	 * Muestra la página de Acerca de
	 */
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String about() {
		logger.info("Iniciamos default/about [GET]");
		
		return "default/about";
	}
	
}
