package org.alexandrialibrary.spring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.alexandrialibrary.spring.editor.EjemplarEditor;
import org.alexandrialibrary.spring.editor.UsuarioEditor;
import org.alexandrialibrary.spring.form.PrestamoIsDevueltoForm;
import org.alexandrialibrary.spring.model.Ejemplar;
import org.alexandrialibrary.spring.model.Libro;
import org.alexandrialibrary.spring.model.Prestamo;
import org.alexandrialibrary.spring.model.Usuario;
import org.alexandrialibrary.spring.service.EjemplarService;
import org.alexandrialibrary.spring.service.LibroService;
import org.alexandrialibrary.spring.service.PrestamoService;
import org.alexandrialibrary.spring.service.UsuarioService;
import org.alexandrialibrary.spring.util.PrestamoFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Prestamo controller.
 * 
 * Contendrá las rutas para todo lo relacionado con préstamos:
 *  - Listado de préstamos
 *  - Crear un nuevo préstamo
 *  - Devolver un préstamo
 * 
 */
@Controller
@RequestMapping("/prestamo") // Prefijo para todas las rutas del controlador.
public class PrestamoController extends AbstractController {
	
	/**
	 * Servicios de préstamos, usuarios, libros y ejemplares, que llamarán a sus respectivos DAOs, 
	 * que interactuará con Hibernate para las consultas.
	 * 
	 * No necesitan ser instanciados gracias a la anotación @Autowired.
	 */
	@Autowired
	private PrestamoService prestamoService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private LibroService libroService;
	
	@Autowired
	private EjemplarService ejemplarService;

	/**
	 * Validador de préstamos que será utilizado al recibir por post un formulario enviado.
	 */
	@Autowired
	private PrestamoFormValidator validator;
	
	/**
	 * Editores de usuario y ejemplar. Se utilizarán en el initBinder().
	 * 
	 * Gracias a ellos, cuando venga una ID de usuario o ejemplar, 
	 * ésta será convertida en el objeto Usuario o Ejemplar correspondiente.
	 */
	@Autowired
	private UsuarioEditor usuarioEditor;

	@Autowired
	private EjemplarEditor ejemplarEditor;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Usuario.class, this.usuarioEditor);
        binder.registerCustomEditor(Ejemplar.class, this.ejemplarEditor);
    }

	/**
	 * Listado de préstamos
	 * 
	 * También permite filtrar por préstamos devueltos o pendientes.
	 * 
	 * Ejemplo: /?devuelto=1
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(@RequestParam(value = "devuelto", required = false) Integer devueltoInt, Model model) {
		logger.info("Iniciamos prestamo/index [GET]");
		
		// Creamos el formulario de filtrado de préstamos (todos/pendientes/devueltos)
		PrestamoIsDevueltoForm prestamoForm = null;
		
		// El listado de préstamos se llenará de unos objetos u otros dependiendo 
		// de lo que esté seleccionado en el formulario de filtrado de préstamos.
		List<Prestamo> prestamos = null;
		
		
		if (devueltoInt != null && devueltoInt >= 0) {
			// False -> 0
			// True -> Valor positivo
			Integer devuelto = (devueltoInt > 0) ? 1 : 0;
			
			prestamoForm = new PrestamoIsDevueltoForm(devuelto);
			prestamos = prestamoService.getPrestamosByDevuelto((devuelto == 1));
		} else {
			// Sin especificar o valor negativo
			prestamoForm = new PrestamoIsDevueltoForm();
			prestamos = prestamoService.getAllPrestamos();
		}

		// Pasamos a la vista el formulario de préstamo (para filtrar) y el listado de préstamos
		logger.info("Pasamos el formulario y el listado de prestamos a la vista.");
		model.addAttribute("prestamoForm", prestamoForm);
		model.addAttribute("prestamos", prestamos);
		
		// Creamos las opciones del select de filtrado de préstamos.
		HashMap<Integer, String> arrDevuelto = new HashMap<Integer, String>();
		arrDevuelto.put(1, "Devuelto");
		arrDevuelto.put(0, "Pendiente");
	    model.addAttribute("arrDevuelto", arrDevuelto);
		
		return "prestamo/index";
	}

	/**
	 * Formulario de nuevo préstamo [GET]
	 * 
	 * Se pueden añadir a la URL las IDs de Usuario y/o Libro. Ejemplo:
	 * 
	 * prestamo/nuevo?usuario=1
	 * prestamo/nuevo?usuario=1&ejemplar=2
	 * 
	 * @param usuario_id
	 * @param ejemplar_id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/nuevo", method = RequestMethod.GET)
	public String nuevo(@RequestParam(value = "usuario", required = false) Long usuario_id, 
			@RequestParam(value = "ejemplar", required = false) Long ejemplar_id, Model model) {
		logger.info("Iniciamos prestamo/nuevo [GET]");
		
		// Creamos un objeto préstamo para que actúe como formulario en la vista.
		Prestamo prestamo = new Prestamo();
		if (usuario_id != null) {
			// Si nos especifican un usuario
			Usuario usuario = usuarioService.getUsuario(usuario_id);
			// Vinculamos el usuario al formulario de préstamos.
			prestamo.setUsuario(usuario);
		}
		
		if (ejemplar_id != null) {
			// Si nos especifican un libro
			Ejemplar ejemplar = ejemplarService.getEjemplar(ejemplar_id);
			// Vinculamos el ejemplar al formulario de préstamos.
			prestamo.setEjemplar(ejemplar);
			long libro_isbn = ejemplar.getLibro().getIsbn();
			model.addAttribute("libro_isbn", libro_isbn);

			List<Ejemplar> ejemplares = ejemplarService.getEjemplaresLibresForIsbn(libro_isbn);
			model.addAttribute("ejemplares", ejemplares);
		}
		
		/*
		 * Pasamos a la vista tanto el formulario de préstamo, como listados de usuarios, ejemplares y libros.
		 */
		model.addAttribute("prestamo", prestamo);
		
		List<Usuario> usuarios = usuarioService.getAllUsuarios();
		model.addAttribute("usuarios", usuarios);
		
		List<Libro> libros = libroService.getAllLibros();
		model.addAttribute("libros", libros);
		
		logger.info("Pasamos un prestamo nuevo a la vista, para vincularlo al form.");
		return "prestamo/nuevo";
	}

	/**
	 * Formulario de nuevo préstamo [POST]
	 * 
	 * @param prestamo
	 * @param result
	 * @param model
	 * @param locale
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/nuevo", method = RequestMethod.POST)
	public String nuevo(@ModelAttribute("prestamo") Prestamo prestamo,
			BindingResult result, Model model, Locale locale, final RedirectAttributes redirectAttributes) {
		logger.info("Iniciamos prestamo/nuevo [POST]");

		// Vinculamos el formulario a su validador para, obviamente, validarlo.
		validator.validate(prestamo, result);
		if (result.hasErrors()) {
			logger.info("Formulario con errores, mostramos de nuevo el formulario.");
			
			// Cargamos todo lo necesario para enviar de nuevo al formulario, ya que contiene errores
			
			List<Usuario> usuarios = usuarioService.getAllUsuarios();
			model.addAttribute("usuarios", usuarios);
			
			List<Libro> libros = libroService.getAllLibros();
			model.addAttribute("libros", libros);
			
			if (prestamo.getEjemplar() != null) {
				long libro_isbn = prestamo.getEjemplar().getLibro().getIsbn();
				model.addAttribute("libro_isbn", libro_isbn);
				List<Ejemplar> ejemplares = ejemplarService.getEjemplaresLibresForIsbn(libro_isbn);
				model.addAttribute("ejemplares", ejemplares);
			}
			
			return "prestamo/nuevo";
		}
		
		// En caso de que no contenga errores...
		logger.info("Formulario correcto! Guardamos el prestamo.");
		
		// Llamamos al servicio para guardar el nuevo préstamo.
		prestamoService.save(prestamo, locale);
		
		// Generamos un mensaje "flash" que aparecerá en la siguiente página a la que se redirija.
		// El mensaje flash nos informará de que el préstamo se ha creado con éxito.
		redirectAttributes.addFlashAttribute("success", 
				String.format("El préstamo del libro <strong>%s</strong> para el usuario <strong>%s %s</strong> se ha realizado con éxito.", 
				prestamo.getEjemplar().getLibro().getTitulo(), prestamo.getUsuario().getNombre(), 
				prestamo.getUsuario().getApellidos()));

		logger.info("Redireccionamos a prestamo/listado [GET]");
		// En vez de mostrar una vista, redireccionamos a la ruta del listado de préstamos.
		return "redirect:/prestamo/";
	}
	
	/**
	 * Devolver un préstamo concreto, estableciendo la fecha de devolución como "ahora" [GET]
	 * 
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/devolver/{id}", method = RequestMethod.GET)
	public String devolver(@PathVariable Long id, final RedirectAttributes redirectAttributes) {
		logger.info("Iniciamos prestamo/devolver/{id} [GET]");
		
		// Obtenemos el préstamo correspondiente a la ID
		Prestamo prestamo = prestamoService.getPrestamo(id);
		
		if (!prestamo.isDevuelto()) {			
			// Si no está devuelto, lo actualizamos (se establecerá la fecha de devolución)
			logger.info("Actualizamos la información del préstamo estableciendo la devolución.");
			
			// Llamamos al servicio para guardar el préstamo modificado (devolución).
			prestamoService.update(prestamo);
			
			// Generamos un mensaje "flash" que aparecerá en la siguiente página a la que se redirija.
			// El mensaje flash nos informará de que el préstamo se ha devuelto con éxito.
			redirectAttributes.addFlashAttribute("success", 
					String.format("El préstamo del libro <strong>%s</strong> para el usuario <strong>%s %s</strong> se ha devuelto con éxito.", 
					prestamo.getEjemplar().getLibro().getTitulo(), prestamo.getUsuario().getNombre(), 
					prestamo.getUsuario().getApellidos()));
		}

		logger.info("Redireccionamos a prestamo/listado [GET]");
		// En vez de mostrar una vista, redireccionamos a la ruta del listado de préstamos.
		return "redirect:/prestamo/";
	}

}
