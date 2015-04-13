package org.alexandrialibrary.spring.form;

/**
 * Formulario que permite filtrar en una vista entre préstamos devueltos o pendientes.
 *
 */
public class PrestamoIsDevueltoForm {

	/**
	 * Opciones:
	 * -1 : Sin seleccionar
	 *  0 : Pendiente
	 *  1 : Devuelto
	 */
	private Integer devuelto;
	
	public PrestamoIsDevueltoForm() {
		super();
	}
	
	public PrestamoIsDevueltoForm(Integer devuelto) {
		this.devuelto = devuelto;
	}

	public Integer getDevuelto() {
		return devuelto;
	}

	public void setDevuelto(Integer devuelto) {
		this.devuelto = devuelto;
	}
}
