/**
 * 
 */
package com.emisi.usertype;

import com.emisi.model.ContenidoImagen;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import javax.sql.PooledConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Types;

/**
 * @author maguero
 * 
 */
public class ImagenUserType implements UserType, Serializable {

	private static final long serialVersionUID = 2308230823023l;

	private static final Class returnedClass = ContenidoImagen.class;
	private static final int SQL_TYPE = Types.STRUCT;
	private static final int[] SQL_TYPES = new int[] { SQL_TYPE };

	private static final String DB_OBJECT_TYPE = "NUEVA_IMAGEN_T";

	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	public Class returnedClass() {
		return returnedClass;
	}

	public int hashCode(Object _obj) {
		return _obj.hashCode();
	}

	public Object assemble(Serializable _cached, Object _owner)
			throws HibernateException {
		try {
			return null; // HibernateXMLType.stringToDom((String) _cached);
		} catch (Exception e) {
			throw new HibernateException(
					"Could not assemble String to Document", e);
		}
	}

	public Serializable disassemble(Object _obj) throws HibernateException {
		try {
			return null; // HibernateXMLType.domToString((Document) _obj);
		} catch (Exception e) {
			throw new HibernateException(
					"Could not disassemble Document to Serializable", e);
		}
	}

	public Object replace(Object _orig, Object _tar, Object _owner) {
		return deepCopy(_orig);
	}

	public boolean equals(Object arg0, Object arg1) throws HibernateException {
		if (arg0 == null && arg1 == null)
			return true;
		else if (arg0 == null && arg1 != null)
			return false;
		else
			return arg0.equals(arg1);
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object arg2)
			throws HibernateException, SQLException {

		final Struct struct = (Struct) rs.getObject(names[0]);

		if (rs.wasNull()) {

			return null;

		}

		final ContenidoImagen imagen = new ContenidoImagen();

		imagen.setIdImagen(struct.getAttributes()[0].toString());
//		imagen.setDicom((OrdDicom) struct.getAttributes()[1]);
		
		
		Struct structDCM = (Struct) struct.getAttributes()[1];
		((STRUCT) structDCM).getSQLTypeName();
		((STRUCT) structDCM).getOracleAttributes();
		
		
		imagen.setDiagnostico((String) struct.getAttributes()[2]);
		imagen.setModalidad((String) struct.getAttributes()[3]);
		
//		org.apache.commons.dbcp.DelegatingResultSet drs = (org.apache.commons.dbcp.DelegatingResultSet)rs;
//		OracleResultSet ors = (OracleResultSet)drs.getInnermostDelegate();
		
		// extraer la imagen dicom
		//OrdDicom dicom = (OrdDicom) ors.getORAData("this_.imagen.dicom", OrdDicom.getORADataFactory());
		

		return imagen;
	}

	public void nullSafeSet(PreparedStatement statement, Object value, int index)
			throws HibernateException, SQLException {

		if (value == null) {

			statement.setNull(index, SQL_TYPE, DB_OBJECT_TYPE);

		} else {

			final ContenidoImagen imagen = (ContenidoImagen) value;

			final Object[] values = new Object[] { imagen.getIdImagen()
					,imagen.getDicom() 
					};

			//final Connection connection = statement.getConnection();
			final Connection hlp = statement.getConnection().getMetaData().getConnection();
			OracleConnection oc = null;
			if(hlp instanceof OracleConnection)
				oc = (OracleConnection) hlp;
			else if(hlp instanceof PooledConnection)
				oc = (OracleConnection) ((PooledConnection)hlp).getConnection();

			final STRUCT struct = new STRUCT(StructDescriptor.createDescriptor(
					DB_OBJECT_TYPE,
					oc), oc, values);

			statement.setObject(index, struct, SQL_TYPE);

		}

	}

	public Object deepCopy(Object value) throws HibernateException {
		if (value == null)
			return null;

		return (ContenidoImagen) ((ContenidoImagen) value);
	}

	public boolean isMutable() {
		return false;
	}

}
