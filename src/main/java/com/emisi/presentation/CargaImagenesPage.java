package com.emisi.presentation;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.wicket.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.io.ByteArrayOutputStream;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.imageio.plugins.dcm.DicomImageReadParam;
import org.dcm4che2.io.DicomOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emisi.model.Imagen;
import com.emisi.service.GenericService;

public class CargaImagenesPage extends TemplateIndex {
	
	private final Logger logger = LoggerFactory.getLogger(CargaImagenesPage.class);
	private static final long serialVersionUID = 1L;
	
	@SpringBean(name = "service.imagen")
	private GenericService<Imagen> imagenService;

	public CargaImagenesPage(final PageParameters parameters) {
		super(parameters);

		//add(new Label("version", getApplication().getFrameworkSettings().getVersion()));

	
    }
	
	public static byte[] toByteArray(DicomObject obj) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(baos);
		DicomOutputStream dos = new DicomOutputStream(bos);
		dos.writeDicomFile(obj);
		dos.close();
		byte[] data = baos.toByteArray();
		return data;
	}

	public static BufferedImage getPixelDataAsBufferedImage(byte[] dicomData)
			throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(dicomData);
		BufferedImage buff = null;
		Iterator<ImageReader> iter = ImageIO
				.getImageReadersByFormatName("DICOM");
		ImageReader reader = (ImageReader) iter.next();
		DicomImageReadParam param = (DicomImageReadParam) reader
				.getDefaultReadParam();
		ImageInputStream iis = ImageIO.createImageInputStream(bais);
		reader.setInput(iis, false);
		buff = reader.read(0, param);
		iis.close();
		if (buff == null)
			throw new IOException(
					"Could not read Dicom file. Maybe pixel data is invalid.");
		return buff;
	}
}
