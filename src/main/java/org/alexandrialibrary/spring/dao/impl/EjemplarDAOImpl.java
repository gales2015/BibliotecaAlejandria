package org.alexandrialibrary.spring.dao.impl;

import java.util.List;

import org.alexandrialibrary.spring.dao.AbstractDAO;
import org.alexandrialibrary.spring.dao.EjemplarDAO;
import org.alexandrialibrary.spring.model.Ejemplar;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Implementación de la clase DAO de Ejemplares.
 * 
 * Se comunicará con la BBDD a través de Hibernate.
 */
@Repository
public class EjemplarDAOImpl extends AbstractDAO implements EjemplarDAO {
	
	/**
	 * Devuelve todos los ejemplares.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Ejemplar> getAllEjemplares() {
		// Creamos un criterio llamando a la sesión para obtener todos los objetos de Ejemplar de la BBDD.
		Criteria criteria = this.getCurrentSession().createCriteria(Ejemplar.class);
		List<Ejemplar> ejemplares = criteria.list();
		
		/* La colección Préstamos de Ejemplares es Lazy: 
		 * no se carga a menos que lo indiquemos explícitamente.
		 */ 
		for (Ejemplar ejemplar : ejemplares) {
			// Inicializamos la colección de préstamos
			Hibernate.initialize(ejemplar.getPrestamos());
		}
		return ejemplares; 
	}
	
	/**
	 * Devuelve un ejemplar concreto.
	 */
	@Override
	public Ejemplar getEjemplar(long id) {
		// Creamos un criterio llamando a la sesión para obtener el objeto de Ejemplar de la BBDD que se corresponda con una ID concreta.
		Ejemplar ejemplar = (Ejemplar) this.getCurrentSession().get(Ejemplar.class, id);
		if (ejemplar != null) {
			/* La colección Préstamos de Ejemplares es Lazy: 
			 * no se carga a menos que lo indiquemos explícitamente.
			 */ 
			Hibernate.initialize(ejemplar.getPrestamos());			
		}
		
		return ejemplar;
	}

	/**
	 * Guarda un ejemplar nuevo.
	 */
	@Override
	public long save(Ejemplar ejemplar) {
		return (Long) this.getCurrentSession().save(ejemplar); 
	}

	/**
	 * Actualiza un ejemplar existente.
	 */
	@Override
	public void update(Ejemplar ejemplar) {
		this.getCurrentSession().merge(ejemplar);
	}

	/**
	 * Elimina un ejemplar concreto.
	 */
	@Override
	public void delete(long id) {
		Ejemplar ejemplar = getEjemplar(id);
		this.getCurrentSession().delete(ejemplar);
	}

	/**
	 * Devuelve aquellos ejemplares que cumplan un ISBN de libro asociado concreto, 
	 * y que no estén actualmente prestados.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Ejemplar> getEjemplaresLibresForIsbn(long libro_isbn) {
		// Creamos una HQL (Hibernate Query Language) para generar una consulta.
		Query query = (Query) this.getCurrentSession().
				createQuery("from Ejemplar e where e.libro.isbn = :libro_isbn and " +
				"(select count(p) from Ejemplar e2 LEFT OUTER JOIN e2.prestamos p where e2.id = e.id " +
				"and p.fechaDevolucion is null) = :prestamosPendientes");
        
		/*
		 * Establecemos los parámetros de la consulta.
		 * Los parámetros se declaran en la query precedidos de ":". Ejemplo -> :libro_isbn
		 */
		query.setParameter("libro_isbn", (long) libro_isbn);
        query.setParameter("prestamosPendientes", 0L);
        
        return query.list();
	}

}
