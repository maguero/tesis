/**
 * 
 */
package com.emisi.usertype;

import oracle.ord.dicom.OrdDicom;

import org.hibernate.dialect.Oracle10gDialect;

/**
 * @author maguero
 * 
 */
public class DicomOracle10gDialect extends Oracle10gDialect {

	public DicomOracle10gDialect() {
		super();

		registerColumnType(OrdDicom._SQL_TYPECODE, OrdDicom._SQL_NAME);
		registerHibernateType(OrdDicom._SQL_TYPECODE, OrdDicom._SQL_NAME);

	}
	
}
