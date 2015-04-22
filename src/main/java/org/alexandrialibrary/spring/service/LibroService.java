package org.alexandrialibrary.spring.service;

import java.util.List;

import org.alexandrialibrary.spring.model.Libro;

/**
 * Interfaz Service para Libros con los métodos a ser implementados.
 * 
 * En las implementaciones se deberá llamar a los métodos pertinentes de las clases DAO.
 */
public interface LibroService {
		
	List<Libro> getAllLibros(); 

	Libro getLibro(long isbn);
	
	List<Libro> getLibrosByTituloParcial(String titulo);
	
	long save(Libro libro);
	
	void update(Libro libro);
	
	void delete(long isbn);

}
