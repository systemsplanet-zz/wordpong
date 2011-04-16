package com.wordpong.api.svc.dao.err;


public class DaoExceptionUserNotFound extends DaoException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new WPServiceExceptionUserNotFound with the specified
	 * message.
	 * 
	 * @param message
	 *            An error message describing what went wrong.
	 */
	public DaoExceptionUserNotFound(String message) {
		super(message);
	}

	/**
	 * Constructs a new WPServiceExceptionUserNotFound with the specified
	 * message and exception indicating the root cause.
	 * 
	 * @param message
	 *            An error message describing what went wrong.
	 * @param cause
	 *            The root exception that caused this exception to be thrown.
	 */
	public DaoExceptionUserNotFound(String message, Exception cause) {
		super(message, cause);
	}

	/**
	 * Returns a string summary of the details of this exception including the
	 * HTTP status code, WP request ID, WP error code and error message.
	 * 
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		return "DAO Error Message: " + getMessage();
	}
}
