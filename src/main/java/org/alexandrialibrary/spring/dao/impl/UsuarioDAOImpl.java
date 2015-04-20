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

@Repository
public class UsuarioDAOImpl extends AbstractDAO implements UsuarioDAO {
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Usuario> getAllUsuarios() {
		Criteria criteria = this.getCurrentSession().createCriteria(Usuario.class);
		List<Usuario> usuarios = criteria.list();
		for (Usuario usuario : usuarios) {
			Hibernate.initialize(usuario.getPrestamos());
		}
		return usuarios; 
	}
	 
	@Override
	public Usuario getUsuario(long id) {
		Usuario usuario = (Usuario) this.getCurrentSession().get(Usuario.class, id);
		if (usuario != null) {
			Hibernate.initialize(usuario.getPrestamos());			
		}
		
		return usuario;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Usuario> getUsuariosByNombreParcial(String nombre) {
		Criteria criteria = this.getCurrentSession().createCriteria(Usuario.class);
		criteria.add(Restrictions.ilike("nombre", "%"+nombre+"%"));
		List<Usuario> usuarios = criteria.list();
		for (Usuario usuario : usuarios) {
			Hibernate.initialize(usuario.getPrestamos());
		}
		return usuarios; 
	}

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

	@Override
	public long save(Usuario usuario) {
		return (Long) this.getCurrentSession().save(usuario); 
	}

	@Override
	public void update(Usuario usuario) {
		Usuario existingUsuario = getUsuario(usuario.getId());
		
		existingUsuario.setNombre(usuario.getNombre());
		existingUsuario.setApellidos(usuario.getApellidos());
		existingUsuario.setDni(usuario.getDni());
		existingUsuario.setEmail(usuario.getEmail());
		existingUsuario.setDireccion(usuario.getDireccion());
		
		this.getCurrentSession().merge(existingUsuario);
	}

	@Override
	public void delete(long id) {
		Usuario usuario = getUsuario(id);
		this.getCurrentSession().delete(usuario);
	}

}
