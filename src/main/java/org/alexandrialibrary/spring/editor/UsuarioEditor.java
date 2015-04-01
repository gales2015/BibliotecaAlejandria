package org.alexandrialibrary.spring.editor;

import java.beans.PropertyEditorSupport;

import org.alexandrialibrary.spring.bean.Usuario;
import org.alexandrialibrary.spring.dao.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("usuarioEditor")
public class UsuarioEditor extends PropertyEditorSupport {

	@Autowired
	private UsuarioDAO usuarioDAO;

	/**
	 * Convierte de String a Usuario (cuando se envía un formulario)
	 */
	@Override
	public void setAsText(String text) {
		Usuario u = usuarioDAO.getUsuario(Long.valueOf(text));
		this.setValue(u);
	}
}
