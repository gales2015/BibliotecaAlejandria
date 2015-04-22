package org.alexandrialibrary.spring.util;

import org.alexandrialibrary.spring.model.Prestamo;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Clase validadora del formulario de Préstamos
 */
@Component("prestamoFormValidator")
public class PrestamoFormValidator implements Validator {

	/**
	 * La clase a la que se vincula.
	 */
	@Override
	public boolean supports(Class<?> clase) {
		return Prestamo.class.isAssignableFrom(clase);
	}

	/**
	 * Método validate() llamado desde el controlador al enviar el formulario.
	 * 
	 * Contiene las reglas para comprobar que el formulario se ha enviado correctamente. 
	 * Establece los errores que se mostrarán en la vista en caso de que algo sea incorrecto.
	 */
	@Override
	public void validate(Object model, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "usuario", "required.usuario", "Debe especificar el usuario.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ejemplar", "required.ejemplar", "Debe especificar el ejemplar del libro.");
	}

}