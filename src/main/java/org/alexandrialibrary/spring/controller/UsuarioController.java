package org.alexandrialibrary.spring.controller;

import java.util.List;

import org.alexandrialibrary.spring.bean.Usuario;
import org.alexandrialibrary.spring.dao.UsuarioDAO;
import org.alexandrialibrary.spring.util.UsuarioFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/usuario")
public class UsuarioController extends AbstractController {
	
	@Autowired
	private UsuarioDAO usuarioDAO;

	@Autowired
	private UsuarioFormValidator validator;

	/**
	 * Listado de usuarios
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		logger.info("Iniciamos usuario/index [GET]");
		List<Usuario> usuarios = usuarioDAO.getAllUsuarios();
		model.addAttribute("usuarios", usuarios);

		logger.info("Pasamos el listado de usuarios a la vista.");
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
	 * @return
	 */
	@RequestMapping(value = "/nuevo", method = RequestMethod.POST)
	public String nuevo(@ModelAttribute("usuario") Usuario usuario,
			BindingResult result, Model model) {
		logger.info("Iniciamos usuario/nuevo [POST]");

		validator.validate(usuario, result);
		if (result.hasErrors()) {
			logger.info("Formulario con errores, mostramos de nuevo el formulario.");
			return "usuario/nuevo";
		}

		logger.info("Formulario correcto! Guardamos el usuario.");

		usuarioDAO.save(usuario);

		logger.info("Redireccionamos a usuario/listado [GET]");
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
		
		Usuario usuario = usuarioDAO.getUsuario(id);		
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
	public String eliminar(@PathVariable Long id, Model model) {
		logger.info("Iniciamos usuario/eliminar/{id} [GET]");

		usuarioDAO.delete(id);

		logger.info("Redireccionamos a usuario/listado [GET]");
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
		
		Usuario usuario = usuarioDAO.getUsuario(id);		
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

		validator.validate(usuario, result);
		if (result.hasErrors()) {
			logger.info("Formulario con errores, mostramos de nuevo el formulario.");
			return "usuario/editar";
		}

		logger.info("Formulario correcto! Guardamos el usuario.");

		usuarioDAO.update(usuario);

		logger.info("Redireccionamos a usuario/listado [GET]");
		return "redirect:/usuario/";
	}

}
