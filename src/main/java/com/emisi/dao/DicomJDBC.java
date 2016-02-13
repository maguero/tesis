/**
 * 
 */
package com.emisi.dao;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.Statement;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import oracle.ord.dicom.OrdDicom;

import com.emisi.model.Imagen;

/**
 * @author maguero
 * 
 */
public class DicomJDBC {

	private static String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
	private static String DB_HOST = "jdbc:oracle:thin:@localhost:1521:emisi";
	private static String DB_USER = "mjaguero";
	private static String DB_PASSWORD = "admin";

	/**
	 * Clase para conectar a la base de datos
	 * 
	 * @return
	 * @throws Exception
	 */
	public OracleConnection connect() throws Exception {

		Class.forName(DRIVER_CLASS);
		OracleConnection con = (OracleConnection) DriverManager.getConnection(
				DB_HOST, DB_USER, DB_PASSWORD);
		con.setAutoCommit(false);
		return con;
	}

	/**
	 * saveDicomImage(lista.toArray(new FileInputStream[lista.size()]));
	 * saveDicomImage(new FileInputStream(new File("")));
	 * 
	 * @return
	 */
	public boolean saveImagen(Imagen img, File dicomFile) {

		try {

			// connect to database
			OracleConnection con = connect();
			Statement s = con.createStatement();

			// enable ORDDicom
			CallableStatement cs = con
					.prepareCall("{ CALL ordsys.ord_dicom.setDataModel() }");
			cs.executeUpdate();

			if (!exists(img)) {

				System.out.println("Inserting empty image... " + img.getIdImagen());
				s.executeUpdate(getInsertImagenCompleta(img));
				System.out.println("Empty image inserted.");

			}
			
			// traer la imagen vacia desde la base
			OracleResultSet rs = (OracleResultSet) s
					.executeQuery("select dicom from imagen_dicom_tab where id_imagen = '"
							+ img.getIdImagen() + "' for update ");
			
			if (rs.next()) {
				
				// extraer la imagen dicom
				OrdDicom image = (OrdDicom) rs.getORAData(1, OrdDicom.getORADataFactory());
				
				if (image.isLocal()) {
					System.out.println("Image is stored locally.");
				} else {
					System.out.println("Image is stored externally at "
							+ image.getSourceInformation());
				}
				
				// cargar la imagen
				if (!image.loadContent(dicomFile, true)) {
					System.out.println("loadContent failed for image: " + dicomFile);
					return false;
				}
				
				// actualizar la imagen
				image.setProperties();
				OraclePreparedStatement stmt1 = (OraclePreparedStatement) con
						.prepareCall("update imagen_dicom_tab set dicom = ? where id_imagen = "
								+ img.getIdImagen());
				stmt1.setORAData(1, image);
				stmt1.execute();
				stmt1.close();
				
				System.out.println("Image updated. " + dicomFile.getName());
			} else {
				System.out.println("Record not found.");
			}

			// commit
			con.commit();
			con.close();

		} catch (Exception e) {
			System.out.println("Error saving image. " + e.getMessage());
			e.printStackTrace();
		}

		return true;
	}

	private boolean exists(Imagen img) {
		// TODO Auto-generated method stub
		return false;
	}

	private String getInsertImagenCompleta(Imagen img) {
		return "insert into imagen_dicom_tab(id_imagen, dicom) " 
				+ "values ('" + img.getIdImagen() + "', " + "ORDSYS.ORDDicom()"
				+ ")";
	}

}
