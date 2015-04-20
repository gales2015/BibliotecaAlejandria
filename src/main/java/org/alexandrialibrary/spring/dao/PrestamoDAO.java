package org.alexandrialibrary.spring.dao;

import java.util.List;
import java.util.Locale;

import org.alexandrialibrary.spring.model.Prestamo;

public interface PrestamoDAO {
		
	List<Prestamo> getAllPrestamos(); 

	Prestamo getPrestamo(long id);

	List<Prestamo> getPrestamosByDevuelto(boolean isDevuelto);
	
	long save(Prestamo prestamo, Locale locale);
	
	void update(Prestamo prestamo);
	
	void delete(long id);
}
