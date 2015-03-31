package org.alexandrialibrary.spring.dao.impl;

import java.util.List;

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
	public List<Prestamo> getAllPrestamo() {
		Criteria criteria = this.getCurrentSession().createCriteria(Prestamo.class);  
		return criteria.list(); 
	}
	 
	@Override
	public Prestamo getPrestamo(long id) {
		return (Prestamo) this.getCurrentSession().get(Prestamo.class, id);
	}

	@Override
	public long save(Prestamo prestamo) {
		return (Long) this.getCurrentSession().save(prestamo); 
	}

	/**
	 * En este caso solo actualizamos:
	 * - Fecha devolución
	 */
	@Override
	public void update(Prestamo prestamo) {
		Prestamo existingPrestamo = getPrestamo(prestamo.getId());
		
		existingPrestamo.setFechaDevolucion(prestamo.getFechaDevolucion());
		
		this.getCurrentSession().merge(existingPrestamo);
	}

	@Override
	public void delete(long id) {
		Prestamo prestamo = getPrestamo(id);
		this.getCurrentSession().delete(prestamo);
	}

}
