package org.alexandrialibrary.spring.controller;

import java.util.List;

import org.alexandrialibrary.spring.model.Usuario;
import org.alexandrialibrary.spring.service.UsuarioService;
import org.alexandrialibrary.spring.util.UsuarioFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Usuario controller.
 * 
 * Contendrá las rutas para todo lo relacionado con usuarios:
 *  - Listado de usuarios
 *  - Crear un nuevo usuario
 *  - Ver un usuario
 *  - Editar un usuario
 *  - Eliminar un usuario
 * 
 */
@Controller
@RequestMapping("/usuario") // Prefijo para todas las rutas del controlador.
public class UsuarioController extends AbstractController {
	
	/**
	 * Servicio de usuarios, que llamará al DAO de usuarios, que interactuará con Hibernate para las consultas.
	 * 
	 * No necesita ser instanciado gracias a la anotación @Autowired.
	 */
	@Autowired
	private UsuarioService usuarioService;

	/**
	 * Validador de usuarios que será utilizado al recibir por post un formulario enviado.
	 */
	@Autowired
	private UsuarioFormValidator validator;

	/**
	 * Listado de usuarios
	 * 
	 * También permite buscar por nombre parcial de usuarios.
	 * 
	 * Ejemplo: /?nombre=Pepe
	 * 
	 * @param nombre
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(@RequestParam(value = "nombre", required = false) String nombre, Model model) {
		logger.info("Iniciamos usuario/index [GET]");
		
		Usuario usuario = null;
		List<Usuario> usuarios = null;
		
		if (nombre != null && !nombre.equals("")) {
			// Si estamos buscando por nombre parcial:
			// - Guardamos el nombre en el formulario "usuario"
			// - Obtenemos la lista de usuarios coincidentes
			usuario = new Usuario(nombre);
			usuarios = usuarioService.getUsuariosByNombreParcial(nombre);
		} else {
			// Si no estamos buscando:
			// - Creamos un formulario "usuario" en blanco
			// - Obtenemos la lista de usuarios por defecto
			usuario = new Usuario();
			usuarios = usuarioService.getAllUsuarios();
		}

		logger.info("Pasamos el formulario y el listado de usuarios a la vista.");
		// Los atributos pasados al modelo serán recibidos por la vista.
		model.addAttribute("usuario", usuario);
		model.addAttribute("usuarios", usuarios);

		return "usuario/index";
	}

	/**
	 * Formulario de nuevo usuario [GET]
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/nuevo", method = RequestMethod.GET)
	public String nuevo(Model model) {
		logger.info("Iniciamos usuario/nuevo [GET]");
		
		// Creamos un nuevo usuario que actuará como formulario en la vista.
		model.addAttribute("usuario", new Usuario());
		
		logger.info("Pasamos un usuario nuevo a la vista, para vincularlo al form.");
		return "usuario/nuevo";
	}

	/**
	 * Formulario de nuevo usuario [POST]
	 * 
	 * @param usuario
	 * @param result
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/nuevo", method = RequestMethod.POST)
	public String nuevo(@ModelAttribute("usuario") Usuario usuario,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		logger.info("Iniciamos usuario/nuevo [POST]");

		// Vinculamos el formulario a su validador para, obviamente, validarlo.
		validator.validate(usuario, result);
		if (result.hasErrors()) {
			// Volvemos a mostrar el formulario, ya que contiene errores
			logger.info("Formulario con errores, mostramos de nuevo el formulario.");
			return "usuario/nuevo";
		}

		logger.info("Formulario correcto! Guardamos el usuario.");

		// El formulario es correcto, así que llamamos al servicio para guardar el usuario
		usuarioService.save(usuario);
		
		// Generamos un mensaje "flash" que aparecerá en la siguiente página a la que se redirija.
		// El mensaje flash nos informará de que el usuario se ha creado con éxito.
		redirectAttributes.addFlashAttribute("success", String.format("Usuario '%s %s' creado con éxito.", 
				usuario.getNombre(), usuario.getApellidos()));

		logger.info("Redireccionamos a usuario/listado [GET]");
		// En vez de mostrar una vista, redireccionamos a la ruta del listado de usuarios.
		return "redirect:/usuario/";
	}
	
	/**
	 * Ver un usuario concreto [GET]
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ver/{id}", method = RequestMethod.GET)
	public String ver(@PathVariable Long id, Model model) {
		logger.info("Iniciamos usuario/ver/{id} [GET]");
		
		// Obtenemos el usuario correspondiente a la ID y lo pasamos a la vista
		Usuario usuario = usuarioService.getUsuario(id);		
		model.addAttribute("usuario", usuario);
		
		return "usuario/ver";
	}

	/**
	 * Eliminar un usuario concreto
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/eliminar/{id}", method = RequestMethod.GET)
	public String eliminar(@PathVariable Long id, Model model, final RedirectAttributes redirectAttributes) {
		logger.info("Iniciamos usuario/eliminar/{id} [GET]");

		// Obtenemos el préstamo correspondiente a la ID
		Usuario usuario = usuarioService.getUsuario(id);
		if (usuario.getHasPrestamosPendientes()) {
			// Si el usuario tiene préstamos pendientes no permitiremos que sea eliminado
			
			// Generamos un mensaje "flash" que aparecerá en la siguiente página a la que se redirija.
			// El mensaje flash nos informará de que el usuario no se ha podido eliminar por tener préstamos pendientes.
			redirectAttributes.addFlashAttribute("danger", 
					String.format("El usuario <strong>%s %s</strong> tiene préstamos pendientes y no se puede eliminar.", 
					usuario.getNombre(), usuario.getApellidos()));
			
			logger.info("Tiene préstamos pendientes, no se puede eliminar.");
			logger.info("Redireccionamos a usuario/ver/{id} [GET]");
			// En vez de mostrar una vista, redireccionamos a la ruta de la ficha del usuario a eliminar.
			return String.format("redirect:/usuario/ver/%d", id);
		}

		logger.info("No tiene préstamos pendientes, eliminamos el usuario.");
		// En caso de no tener préstamos pendientes se llama al servicio para eliminar el usuario.
		usuarioService.delete(id);

		// Generamos un mensaje "flash" que aparecerá en la siguiente página a la que se redirija.
		// El mensaje flash nos informará de que el usuario se ha eliminado con éxito.
		redirectAttributes.addFlashAttribute("success", String.format("Usuario <strong>%s %s</strong> eliminado con éxito.", 
				usuario.getNombre(), usuario.getApellidos()));

		logger.info("Redireccionamos a usuario/listado [GET]");
		// En vez de mostrar una vista, redireccionamos a la ruta del listado de usuarios.
		return "redirect:/usuario/";
	}
	
	/**
	 * Formulario para editar un usuario concreto [GET]
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
	public String editar(@PathVariable Long id, Model model) {
		logger.info("Iniciamos usuario/editar/{id} [GET]");

		// Obtenemos el préstamo correspondiente a la ID y lo pasamos a la vista, donde actuará como formulario
		Usuario usuario = usuarioService.getUsuario(id);		
		model.addAttribute("usuario", usuario);
		
		return "usuario/editar";
	}

	/**
	 * Formulario para editar un usuario concreto [POST]
	 * 
	 * @param usuario
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/editar/{id}", method = RequestMethod.POST)
	public String editar(@ModelAttribute("usuario") Usuario usuario,
			BindingResult result, Model model) {
		logger.info("Iniciamos usuario/editar/{id} [POST]");

		// Vinculamos el formulario a su validador para, obviamente, validarlo.
		validator.validate(usuario, result);
		if (result.hasErrors()) {
			// Si tiene errores, lo devolvemos de nuevo al formulario.
			logger.info("Formulario con errores, mostramos de nuevo el formulario.");
			return "usuario/editar";
		}

		logger.info("Formulario correcto! Guardamos el usuario.");
		
		// El formulario es correcto, llamamos al servicio para que actualice el objeto usuario.
		usuarioService.update(usuario);

		logger.info("Redireccionamos a usuario/listado [GET]");
		// En vez de mostrar una vista, redireccionamos a la ruta del listado de usuarios.
		return "redirect:/usuario/";
	}

}
