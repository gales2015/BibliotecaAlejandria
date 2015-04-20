package org.alexandrialibrary.spring.util;

import org.alexandrialibrary.spring.model.Prestamo;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("prestamoFormValidator")
public class PrestamoFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clase) {
		return Prestamo.class.isAssignableFrom(clase);
	}

	@Override
	public void validate(Object model, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "usuario", "required.usuario", "Debe especificar el usuario.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ejemplar", "required.ejemplar", "Debe especificar el ejemplar del libro.");
	}

}