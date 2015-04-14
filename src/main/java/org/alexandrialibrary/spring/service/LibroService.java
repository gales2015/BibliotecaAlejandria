package org.alexandrialibrary.spring.service;

import java.util.List;

import org.alexandrialibrary.spring.bean.Libro;

public interface LibroService {
		
	List<Libro> getAllLibros(); 

	Libro getLibro(long isbn);
	
	List<Libro> getLibrosByTituloParcial(String titulo);
	
	long save(Libro libro);
	
	void update(Libro libro);
	
	void delete(long isbn);

}
