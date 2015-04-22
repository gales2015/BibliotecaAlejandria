package org.alexandrialibrary.spring.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.alexandrialibrary.spring.dao.AbstractDAO;
import org.alexandrialibrary.spring.dao.UsuarioDAO;
import org.alexandrialibrary.spring.model.Usuario;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Implementación de la clase DAO de Usuario.
 * 
 * Se comunicará con la BBDD a través de Hibernate.
 */
@Repository
public class UsuarioDAOImpl extends AbstractDAO implements UsuarioDAO {
	
	/**
	 * Devuelve todos los usuarios.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Usuario> getAllUsuarios() {
		// Creamos un criterio llamando a la sesión para obtener todos los objetos de Usuario de la BBDD.
		Criteria criteria = this.getCurrentSession().createCriteria(Usuario.class);
		List<Usuario> usuarios = criteria.list();
		
		/* La colección Préstamos de Ejemplares es Lazy: 
		 * no se carga a menos que lo indiquemos explícitamente.
		 */ 
		for (Usuario usuario : usuarios) {
			// Inicializamos la colección de préstamos
			Hibernate.initialize(usuario.getPrestamos());
		}
		return usuarios; 
	}
	 
	/**
	 * Devuelve un usuario concreto.
	 */
	@Override
	public Usuario getUsuario(long id) {
		// Creamos un criterio llamando a la sesión para obtener el objeto de Usuario de la BBDD que se corresponda con una ID concreta.
		Usuario usuario = (Usuario) this.getCurrentSession().get(Usuario.class, id);
		if (usuario != null) {
			/* La colección Préstamos de Ejemplares es Lazy: 
			 * no se carga a menos que lo indiquemos explícitamente.
			 */ 
			Hibernate.initialize(usuario.getPrestamos());			
		}
		
		return usuario;
	}

	/**
	 * Devuelve todos los objetos usuario cuyo título parcial coincida.
	 * 
	 * Ejemplo: "pepe", recogería "Pepe Pérez".
	 * 
	 * Utilizaremos la restricción ilike(), que es case insensitive.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Usuario> getUsuariosByNombreParcial(String nombre) {
		// Creamos un criterio llamando a la sesión para obtener todos los objetos de Usuario de la BBDD.
		Criteria criteria = this.getCurrentSession().createCriteria(Usuario.class);
		// Le añadimos la restricción LIKE (case insensitive) con %nombre% como parámetro
		criteria.add(Restrictions.ilike("nombre", "%"+nombre+"%"));
		List<Usuario> usuarios = criteria.list();
		
		/* La colección Préstamos de Ejemplares es Lazy: 
		 * no se carga a menos que lo indiquemos explícitamente.
		 */ 
		for (Usuario usuario : usuarios) {
			// Inicializamos la colección de préstamos
			Hibernate.initialize(usuario.getPrestamos());
		}
		return usuarios; 
	}

	/**
	 * Comprobamos si el DNI del usuario pasado como parámetro está libre.
	 * 
	 * Se harán 2 distinciones:
	 *  - Si la ID de usuario es null, el usuario es nuevo y se buscará a ver si el dni está entre los usuarios existentes.
	 *  - Si la ID de usuario NO es null, el usuario existe y se buscará el dni está entre los usuarios existentes, excluyéndole a él.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean isDniAvailable(Usuario usuario) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		Query query = null;
		Long id = usuario.getId();
		if (id == null || id == 0) {
			// Es usuario nuevo
			query = (Query) this.getCurrentSession().
					createQuery("from Usuario u where u.dni = :dni");
		} else {
			// Es usuario registrado
			query = (Query) this.getCurrentSession().
					createQuery("from Usuario u where u.dni = :dni and u.id != :id");
	        query.setParameter("id", id);
		}

        query.setParameter("dni", usuario.getDni());
        query.setMaxResults(1); // Límite 1
        usuarios = query.list();
        
        // Si hay algún resultado es que el DNI no está disponible
        return (usuarios.size() == 0);
	}

	/**
	 * Comprobamos si el email del usuario pasado como parámetro está libre.
	 * 
	 * Se harán 2 distinciones:
	 *  - Si la ID de usuario es null, el usuario es nuevo y se buscará a ver si el email está entre los usuarios existentes.
	 *  - Si la ID de usuario NO es null, el usuario existe y se buscará el email está entre los usuarios existentes, excluyéndole a él.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean isEmailAvailable(Usuario usuario) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		Query query = null;
		Long id = usuario.getId();
		if (id == null || id == 0) {
			// Es usuario nuevo
			query = (Query) this.getCurrentSession().
					createQuery("from Usuario u where u.email = :email");
		} else {
			// Es usuario registrado
			query = (Query) this.getCurrentSession().
					createQuery("from Usuario u where u.email = :email and u.id != :id");
	        query.setParameter("id", id);
		}

        query.setParameter("email", usuario.getEmail());
        query.setMaxResults(1); // Límite 1
        usuarios = query.list();
        
        // Si hay algún resultado es que el Email no está disponible
        return (usuarios.size() == 0);
	}

	/**
	 * Guarda un usuario nuevo.
	 */
	@Override
	public long save(Usuario usuario) {
		return (Long) this.getCurrentSession().save(usuario); 
	}

	/**
	 * Actualiza un usuario existente.
	 */
	@Override
	public void update(Usuario usuario) {
		/* Con la ID que viene en el usuario a guardar obtenemos una copia completa 
		 * (con sus colecciones cargadas) de lo que existe actualmente en la BBDD.
		 */
		Usuario existingUsuario = getUsuario(usuario.getId());
		
		/* Sobre el usuario existente, volcamos aquellos atributos que hayan podido cambiar:
		 *  - Nombre
		 *  - Apellidos
		 *  - Dni (se habrá hecho la comprobación anteriormente, ya que debe ser único)
		 *  - Email (se habrá hecho la comprobación anteriormente, ya que debe ser único)
		 *  - Dirección
		 */
		existingUsuario.setNombre(usuario.getNombre());
		existingUsuario.setApellidos(usuario.getApellidos());
		existingUsuario.setDni(usuario.getDni());
		existingUsuario.setEmail(usuario.getEmail());
		existingUsuario.setDireccion(usuario.getDireccion());
		
		// Una vez cambiados los atributos pertinentes, guardamos el usuario existente en la BBDD.
		this.getCurrentSession().merge(existingUsuario);
	}

	/**
	 * Elimina un usuario concreto.
	 */
	@Override
	public void delete(long id) {
		Usuario usuario = getUsuario(id);
		this.getCurrentSession().delete(usuario);
	}

}
