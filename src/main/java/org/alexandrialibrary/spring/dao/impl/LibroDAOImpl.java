package org.alexandrialibrary.spring.dao.impl;

import java.util.List;

import org.alexandrialibrary.spring.dao.AbstractDAO;
import org.alexandrialibrary.spring.dao.LibroDAO;
import org.alexandrialibrary.spring.model.Ejemplar;
import org.alexandrialibrary.spring.model.Libro;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Implementación de la clase DAO de Libros.
 * 
 * Se comunicará con la BBDD a través de Hibernate.
 */
@Repository
public class LibroDAOImpl extends AbstractDAO implements LibroDAO {
	
	/**
	 * Devuelve todos los libros.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Libro> getAllLibros() {
		// Creamos un criterio llamando a la sesión para obtener todos los objetos de Libro de la BBDD.
		Criteria criteria = this.getCurrentSession().createCriteria(Libro.class);
		List<Libro> libros = criteria.list();
		
		/* La colección Ejemplares de Libro es Eager, se carga de manera automática. 
		 * La colección Préstamos de Ejemplares es Lazy, así que hay que cargarla explícitamente.
		 */ 
		for (Libro libro : libros) {
			List<Ejemplar> ejemplares = libro.getEjemplares();
			for (Ejemplar ejemplar : ejemplares) {
				// Inicializamos la colección de préstamos
				Hibernate.initialize(ejemplar.getPrestamos());				
			}
		}
		return libros;
	}
	 
	/**
	 * Devuelve un libro concreto.
	 */
	@Override
	public Libro getLibro(long isbn) {
		// Creamos un criterio llamando a la sesión para obtener el objeto de Libro de la BBDD que se corresponda con un ISBN concreto.
		Libro libro = (Libro) this.getCurrentSession().get(Libro.class, isbn);
		if (libro != null) {
			/* La colección Ejemplares de Libro es Eager, se carga de manera automática. 
			 * La colección Préstamos de Ejemplares es Lazy, así que hay que cargarla explícitamente.
			 */ 
			List<Ejemplar> ejemplares = libro.getEjemplares();
			for (Ejemplar ejemplar : ejemplares) {
				Hibernate.initialize(ejemplar.getPrestamos());				
			}			
		}
		return libro;
	}
	
	/**
	 * Devuelve todos los objetos libro cuyo título parcial coincida.
	 * 
	 * Ejemplo: "anillos", recogería "El Señor de los Anillos".
	 * 
	 * Utilizaremos la restricción ilike(), que es case insensitive.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Libro> getLibrosByTituloParcial(String titulo) {
		// Creamos un criterio llamando a la sesión para obtener todos los objetos de Libro de la BBDD.
		Criteria criteria = this.getCurrentSession().createCriteria(Libro.class);
		// Le añadimos la restricción LIKE (case insensitive) con %titulo% como parámetro
		criteria.add(Restrictions.ilike("titulo", "%"+titulo+"%"));
		List<Libro> libros = criteria.list();
		
		/* La colección Ejemplares de Libro es Eager, se carga de manera automática. 
		 * La colección Préstamos de Ejemplares es Lazy, así que hay que cargarla explícitamente.
		 */ 
		for (Libro libro : libros) {
			List<Ejemplar> ejemplares = libro.getEjemplares();
			for (Ejemplar ejemplar : ejemplares) {
				Hibernate.initialize(ejemplar.getPrestamos());				
			}
		}
		return libros;
	}

	/**
	 * Guarda un libro nuevo.
	 */
	@Override
	public long save(Libro libro) {
		return (Long) this.getCurrentSession().save(libro); 
	}

	/**
	 * Actualiza un libro existente.
	 */
	@Override
	public void update(Libro libro) {
		this.getCurrentSession().merge(libro);
	}

	/**
	 * Elimina un libro concreto.
	 */
	@Override
	public void delete(long isbn) {
		Libro libro = getLibro(isbn);
		this.getCurrentSession().delete(libro);
	}

}
