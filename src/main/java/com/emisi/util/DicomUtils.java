/**
 * 
 */
package com.emisi.util;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import oracle.ord.dicom.OrdDicom;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.resource.BufferedDynamicImageResource;
import org.apache.wicket.util.io.ByteArrayOutputStream;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.imageio.plugins.dcm.DicomImageReadParam;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.io.DicomOutputStream;

/**
 * @author maguero
 * 
 */
public class DicomUtils {

	/**
	 * Conver ORDDICOM to DCM4CHE Dicom object
	 * 
	 * @param dicom
	 * @return
	 */
	public static DicomObject getDicomObject(OrdDicom dicom) {

		DicomObject dcmObj = null;
		DicomInputStream din = null;
		InputStream binaryStream = null;
		try {
			binaryStream = dicom.getBlobContent().getBinaryStream();
			din = new DicomInputStream(binaryStream);
			dcmObj = din.readDicomObject();
		} catch (Exception e) {
			// TODO log ERROR
			e.printStackTrace();
		}

		return dcmObj;
	}

	/**
	 * 
	 * @param dcmObj
	 * @return
	 * @throws IOException
	 */
	public static Image getImageFromDicom(DicomObject dcmObj)
			throws IOException {
		BufferedImage _img = null;
		_img = getPixelDataAsBufferedImage(toByteArray(dcmObj));

		BufferedDynamicImageResource bufferedDynamicImage = new BufferedDynamicImageResource();
		bufferedDynamicImage.setImage(_img);
		return new Image("imagen", bufferedDynamicImage);
	}

	/**
	 * 
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	private static byte[] toByteArray(DicomObject obj) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(baos);
		DicomOutputStream dos = new DicomOutputStream(bos);
		dos.writeDicomFile(obj);
		dos.close();
		byte[] data = baos.toByteArray();
		return data;
	}

	/**
	 * 
	 * @param dicomData
	 * @return
	 * @throws IOException
	 */
	private static BufferedImage getPixelDataAsBufferedImage(byte[] dicomData)
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
