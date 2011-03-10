package com.wordpong.api.err;

public class WPException extends Exception {
	  private static final long serialVersionUID = 1L; 

	    public WPException(String message, Throwable t) {
	        super(message, t);
	    }

	    public WPException(String message) {
	        super(message);
	    } 
}
