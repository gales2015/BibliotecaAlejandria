package org.alexandrialibrary.spring.dao;

import java.util.List;

import org.alexandrialibrary.spring.bean.Prestamo;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PrestamoDAO {
		
	List<Prestamo> getAllPrestamo(); 

	Prestamo getPrestamo(long id);
	
	long save(Prestamo prestamo);
	
	void update(Prestamo prestamo);
	
	void delete(long id);
}
