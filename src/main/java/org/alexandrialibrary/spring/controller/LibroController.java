package org.alexandrialibrary.spring.controller;

import java.util.Arrays;
import java.util.List;

import org.alexandrialibrary.spring.bean.Ejemplar;
import org.alexandrialibrary.spring.bean.Libro;
import org.alexandrialibrary.spring.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/libro")
public class LibroController extends AbstractController {
	
	@Autowired
	private LibroService libroService;

	/**
	 * Listado de libros
	 * 
	 * También permite buscar por título parcial de libros.
	 * 
	 * Ejemplo: /?titulo=anillos
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(@RequestParam(value = "titulo", required = false) String titulo, Model model) {
		logger.info("Iniciamos libro/index [GET]");
		
		Libro libro = null;
		List<Libro> libros = null;
		
		if (titulo != null && !titulo.equals("")) {
			// Si estamos buscando por título parcial:
			// - Guardamos el nombre en el formulario "libro"
			// - Obtenemos la lista de libros coincidentes
			libro = new Libro(titulo);
			libros = libroService.getLibrosByTituloParcial(titulo);
		} else {
			// Si no estamos buscando:
			// - Creamos un formulario "libro" en blanco
			// - Obtenemos la lista de libros por defecto
			libro = new Libro();
			libros = libroService.getAllLibros();
		}

		logger.info("Pasamos el formulario y el listado de libros a la vista.");
		model.addAttribute("libro", libro);
		model.addAttribute("libros", libros);

		return "libro/index";
	}
	
	/**
	 * Ver un libro concreto [GET]
	 * 
	 * @param isbn
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ver/{isbn}", method = RequestMethod.GET)
	public String ver(@PathVariable Long isbn, Model model) {
		logger.info("Iniciamos libro/ver/{isbn} [GET]");
		
		Libro libro = libroService.getLibro(isbn);		
		model.addAttribute("libro", libro);
		
		return "libro/ver";
	}
	
	/**
	 * Recibe por AJAX la petición para mostrar las IDs de los Ejemplares disponibles de un Libro en concreto
	 * 
	 * @param isbn
	 * @return
	 */
	@RequestMapping(value = "/ejemplares-libres.json", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody long[] ejemplares(@RequestParam(value = "isbn", required = true) Long isbn) {
		logger.info("Iniciamos libro/ejemplares.json?isbn={isbn} [GET]");
		
		Libro libro = libroService.getLibro(isbn);
		long[] ejemplares = new long[libro.getTotalEjemplaresLibres()];
		int index = 0;
		
		for (Ejemplar ejemplar : libro.getEjemplares()) {
			if (!ejemplar.isPrestado()) {
				ejemplares[index] = ejemplar.getId();
				index++;
			}
		}
		
		logger.info("IDs de Ejemplares disponibles: " + Arrays.toString(ejemplares));

		return ejemplares;
	}
	

}
