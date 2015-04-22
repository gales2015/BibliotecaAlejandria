package org.alexandrialibrary.spring.dao;

import java.util.List;

import org.alexandrialibrary.spring.model.Ejemplar;

/**
 * Interfaz DAO para Ejemplares con los métodos a ser implementados.
 */
public interface EjemplarDAO {
		
	List<Ejemplar> getAllEjemplares();

	Ejemplar getEjemplar(long id);
	
	long save(Ejemplar ejemplar);
	
	void update(Ejemplar ejemplar);
	
	void delete(long id);

	List<Ejemplar> getEjemplaresLibresForIsbn(long libro_isbn);
}
