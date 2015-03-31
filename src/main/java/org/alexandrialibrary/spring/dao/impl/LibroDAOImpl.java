package org.alexandrialibrary.spring.dao.impl;

import java.util.List;

import org.alexandrialibrary.spring.bean.Libro;
import org.alexandrialibrary.spring.dao.AbstractDAO;
import org.alexandrialibrary.spring.dao.LibroDAO;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class LibroDAOImpl extends AbstractDAO implements LibroDAO {
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Libro> getAllLibro() {
		Criteria criteria = this.getCurrentSession().createCriteria(Libro.class);  
		return criteria.list(); 
	}
	 
	@Override
	public Libro getLibro(long isbn) {
		return (Libro) this.getCurrentSession().get(Libro.class, isbn);
	}

	@Override
	public long save(Libro libro) {
		return (Long) this.getCurrentSession().save(libro); 
	}

	@Override
	public void update(Libro libro) {
		this.getCurrentSession().merge(libro);
	}

	@Override
	public void delete(long isbn) {
		Libro libro = getLibro(isbn);
		this.getCurrentSession().delete(libro);
	}

}
