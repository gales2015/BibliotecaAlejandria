package org.alexandrialibrary.spring.dao.impl;

import java.util.List;

import org.alexandrialibrary.spring.bean.Ejemplar;
import org.alexandrialibrary.spring.dao.AbstractDAO;
import org.alexandrialibrary.spring.dao.EjemplarDAO;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class EjemplarDAOImpl extends AbstractDAO implements EjemplarDAO {
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Ejemplar> getAllEjemplar() {
		Criteria criteria = this.getCurrentSession().createCriteria(Ejemplar.class);  
		return criteria.list(); 
	}
	 
	@Override
	public Ejemplar getEjemplar(long id) {
		return (Ejemplar) this.getCurrentSession().get(Ejemplar.class, id);
	}

	@Override
	public long save(Ejemplar ejemplar) {
		return (Long) this.getCurrentSession().save(ejemplar); 
	}

	@Override
	public void update(Ejemplar ejemplar) {
		this.getCurrentSession().merge(ejemplar);
	}

	@Override
	public void delete(long id) {
		Ejemplar ejemplar = getEjemplar(id);
		this.getCurrentSession().delete(ejemplar);
	}

}
