package org.alexandrialibrary.spring.service;

import java.util.List;

import org.alexandrialibrary.spring.model.Usuario;

/**
 * Interfaz Service para Usuarios con los métodos a ser implementados.
 * 
 * En las implementaciones se deberá llamar a los métodos pertinentes de las clases DAO.
 */
public interface UsuarioService {
		
	List<Usuario> getAllUsuarios(); 

	Usuario getUsuario(long id);

	List<Usuario> getUsuariosByNombreParcial(String nombre);

	boolean isDniAvailable(Usuario usuario);

	boolean isEmailAvailable(Usuario usuario);
	
	long save(Usuario usuario);
	
	void update(Usuario usuario);
	
	void delete(long id);

}
