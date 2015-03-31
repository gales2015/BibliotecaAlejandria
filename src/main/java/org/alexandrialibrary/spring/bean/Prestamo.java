package org.alexandrialibrary.spring.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "prestamo")
public class Prestamo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "fecha_inicio")
	private Date fechaInicio;

	@Column(name = "fecha_fin")
	private Date fechaFin;

	@Column(name = "fecha_devolucion")
	private Date fechaDevolucion;

	@Id
	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "usuario_id", columnDefinition = "bigint")
	private Usuario usuario;

	@Id
	@ManyToOne(targetEntity = Ejemplar.class)
	@JoinColumn(name = "ejemplar_id", columnDefinition = "bigint")
	private Ejemplar ejemplar;


	/* -------- */
	public Prestamo() {
		super();
	}

	public Prestamo(Date fechaInicio, Date fechaFin, Date fechaDevolucion, Usuario usuario, Ejemplar ejemplar) {
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.fechaDevolucion = fechaDevolucion;
		this.usuario = usuario;
		this.ejemplar = ejemplar;		
	}
	/* -------- */
	

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(Date fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Ejemplar getEjemplar() {
		return ejemplar;
	}

	public void setEjemplar(Ejemplar ejemplar) {
		this.ejemplar = ejemplar;
	}
	
}
