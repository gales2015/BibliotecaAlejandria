package org.alexandrialibrary.spring.controller;

import java.util.Arrays;
import java.util.List;

import org.alexandrialibrary.spring.model.Ejemplar;
import org.alexandrialibrary.spring.model.Libro;
import org.alexandrialibrary.spring.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Libro controller.
 * 
 * Contendrá las rutas para todo lo relacionado con libros:
 *  - Listado de libros
 *  - Ver un libro concreto
 *  - Petición por Ajax para mostrar las ID's de los ejemplares disponibles 
 *    (no prestados en este momento) de un libro concreto.
 * 
 */
@Controller
@RequestMapping("/libro") // Prefijo para todas las rutas del controlador.
public class LibroController extends AbstractController {
	
	/**
	 * Servicio de libros, que llamará al DAO de libros, que interactuará con Hibernate para las consultas.
	 * 
	 * No necesita ser instanciado gracias a la anotación @Autowired.
	 */
	@Autowired
	private LibroService libroService;

	/**
	 * Listado de libros
	 * 
	 * También permite buscar por título parcial de libros.
	 * 
	 * Ejemplo: /?titulo=anillos
	 * 
	 * @param titulo
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
		// Los atributos pasados al modelo serán recibidos por la vista.
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
		
		// Obtenemos el libro al que corresponda el isbn y lo pasamos a la vista.
		Libro libro = libroService.getLibro(isbn);		
		model.addAttribute("libro", libro);
		
		return "libro/ver";
	}
	
	/**
	 * Recibe por AJAX la petición para mostrar las IDs de los Ejemplares disponibles de un Libro en concreto.
	 * 
	 * Con la anotación @ResponseBody y la dependencia jackson-mapper-asl la respuesta se parseará a JSON.
	 * 
	 * @param isbn
	 * @return
	 */
	@RequestMapping(value = "/ejemplares-libres.json", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody long[] ejemplares(@RequestParam(value = "isbn", required = true) Long isbn) {
		logger.info("Iniciamos libro/ejemplares.json?isbn={isbn} [GET]");
		
		// Obtenemos el libro y creamos un array con tantas posibiciones como ejemplares libres
		Libro libro = libroService.getLibro(isbn);
		long[] ejemplares = new long[libro.getTotalEjemplaresLibres()];
		int index = 0;
		
		// Por cada ejemplar libre, insertamos su ID en el array.
		for (Ejemplar ejemplar : libro.getEjemplares()) {
			if (!ejemplar.isPrestado()) {
				ejemplares[index] = ejemplar.getId();
				index++;
			}
		}
		
		logger.info("IDs de Ejemplares disponibles: " + Arrays.toString(ejemplares));

		// Devolvemos el array de ID's, que será parseado a JSON
		return ejemplares;
	}
	

}
