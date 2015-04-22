package org.alexandrialibrary.spring.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Clase Libro del modelo. Corresponde a la tabla 'libro'.
 *
 */
@Entity
@Table(name = "libro")
public class Libro implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Atributos de Libro, que a su vez serán campos de la tabla 'libro'.
	 * 
	 * Las anotaciones indican a JPA (Java Persistence API) qué papel representa cada uno.
	 */
	
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
	
	@OneToMany(targetEntity = Ejemplar.class, mappedBy = "libro", cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private List<Ejemplar> ejemplares;
	

	/* -------- */
	public Libro() {
		super();
	}
	
	public Libro(String titulo) {
		this.titulo = titulo;
	}

	public Libro(String titulo, String autor, String categoria) {
		
		this.autor = autor;
		this.categoria = categoria;
	}

	public String toString() {
		return this.titulo + " (" + this.autor + ")";
	}
	/* -------- */
	
	/*
	 * Getters/Setters
	 */

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
	
	/**
	 * Recorre todos sus ejemplares y devuelve cuántos de ellos están prestados.
	 */
	public int getTotalEjemplaresPrestados() {
		int total = 0;
		for (Ejemplar ejemplar : ejemplares) {
			if (ejemplar.isPrestado()) {
				total++;
			}
		}
		return total;
	}
	
	public int getTotalEjemplaresLibres() {
		return (getTotalEjemplares() - getTotalEjemplaresPrestados());
	}

}
