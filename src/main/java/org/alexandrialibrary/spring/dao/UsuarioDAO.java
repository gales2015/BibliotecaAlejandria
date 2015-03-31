package org.alexandrialibrary.spring.dao;

import java.util.List;

import org.alexandrialibrary.spring.bean.Usuario;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UsuarioDAO {
		
	List<Usuario> getAllUsuario(); 

	Usuario getUsuario(long id);
	
	long save(Usuario usuario);
	
	void update(Usuario usuario);
	
	void delete(long id);
}
