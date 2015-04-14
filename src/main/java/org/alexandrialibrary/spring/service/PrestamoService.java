package org.alexandrialibrary.spring.service;

import java.util.List;
import java.util.Locale;

import org.alexandrialibrary.spring.bean.Prestamo;

public interface PrestamoService {
		
	List<Prestamo> getAllPrestamos(); 

	Prestamo getPrestamo(long id);

	List<Prestamo> getPrestamosByDevuelto(boolean isDevuelto);
	
	long save(Prestamo prestamo, Locale locale);
	
	void update(Prestamo prestamo);
	
	void delete(long id);
}
