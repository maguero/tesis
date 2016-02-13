/**
 * 
 */
package com.emisi.model;

import java.util.List;
import java.util.Set;

/**
 * @author maguero
 * 
 */
public class Estudio {

	private String IdEstudio;
	private String idPaciente;
	private String descripcion;
	private Set<Serie> series;

	/**
	 * @return the idEstudio
	 */
	public String getIdEstudio() {
		return IdEstudio;
	}

	/**
	 * @param idEstudio
	 *            the idEstudio to set
	 */
	public void setIdEstudio(String idEstudio) {
		IdEstudio = idEstudio;
	}

	/**
	 * @return the idPaciente
	 */
	public String getIdPaciente() {
		return idPaciente;
	}

	/**
	 * @param idPaciente
	 *            the idPaciente to set
	 */
	public void setIdPaciente(String idPaciente) {
		this.idPaciente = idPaciente;
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
	 * @return the series
	 */
	public Set<Serie> getSeries() {
		return series;
	}

	/**
	 * @param series
	 *            the series to set
	 */
	public void setSeries(Set<Serie> series) {
		this.series = series;
	}

}
