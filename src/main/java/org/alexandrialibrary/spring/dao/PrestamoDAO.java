package org.alexandrialibrary.spring.dao;

import java.util.List;
import java.util.Locale;

import org.alexandrialibrary.spring.model.Prestamo;

/**
 * Interfaz DAO para Préstamos con los métodos a ser implementados.
 */
public interface PrestamoDAO {
		
	List<Prestamo> getAllPrestamos(); 

	Prestamo getPrestamo(long id);

	List<Prestamo> getPrestamosByDevuelto(boolean isDevuelto);
	
	long save(Prestamo prestamo, Locale locale);
	
	void update(Prestamo prestamo);
	
	void delete(long id);
}
