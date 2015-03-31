package org.alexandrialibrary.spring.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ejemplar")
public class Ejemplar implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;
	
	@ManyToOne(targetEntity = Libro.class)
	@JoinColumn(name = "libro_id", columnDefinition = "bigint")
	private Libro libro;
	
	@OneToMany(targetEntity = Prestamo.class, mappedBy = "ejemplar", cascade={CascadeType.ALL})
	private List<Prestamo> prestamos;


	/* -------- */
	public Ejemplar() {
		super();
	}

	public Ejemplar(long id, Libro libro) {
		this.id = id;
		this.libro = libro;
	}
	
	public String toString() {
		return String.valueOf(this.id) + " (" + this.libro + ")";
	}
	/* -------- */
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}

	public List<Prestamo> getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(List<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}

}
