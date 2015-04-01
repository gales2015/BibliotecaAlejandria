package org.alexandrialibrary.spring.editor;

import java.beans.PropertyEditorSupport;

import org.alexandrialibrary.spring.bean.Ejemplar;
import org.alexandrialibrary.spring.dao.EjemplarDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ejemplarEditor")
public class EjemplarEditor extends PropertyEditorSupport {

	@Autowired
	private EjemplarDAO ejemplarDAO;

	/**
	 * Convierte de String a Ejemplar (cuando se envía un formulario)
	 */
	@Override
	public void setAsText(String text) {
		Ejemplar u = ejemplarDAO.getEjemplar(Long.valueOf(text));
		this.setValue(u);
	}
}
