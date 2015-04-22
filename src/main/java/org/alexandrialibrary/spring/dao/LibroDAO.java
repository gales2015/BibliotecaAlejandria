package org.alexandrialibrary.spring.dao;

import java.util.List;

import org.alexandrialibrary.spring.model.Libro;

/**
 * Interfaz DAO para Libros con los métodos a ser implementados.
 */
public interface LibroDAO {
		
	List<Libro> getAllLibros(); 

	Libro getLibro(long isbn);
	
	List<Libro> getLibrosByTituloParcial(String titulo);
	
	long save(Libro libro);
	
	void update(Libro libro);
	
	void delete(long isbn);

}
