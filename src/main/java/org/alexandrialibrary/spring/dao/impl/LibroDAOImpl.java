package org.alexandrialibrary.spring.dao.impl;

import java.util.List;

import org.alexandrialibrary.spring.bean.Ejemplar;
import org.alexandrialibrary.spring.bean.Libro;
import org.alexandrialibrary.spring.dao.AbstractDAO;
import org.alexandrialibrary.spring.dao.LibroDAO;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LibroDAOImpl extends AbstractDAO implements LibroDAO {
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Libro> getAllLibros() {
		Criteria criteria = this.getCurrentSession().createCriteria(Libro.class);
		List<Libro> libros = criteria.list();
		for (Libro libro : libros) {
			List<Ejemplar> ejemplares = libro.getEjemplares();
			for (Ejemplar ejemplar : ejemplares) {
				Hibernate.initialize(ejemplar.getPrestamos());				
			}
		}
		return libros;
	}
	 
	@Override
	public Libro getLibro(long isbn) {
		Libro libro = (Libro) this.getCurrentSession().get(Libro.class, isbn);
		List<Ejemplar> ejemplares = libro.getEjemplares();
		for (Ejemplar ejemplar : ejemplares) {
			Hibernate.initialize(ejemplar.getPrestamos());				
		}
		return libro;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Libro> getLibrosByTituloParcial(String titulo) {
		Criteria criteria = this.getCurrentSession().createCriteria(Libro.class);
		criteria.add(Restrictions.ilike("titulo", "%"+titulo+"%"));
		List<Libro> libros = criteria.list();
		for (Libro libro : libros) {
			List<Ejemplar> ejemplares = libro.getEjemplares();
			for (Ejemplar ejemplar : ejemplares) {
				Hibernate.initialize(ejemplar.getPrestamos());				
			}
		}
		return libros;
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
