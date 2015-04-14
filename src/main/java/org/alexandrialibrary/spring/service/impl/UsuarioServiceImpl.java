package org.alexandrialibrary.spring.service.impl;

import java.util.List;

import org.alexandrialibrary.spring.bean.Usuario;
import org.alexandrialibrary.spring.dao.UsuarioDAO;
import org.alexandrialibrary.spring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
    private UsuarioDAO usuarioDAO;
	
	@Override
	public List<Usuario> getAllUsuarios() {
		return usuarioDAO.getAllUsuarios();
	}

	@Override
	public Usuario getUsuario(long id) {
		return usuarioDAO.getUsuario(id);
	}

	@Override
	public List<Usuario> getUsuariosByNombreParcial(String nombre) {
		return usuarioDAO.getUsuariosByNombreParcial(nombre);
	}

	@Override
	public boolean isDniAvailable(Usuario usuario) {
		return usuarioDAO.isDniAvailable(usuario);
	}

	@Override
	public boolean isEmailAvailable(Usuario usuario) {
		return usuarioDAO.isEmailAvailable(usuario);
	}

	@Override
	public long save(Usuario usuario) {
		return usuarioDAO.save(usuario);
	}

	@Override
	public void update(Usuario usuario) {
		usuarioDAO.update(usuario);
	}

	@Override
	public void delete(long id) {
		usuarioDAO.delete(id);
	}

}
