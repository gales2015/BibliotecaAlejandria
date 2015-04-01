package org.alexandrialibrary.spring.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "libro")
public class Libro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "isbn")
	private long isbn;

	@Column(name = "titulo")
	private String titulo;
	
	@Column(name = "autor")
	private String autor;
	
	@Column(name = "categoria")
	private String categoria;
	
	@OneToMany(targetEntity = Ejemplar.class, mappedBy = "libro", cascade={CascadeType.ALL})
	private List<Ejemplar> ejemplares;
	

	/* -------- */
	public Libro() {
		super();
	}

	public Libro(String titulo, String autor, String categoria) {
		this.titulo = titulo;
		this.autor = autor;
		this.categoria = categoria;
	}
	
	public String toString() {
		return this.titulo + " (" + this.autor + ")";
	}
	/* -------- */
	

	public long getIsbn() {
		return isbn;
	}

	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public List<Ejemplar> getEjemplares() {
		return ejemplares;
	}

	public void setEjemplares(List<Ejemplar> ejemplares) {
		this.ejemplares = ejemplares;
	}
	
	public int getTotalEjemplares() {
		return ejemplares.size();
	}
	
	public int getTotalEjemplaresLibres() {
		// TODO Calcular ejemplares libres de la lista
		return 0;
	}
	
	public int getTotalEjemplaresPrestados() {
		return (getTotalEjemplares() - getTotalEjemplaresLibres());
	}

}
