package org.alexandrialibrary.spring.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.alexandrialibrary.spring.dao.AbstractDAO;
import org.alexandrialibrary.spring.dao.PrestamoDAO;
import org.alexandrialibrary.spring.model.Prestamo;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Implementación de la clase DAO de Préstamos.
 * 
 * Se comunicará con la BBDD a través de Hibernate.
 */
@Repository
public class PrestamoDAOImpl extends AbstractDAO implements PrestamoDAO {
	
	/**
	 * Devuelve todos los préstamos.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Prestamo> getAllPrestamos() {
		// Creamos un criterio llamando a la sesión para obtener todos los objetos de Préstamo de la BBDD.
		Criteria criteria = this.getCurrentSession().createCriteria(Prestamo.class);  
		return criteria.list(); 
	}
	 
	/**
	 * Devuelve un préstamo concreto.
	 */
	@Override
	public Prestamo getPrestamo(long id) {
		// Creamos un criterio llamando a la sesión para obtener el objeto de Préstamo de la BBDD que se corresponda con un ID concreto.
		return (Prestamo) this.getCurrentSession().get(Prestamo.class, id);
	}

	/**
	 * Devuelve préstamos eligiendo si son los devueltos o los pendientes.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Prestamo> getPrestamosByDevuelto(boolean isDevuelto) {
		// Creamos un criterio llamando a la sesión para obtener todos los objetos de Préstamo de la BBDD.
		Criteria criteria = this.getCurrentSession().createCriteria(Prestamo.class);
		
		// Añadimos un criterio para coger solo los devueltos o pendientes.
		if (isDevuelto) {
			// Devuelto: Fecha de devolución no nula.
			criteria.add(Restrictions.isNotNull("fechaDevolucion"));
		} else {
			// No devuelto: Fecha de devolución nula.
			criteria.add(Restrictions.isNull("fechaDevolucion"));			
		}
		return criteria.list(); 
	}

	/**
	 * Guarda un préstamo nuevo.
	 */
	@Override
	public long save(Prestamo prestamo, Locale locale) {

		// Se obtiene la fecha actual y se calcula la fecha de caducidad del préstamo.
		Date fechaInicio = new Date();
		Calendar calendar = new GregorianCalendar(locale);
		calendar.setTime(fechaInicio);
		calendar.add(Calendar.DATE, Prestamo.DIAS_PRESTAMO);
		Date fechaFin = calendar.getTime();
		
		// Ambas fechas se vinculan al objeto préstamo antes de guardarlo.
		prestamo.setFechaInicio(fechaInicio);
		prestamo.setFechaFin(fechaFin);
		return (Long) this.getCurrentSession().save(prestamo); 
	}

	/**
	 * Actualizamos un préstamo existente.
	 * 
	 * En este caso solo actualizamos:
	 * - Fecha devolución
	 */
	@Override
	public void update(Prestamo prestamo) {
		prestamo.setFechaDevolucion(new Date());
		
		this.getCurrentSession().merge(prestamo);
	}

	/**
	 * Elimina un préstamo concreto.
	 */
	@Override
	public void delete(long id) {
		Prestamo prestamo = getPrestamo(id);
		this.getCurrentSession().delete(prestamo);
	}
}
