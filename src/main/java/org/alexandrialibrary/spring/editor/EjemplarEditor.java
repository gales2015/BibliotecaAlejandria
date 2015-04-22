package org.alexandrialibrary.spring.editor;

import java.beans.PropertyEditorSupport;

import org.alexandrialibrary.spring.model.Ejemplar;
import org.alexandrialibrary.spring.service.EjemplarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Editor que convierte ID's tipo String en el objeto Ejemplar correspondiente.
 * 
 * La llamada al Editor se hará desde un controlador, vinculándolo en el initBinder().
 */
@Component("ejemplarEditor")
public class EjemplarEditor extends PropertyEditorSupport {

	@Autowired
	private EjemplarService ejemplarService;

	/**
	 * Convierte de String a Ejemplar (cuando se envía un formulario)
	 */
	@Override
	public void setAsText(String idString) {
		/* La variable idString contendrá una ID de ejemplar.
		 * Ésta será convertida al tipo correspondiente de la ID y se hará una llamada 
		 * al servicio de Ejemplares para obtener el objeto Ejemplar correspondiente.
		 */
		Ejemplar ejemplar = ejemplarService.getEjemplar(Long.valueOf(idString));
		this.setValue(ejemplar);
	}
}
