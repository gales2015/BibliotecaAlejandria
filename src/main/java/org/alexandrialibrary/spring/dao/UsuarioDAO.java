package org.alexandrialibrary.spring.dao;

import java.util.List;

import org.alexandrialibrary.spring.bean.Usuario;

public interface UsuarioDAO {
		
	List<Usuario> getAllUsuarios(); 

	Usuario getUsuario(long id);

	List<Usuario> getUsuariosByNombreParcial(String nombre);

	boolean isDniAvailable(Usuario usuario);

	boolean isEmailAvailable(Usuario usuario);
	
	long save(Usuario usuario);
	
	void update(Usuario usuario);
	
	void delete(long id);

}
