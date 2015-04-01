package org.alexandrialibrary.spring.dao;

import java.util.List;
import java.util.Locale;

import org.alexandrialibrary.spring.bean.Prestamo;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PrestamoDAO {
		
	List<Prestamo> getAllPrestamos(); 

	Prestamo getPrestamo(long id);
	
	long save(Prestamo prestamo, Locale locale);
	
	void update(Prestamo prestamo);
	
	void delete(long id);
}
