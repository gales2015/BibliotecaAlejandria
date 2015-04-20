package org.alexandrialibrary.spring.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "prestamo", uniqueConstraints=
	@UniqueConstraint(columnNames={"fecha_inicio", "usuario_id", "ejemplar_id"}))
public class Prestamo implements Serializable {

	private static final long serialVersionUID = 1L;

	private static DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("es", "ES"));
	
	public static final int DIAS_PRESTAMO = 30;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "fecha_inicio")
	private Date fechaInicio;

	@Column(name = "fecha_fin")
	private Date fechaFin;

	@Column(name = "fecha_devolucion")
	private Date fechaDevolucion;

	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "usuario_id", columnDefinition = "bigint")
	private Usuario usuario;

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


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public String getFechaInicioFormat() {
		return dateFormat.format(fechaInicio);
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public String getFechaFinFormat() {
		return dateFormat.format(fechaFin);
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaDevolucion() {
		return fechaDevolucion;
	}

	public String getFechaDevolucionFormat() {
		return dateFormat.format(fechaDevolucion);
	}

	public void setFechaDevolucion(Date fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}
	
	public boolean isDevuelto() {
		return (this.fechaDevolucion != null);
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
