package com.emisi.dao;

/**
 * @author mjaguero
 * 
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 3731175030335011232L;

	public DAOException() {
		super();
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}
}
