package org.alexandrialibrary.spring.util;

import org.alexandrialibrary.spring.bean.Usuario;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("prestamoFormValidator")
public class PrestamoFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clase) {
		return Usuario.class.isAssignableFrom(clase);
	}

	@Override
	public void validate(Object model, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "usuario_id", "required.usuario_id", "Debe especificar el usuario.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ejemplar_id", "required.ejemplar_id", "Debe especificar el ejemplar del libro.");
	}

}