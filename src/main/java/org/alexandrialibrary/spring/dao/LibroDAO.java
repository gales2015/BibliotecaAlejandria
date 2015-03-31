package org.alexandrialibrary.spring.dao;

import java.util.List;

import org.alexandrialibrary.spring.bean.Libro;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LibroDAO {
		
	List<Libro> getAllLibro(); 

	Libro getLibro(long isbn);
	
	long save(Libro libro);
	
	void update(Libro libro);
	
	void delete(long isbn);
}
