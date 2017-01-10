/**
 * 
 */
package com.emisi.usertype;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.PooledConnection;

import oracle.jdbc.OracleResultSet;
import oracle.jdbc.driver.OracleConnection;
import oracle.ord.dicom.OrdDicom;

import org.apache.commons.dbcp.DelegatingPreparedStatement;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * @author maguero
 * 
 */
public class ImagenDicomUserType implements UserType, Serializable {

	private static final long serialVersionUID = 2308230823023l;
	private static final Class returnedClass = OrdDicom.class;
	private static final int[] SQL_TYPES = new int[] { OrdDicom._SQL_TYPECODE };

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
			return null; //HibernateXMLType.stringToDom((String) _cached);
		} catch (Exception e) {
			throw new HibernateException(
					"Could not assemble String to Document", e);
		}
	}

	public Serializable disassemble(Object _obj) throws HibernateException {
		try {
			return null; //HibernateXMLType.domToString((Document) _obj);
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

		org.apache.commons.dbcp.DelegatingResultSet drs = (org.apache.commons.dbcp.DelegatingResultSet)rs;
		OracleResultSet ors = (OracleResultSet)drs.getInnermostDelegate();
		
		// extraer la imagen dicom
		return (OrdDicom) ors.getORAData(names[0], OrdDicom.getORADataFactory());
	}

	public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i)
			throws HibernateException, SQLException {
		if( o == null) {
			preparedStatement.setNull(i, Types.STRUCT, OrdDicom._SQL_NAME);
		} else {
			if( o instanceof OrdDicom) {
				OrdDicom imagen = (OrdDicom) o;				
				final Connection hlp = preparedStatement.getConnection().getMetaData().getConnection();
				OracleConnection oc = null;
				if(hlp instanceof OracleConnection)
					oc = (OracleConnection) hlp;
				else if(hlp instanceof PooledConnection)
					oc = (OracleConnection) ((PooledConnection)hlp).getConnection();
				
				preparedStatement.setObject( i, imagen.create(imagen.toDatum(oc), i));   
			}
		}

	}

	public Object deepCopy(Object value) throws HibernateException {
		if (value == null)
			return null;

		return (OrdDicom) ((OrdDicom) value);
	}

	public boolean isMutable() {
		return false;
	}

}
