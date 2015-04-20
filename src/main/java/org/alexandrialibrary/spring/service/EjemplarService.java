package org.alexandrialibrary.spring.service;

import java.util.List;

import org.alexandrialibrary.spring.model.Ejemplar;

public interface EjemplarService {
		
	List<Ejemplar> getAllEjemplares();

	Ejemplar getEjemplar(long id);
	
	long save(Ejemplar ejemplar);
	
	void update(Ejemplar ejemplar);
	
	void delete(long id);

	List<Ejemplar> getEjemplaresLibresForIsbn(long libro_isbn);
}
