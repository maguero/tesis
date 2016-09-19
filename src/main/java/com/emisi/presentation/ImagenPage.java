package com.emisi.presentation;

import com.emisi.dao.GenericDAOImpl;
import com.emisi.model.Imagen;
import com.emisi.util.DicomUtils;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;

public class ImagenPage extends TemplateIndex {

	// private final Logger logger =
	// LoggerFactory.getLogger(ConsultaPage.class);
	private static final long serialVersionUID = 1L;

	@SpringBean(name = "dao.imagen")
	private GenericDAOImpl<Imagen> daoImagen;

	public ImagenPage(final PageParameters parameters) {
		super(parameters);

		String imagenId = parameters.getString("id");

		Imagen imagen = daoImagen.find(imagenId, Imagen.class);

		System.out.println("ID Imagen: " + imagen.getId());

		DicomObject dicom = DicomUtils.getDicomObject(imagen.getDicom());
		
		// add(new Label("", ));
		
		// Info de Estudio
		add(new Label("StudyID", dicom.getString(Tag.StudyID)));
		add(new Label("Modality", dicom.getString(Tag.Modality)));
		add(new Label("StudyDescription", dicom.getString(Tag.StudyDescription)));
		add(new Label("StudyDate", dicom.getString(Tag.StudyDate)));

		// Info de Serie
		add(new Label("SeriesInstanceUID", dicom.getString(Tag.SeriesInstanceUID)));
		add(new Label("SeriesDescription", dicom.getString(Tag.SeriesDescription)));
		add(new Label("SeriesType", dicom.getString(Tag.SeriesType)));
		
		// Info de Paciente
		add(new Label("PatientID", dicom.getString(Tag.PatientID)));
		add(new Label("PatientAge", dicom.getString(Tag.PatientAge)));
		add(new Label("PatientWeight", dicom.getString(Tag.PatientWeight)));
		add(new Label("PatientSex", dicom.getString(Tag.PatientSex)));
		add(new Label("PatientSize", dicom.getString(Tag.PatientSize)));
		add(new Label("PatientOrientation", dicom.getString(Tag.PatientOrientation)));
		add(new Label("PatientPosition", dicom.getString(Tag.PatientPosition)));
		
		try {
			add(DicomUtils.getImageFromDicom(dicom));
		} catch (Exception e) {
			add(new Label("imagen", "Imagen no disponible."));
		}

	}

}
