package org.alexandrialibrary.spring.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.alexandrialibrary.spring.bean.Prestamo;
import org.alexandrialibrary.spring.dao.AbstractDAO;
import org.alexandrialibrary.spring.dao.PrestamoDAO;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PrestamoDAOImpl extends AbstractDAO implements PrestamoDAO {
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Prestamo> getAllPrestamos() {
		Criteria criteria = this.getCurrentSession().createCriteria(Prestamo.class);  
		return criteria.list(); 
	}
	 
	@Override
	public Prestamo getPrestamo(long id) {
		return (Prestamo) this.getCurrentSession().get(Prestamo.class, id);
	}

	@Override
	public long save(Prestamo prestamo, Locale locale) {

		Date fechaInicio = new Date();
		Calendar calendar = new GregorianCalendar(locale);
		calendar.setTime(fechaInicio);
		calendar.add(Calendar.DATE, Prestamo.DIAS_PRESTAMO);
		Date fechaFin = calendar.getTime();
		

		prestamo.setFechaInicio(fechaInicio);
		prestamo.setFechaFin(fechaFin);
		return (Long) this.getCurrentSession().save(prestamo); 
	}

	/**
	 * En este caso solo actualizamos:
	 * - Fecha devolución
	 */
	@Override
	public void update(Prestamo prestamo) {
		prestamo.setFechaDevolucion(new Date());
		
		this.getCurrentSession().merge(prestamo);
	}

	@Override
	public void delete(long id) {
		Prestamo prestamo = getPrestamo(id);
		this.getCurrentSession().delete(prestamo);
	}

}
