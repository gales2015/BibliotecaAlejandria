package org.alexandrialibrary.spring.editor;

import java.beans.PropertyEditorSupport;

import org.alexandrialibrary.spring.bean.Usuario;
import org.alexandrialibrary.spring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("usuarioEditor")
public class UsuarioEditor extends PropertyEditorSupport {

	@Autowired
	private UsuarioService usuarioService;

	/**
	 * Convierte de String a Usuario (cuando se envía un formulario)
	 */
	@Override
	public void setAsText(String text) {
		Usuario u = usuarioService.getUsuario(Long.valueOf(text));
		this.setValue(u);
	}
}
