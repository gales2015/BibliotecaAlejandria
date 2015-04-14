package org.alexandrialibrary.spring.editor;

import java.beans.PropertyEditorSupport;

import org.alexandrialibrary.spring.bean.Ejemplar;
import org.alexandrialibrary.spring.service.EjemplarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ejemplarEditor")
public class EjemplarEditor extends PropertyEditorSupport {

	@Autowired
	private EjemplarService ejemplarService;

	/**
	 * Convierte de String a Ejemplar (cuando se envía un formulario)
	 */
	@Override
	public void setAsText(String text) {
		Ejemplar u = ejemplarService.getEjemplar(Long.valueOf(text));
		this.setValue(u);
	}
}
