package org.alexandrialibrary.spring.util;

import org.alexandrialibrary.spring.bean.Usuario;
import org.alexandrialibrary.spring.dao.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("usuarioFormValidator")
public class UsuarioFormValidator implements Validator {

	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Override
	public boolean supports(Class<?> clase) {
		return Usuario.class.isAssignableFrom(clase);
	}

	@Override
	public void validate(Object model, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "required.nombre", "Debe rellenar el nombre.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "apellidos", "required.apellidos", "Debe rellenar los apellidos.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dni", "required.dni", "Debe rellenar el DNI.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required.email", "Debe rellenar el email.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "direccion", "required.direccion", "Debe rellenar la dirección.");
		
		Usuario usuario = (Usuario) model;
		
		if (!usuarioDAO.isDniAvailable(usuario)) {
			errors.rejectValue("dni", "required.dni", "Este número de DNI ya está en uso.");
		}
		
		if (!usuarioDAO.isEmailAvailable(usuario)) {
			errors.rejectValue("email", "required.email", "Esta dirección de email ya está en uso.");
		}
		
	}

}
