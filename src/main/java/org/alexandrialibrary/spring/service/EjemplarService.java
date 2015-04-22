package org.alexandrialibrary.spring.service;

import java.util.List;

import org.alexandrialibrary.spring.model.Ejemplar;

/**
 * Interfaz Service para Ejemplares con los métodos a ser implementados.
 * 
 * En las implementaciones se deberá llamar a los métodos pertinentes de las clases DAO.
 */
public interface EjemplarService {
		
	List<Ejemplar> getAllEjemplares();

	Ejemplar getEjemplar(long id);
	
	long save(Ejemplar ejemplar);
	
	void update(Ejemplar ejemplar);
	
	void delete(long id);

	List<Ejemplar> getEjemplaresLibresForIsbn(long libro_isbn);
}
