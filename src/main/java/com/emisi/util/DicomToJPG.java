package com.emisi.util;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.dcm4che2.imageio.plugins.dcm.DicomImageReadParam;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * Clase responsable de convertir una imagen dicom a jpg!
 * 
 * @author mjaguero
 */
public class DicomToJPG {

    public static File dicomToJpg(String origen, String destino) {

        File myDicomFile = new File(origen);
        BufferedImage myJpegImage = null;

        Iterator<ImageReader> iter = ImageIO.getImageReadersByFormatName("DICOM");
        ImageReader reader = (ImageReader) iter.next();

        DicomImageReadParam param = (DicomImageReadParam) reader.getDefaultReadParam();

        try {
            ImageInputStream iis = ImageIO.createImageInputStream(myDicomFile);
            reader.setInput(iis, false);
            myJpegImage = reader.read(0, param);
            iis.close();

            if (myJpegImage == null) {
                System.out.println("\nError: couldn't read dicom image!");
                return null;
            }

            File myJpegFile = new File(destino);
            OutputStream output = new BufferedOutputStream(new FileOutputStream(myJpegFile));
            //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(output);
            //encoder.encode(myJpegImage);
            output.close();
            
            return myJpegFile;

        } catch (IOException e) {
            System.out.println("\nError: couldn't read dicom image!" + e.getMessage());
            return null;
        }
    }

}
