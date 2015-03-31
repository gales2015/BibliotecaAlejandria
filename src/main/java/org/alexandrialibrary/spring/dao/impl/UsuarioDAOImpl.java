package org.alexandrialibrary.spring.dao.impl;

import java.util.List;

import org.alexandrialibrary.spring.bean.Usuario;
import org.alexandrialibrary.spring.dao.AbstractDAO;
import org.alexandrialibrary.spring.dao.UsuarioDAO;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UsuarioDAOImpl extends AbstractDAO implements UsuarioDAO {
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Usuario> getAllUsuario() {
		Criteria criteria = this.getCurrentSession().createCriteria(Usuario.class);  
		return criteria.list(); 
	}
	 
	@Override
	public Usuario getUsuario(long id) {
		return (Usuario) this.getCurrentSession().get(Usuario.class, id);
	}

	@Override
	public long save(Usuario usuario) {
		return (Long) this.getCurrentSession().save(usuario); 
	}

	@Override
	public void update(Usuario usuario) {
		this.getCurrentSession().merge(usuario);
	}

	@Override
	public void delete(long id) {
		Usuario usuario = getUsuario(id);
		this.getCurrentSession().delete(usuario);
	}

}
