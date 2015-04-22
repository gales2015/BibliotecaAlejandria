package org.alexandrialibrary.spring.util;

import org.alexandrialibrary.spring.model.Usuario;
import org.alexandrialibrary.spring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Clase validadora del formulario de Usuarios
 */
@Component("usuarioFormValidator")
public class UsuarioFormValidator implements Validator {

	/**
	 * Mantenemos un servicio de Usuario activo para comprobar que los campos únicos 
	 * (email y dni) no se repitan en la BBDD.
	 */
	@Autowired
	private UsuarioService usuarioService;
	
	/**
	 * La clase a la que se vincula.
	 */
	@Override
	public boolean supports(Class<?> clase) {
		return Usuario.class.isAssignableFrom(clase);
	}

	/**
	 * Método validate() llamado desde el controlador al enviar el formulario.
	 * 
	 * Contiene las reglas para comprobar que el formulario se ha enviado correctamente. 
	 * Establece los errores que se mostrarán en la vista en caso de que algo sea incorrecto.
	 */
	@Override
	public void validate(Object model, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "required.nombre", "Debe rellenar el nombre.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "apellidos", "required.apellidos", "Debe rellenar los apellidos.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dni", "required.dni", "Debe rellenar el DNI.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required.email", "Debe rellenar el email.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "direccion", "required.direccion", "Debe rellenar la dirección.");
		
		Usuario usuario = (Usuario) model;
		
		// Comprobaciones de DNI
		String dniClean = usuario.getDni().trim();
		if (dniClean.length() > 0) {
			// Si se ha rellenado el DNI, comprobamos:
			if (dniClean.length() != 9 || !(dniClean.matches("[0-9]{8}[a-zA-Z]{1}"))) {
				// No cumple el formato 8 números y 1 letra (ejemplo: 12345678A)
				errors.rejectValue("dni", "required.dni", "El DNI debe ser 8 números y 1 letra.");			
			} else if (!usuarioService.isDniAvailable(usuario)) {
				// DNI ya en uso
				errors.rejectValue("dni", "required.dni", "Este número de DNI ya está en uso.");
			}			
		}
		
		// Comprobaciones de Email
		String emailClean = usuario.getEmail().trim();
		if (emailClean.length() > 0) {
			// Si se ha rellenado el Email, comprobamos:
			if (emailClean.length() < 6 || !(emailClean
					.matches("[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})"))) {
				// No cumple el formato de email
				errors.rejectValue("email", "required.email", "Email incorrecto o incompleto (formato incorrecto).");
			} else if (!usuarioService.isEmailAvailable(usuario)) {
				// Email ya en uso
				errors.rejectValue("email", "required.email", "Esta dirección de email ya está en uso.");
			}
		}
	}

}
