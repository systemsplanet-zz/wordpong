package com.wordpong.api.svc.dao.err;

public class DaoException extends Exception {
	  private static final long serialVersionUID = 1L; 

	    public DaoException(String message, Throwable t) {
	        super(message, t);
	    }

	    public DaoException(String message) {
	        super(message);
	    } 
}
