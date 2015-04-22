package org.alexandrialibrary.spring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase DAO base.
 */
abstract public class AbstractDAO {
	
	/**
	 * Sesión que actuará con la capa de persistencia, en este caso el ORM Hibernate.
	 * 
	 * No necesita ser instanciada gracias a la anotación @Autowired.
	 * 
	 * La sesión será accesible antes de que el controlador mande los objetos a la vista. 
	 * Después será destruida, así que todo lo que no se haya consultado entonces, no será accesible.
	 */
	@Autowired  
	protected SessionFactory sessionFactory;
	
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

}
