package org.alexandrialibrary.spring.dao;

import java.util.List;

import org.alexandrialibrary.spring.bean.Ejemplar;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EjemplarDAO {
		
	List<Ejemplar> getAllEjemplares();

	Ejemplar getEjemplar(long id);
	
	long save(Ejemplar ejemplar);
	
	void update(Ejemplar ejemplar);
	
	void delete(long id);
}
