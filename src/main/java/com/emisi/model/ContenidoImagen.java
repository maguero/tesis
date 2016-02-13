/**
 * 
 */
package com.emisi.model;

import java.io.Serializable;

import oracle.ord.dicom.OrdDicom;

/**
 * @author maguero
 *
 */
public class ContenidoImagen implements Serializable {

	private static final long serialVersionUID = -6187948451616397278L;
	
	private String idImagen;
	private OrdDicom dicom;
	private String diagnostico;
	private String modalidad;

	/**
	 * @return the idImagen
	 */
	public String getIdImagen() {
		return idImagen;
	}

	/**
	 * @param idImagen
	 *            the idImagen to set
	 */
	public void setIdImagen(String idImagen) {
		this.idImagen = idImagen;
	}

	/**
	 * @return the dicom
	 */
	public OrdDicom getDicom() {
		return dicom;
	}

	/**
	 * @param dicom
	 *            the dicom to set
	 */
	public void setDicom(OrdDicom dicom) {
		this.dicom = dicom;
	}

	/**
	 * @return the diagnostico
	 */
	public String getDiagnostico() {
		return diagnostico;
	}

	/**
	 * @param diagnostico
	 *            the diagnostico to set
	 */
	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	/**
	 * @return the modalidad
	 */
	public String getModalidad() {
		return modalidad;
	}

	/**
	 * @param modalidad
	 *            the modalidad to set
	 */
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}
	
}
