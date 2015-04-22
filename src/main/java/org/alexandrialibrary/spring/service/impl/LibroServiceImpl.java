package org.alexandrialibrary.spring.service.impl;

import java.util.List;

import org.alexandrialibrary.spring.dao.LibroDAO;
import org.alexandrialibrary.spring.model.Libro;
import org.alexandrialibrary.spring.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio de Libros.
 * 
 * Se anota con @Service y @Transactional para que la sesión que se comunica 
 * con Hibernate esté iniciada en este punto.
 * 
 * Por norma general la clase del Servicio llama a la clase DAO correspondiente.
 */
@Service
@Transactional
public class LibroServiceImpl implements LibroService {

	@Autowired
    private LibroDAO libroDAO;
	
	@Override
	public List<Libro> getAllLibros() {
		return libroDAO.getAllLibros();
	}

	@Override
	public Libro getLibro(long isbn) {
		return libroDAO.getLibro(isbn);
	}

	@Override
	public List<Libro> getLibrosByTituloParcial(String titulo) {
		return libroDAO.getLibrosByTituloParcial(titulo);
	}

	@Override
	public long save(Libro libro) {
		return libroDAO.save(libro);
	}

	@Override
	public void update(Libro libro) {
		libroDAO.update(libro);
	}

	@Override
	public void delete(long isbn) {
		libroDAO.delete(isbn);
	}

}
