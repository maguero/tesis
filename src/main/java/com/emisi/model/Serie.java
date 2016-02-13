/**
 * 
 */
package com.emisi.model;

import java.util.Set;

/**
 * @author maguero
 * 
 */
public class Serie {

	private String idSerie;
	private String descripcion;
	private String fkEstudio;
	private Set<Imagen> imagenes;

	/**
	 * @return the idSerie
	 */
	public String getIdSerie() {
		return idSerie;
	}

	/**
	 * @param idSerie
	 *            the idSerie to set
	 */
	public void setIdSerie(String idSerie) {
		this.idSerie = idSerie;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the fkEstudio
	 */
	public String getFkEstudio() {
		return fkEstudio;
	}

	/**
	 * @param fkEstudio
	 *            the fkEstudio to set
	 */
	public void setFkEstudio(String fkEstudio) {
		this.fkEstudio = fkEstudio;
	}

	/**
	 * @return the imagenes
	 */
	public Set<Imagen> getImagenes() {
		return imagenes;
	}

	/**
	 * @param imagenes
	 *            the imagenes to set
	 */
	public void setImagenes(Set<Imagen> imagenes) {
		this.imagenes = imagenes;
	}

}
