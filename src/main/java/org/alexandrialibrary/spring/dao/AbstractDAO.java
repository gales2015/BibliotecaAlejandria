package org.alexandrialibrary.spring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

abstract public class AbstractDAO {
	
	@Autowired  
	protected SessionFactory sessionFactory;
	
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

}
