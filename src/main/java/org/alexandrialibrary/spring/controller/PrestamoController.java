package org.alexandrialibrary.spring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.alexandrialibrary.spring.bean.Ejemplar;
import org.alexandrialibrary.spring.bean.Libro;
import org.alexandrialibrary.spring.bean.Prestamo;
import org.alexandrialibrary.spring.bean.Usuario;
import org.alexandrialibrary.spring.editor.EjemplarEditor;
import org.alexandrialibrary.spring.editor.UsuarioEditor;
import org.alexandrialibrary.spring.form.PrestamoIsDevueltoForm;
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

@Controller
@RequestMapping("/prestamo")
public class PrestamoController extends AbstractController {
	
	@Autowired
	private PrestamoService prestamoService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private LibroService libroService;
	
	@Autowired
	private EjemplarService ejemplarService;

	@Autowired
	private PrestamoFormValidator validator;
	
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
		
		
		PrestamoIsDevueltoForm prestamoForm = null;
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

		logger.info("Pasamos el formulario y el listado de prestamos a la vista.");
		model.addAttribute("prestamoForm", prestamoForm);
		model.addAttribute("prestamos", prestamos);
		
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
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/nuevo", method = RequestMethod.GET)
	public String nuevo(@RequestParam(value = "usuario", required = false) Long usuario_id, 
			@RequestParam(value = "ejemplar", required = false) Long ejemplar_id, Model model) {
		logger.info("Iniciamos prestamo/nuevo [GET]");
		
		Prestamo prestamo = new Prestamo();
		if (usuario_id != null) {
			// Si nos especifican un usuario
			Usuario usuario = usuarioService.getUsuario(usuario_id);
			prestamo.setUsuario(usuario);
		}
		
		if (ejemplar_id != null) {
			// Si nos especifican un libro
			Ejemplar ejemplar = ejemplarService.getEjemplar(ejemplar_id);
			prestamo.setEjemplar(ejemplar);
			long libro_isbn = ejemplar.getLibro().getIsbn();
			model.addAttribute("libro_isbn", libro_isbn);

			List<Ejemplar> ejemplares = ejemplarService.getEjemplaresLibresForIsbn(libro_isbn);
			model.addAttribute("ejemplares", ejemplares);
		}
		
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
	 * @return
	 */
	@RequestMapping(value = "/nuevo", method = RequestMethod.POST)
	public String nuevo(@ModelAttribute("prestamo") Prestamo prestamo,
			BindingResult result, Model model, Locale locale, final RedirectAttributes redirectAttributes) {
		logger.info("Iniciamos prestamo/nuevo [POST]");

		validator.validate(prestamo, result);
		if (result.hasErrors()) {
			logger.info("Formulario con errores, mostramos de nuevo el formulario.");
			
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

		logger.info("Formulario correcto! Guardamos el prestamo.");
		
		prestamoService.save(prestamo, locale);
		
		redirectAttributes.addFlashAttribute("success", 
				String.format("El préstamo del libro <strong>%s</strong> para el usuario <strong>%s %s</strong> se ha realizado con éxito.", 
				prestamo.getEjemplar().getLibro().getTitulo(), prestamo.getUsuario().getNombre(), 
				prestamo.getUsuario().getApellidos()));

		logger.info("Redireccionamos a prestamo/listado [GET]");
		return "redirect:/prestamo/";
	}
	
	/**
	 * Devolver un préstamo concreto, estableciendo la fecha de devolución como "ahora" [GET]
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/devolver/{id}", method = RequestMethod.GET)
	public String devolver(@PathVariable Long id, final RedirectAttributes redirectAttributes) {
		logger.info("Iniciamos prestamo/devolver/{id} [GET]");
		
		Prestamo prestamo = prestamoService.getPrestamo(id);
		
		if (!prestamo.isDevuelto()) {			
			// Si no está devuelto, lo actualizamos (se establecerá la fecha de devolución)
			logger.info("Actualizamos la información del préstamo estableciendo la devolución.");
			
			prestamoService.update(prestamo);
			
			redirectAttributes.addFlashAttribute("success", 
					String.format("El préstamo del libro <strong>%s</strong> para el usuario <strong>%s %s</strong> se ha devuelto con éxito.", 
					prestamo.getEjemplar().getLibro().getTitulo(), prestamo.getUsuario().getNombre(), 
					prestamo.getUsuario().getApellidos()));
		}

		logger.info("Redireccionamos a prestamo/listado [GET]");
		return "redirect:/prestamo/";
	}

}
