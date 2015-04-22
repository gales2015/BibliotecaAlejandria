package org.alexandrialibrary.spring.editor;

import java.beans.PropertyEditorSupport;

import org.alexandrialibrary.spring.model.Usuario;
import org.alexandrialibrary.spring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Editor que convierte ID's tipo String en el objeto Usuario correspondiente.
 * 
 * La llamada al Editor se hará desde un controlador, vinculándolo en el initBinder().
 */
@Component("usuarioEditor")
public class UsuarioEditor extends PropertyEditorSupport {

	@Autowired
	private UsuarioService usuarioService;

	/**
	 * Convierte de String a Usuario (cuando se envía un formulario)
	 */
	@Override
	public void setAsText(String idString) {
		/* La variable idString contendrá una ID de ejemplar.
		 * Ésta será convertida al tipo correspondiente de la ID y se hará una llamada 
		 * al servicio de Usuarios para obtener el objeto Usuario correspondiente.
		 */
		Usuario usuario = usuarioService.getUsuario(Long.valueOf(idString));
		this.setValue(usuario);
	}
}
