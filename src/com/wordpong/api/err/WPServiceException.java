package com.wordpong.api.err;

public class WPServiceException extends WPException {
	private static final long serialVersionUID = 1L;

	public enum ErrorType {
		Client, Service, Unknown
	}

	/**
	 * The unique WP identifier for the service request the caller made. The WP
	 * request ID can uniquely identify the WP request, and is used for
	 * reporting an error to WP support team.
	 */
	private String requestId;

	/**
	 * The WP error code represented by this exception (ex:
	 * InvalidParameterValue).
	 */
	private String errorCode;

	/**
	 * Indicates (if known) whether this exception was the fault of the caller
	 * or the service.
	 *
	 * @see ErrorType
	 */
	private ErrorType errorType = ErrorType.Unknown;

	/** The HTTP status code that was returned with this error */
	private int statusCode;

	/**
	 * The name of the WP service that sent this error response.
	 */
	private String serviceName;

	/**
	 * Constructs a new WPServiceException with the specified message.
	 *
	 * @param message
	 *            An error message describing what went wrong.
	 */
	public WPServiceException(String message) {
		super(message);
	}

	/**
	 * Constructs a new WPServiceException with the specified message and
	 * exception indicating the root cause.
	 *
	 * @param message
	 *            An error message describing what went wrong.
	 * @param cause
	 *            The root exception that caused this exception to be thrown.
	 */
	public WPServiceException(String message, Exception cause) {
		super(message, cause);
	}

	/**
	 * Sets the WP requestId for this exception.
	 *
	 * @param requestId
	 *            The unique identifier for the service request the caller made.
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * Returns the WP request ID that uniquely identifies the service request
	 * the caller made.
	 *
	 * @return The WP request ID that uniquely identifies the service request
	 *         the caller made.
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * Sets the name of the service that sent this error response.
	 *
	 * @param serviceName
	 *            The name of the service that sent this error response.
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * Returns the name of the service that sent this error response.
	 *
	 * @return The name of the service that sent this error response.
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * Sets the WP error code represented by this exception.
	 *
	 * @param errorCode
	 *            The WP error code represented by this exception.
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Returns the WP error code represented by this exception.
	 *
	 * @return The WP error code represented by this exception.
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Sets the type of error represented by this exception (sender, receiver,
	 * or unknown), indicating if this exception was the caller's fault, or the
	 * service's fault.
	 *
	 * @param errorType
	 *            The type of error represented by this exception (sender or
	 *            receiver), indicating if this exception was the caller's fault
	 *            or the service's fault.
	 */
	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}

	/**
	 * Indicates who is responsible for this exception (caller, service, or
	 * unknown).
	 *
	 * @return A value indicating who is responsible for this exception (caller,
	 *         service, or unknown).
	 */
	public ErrorType getErrorType() {
		return errorType;
	}

	/**
	 * Sets the HTTP status code that was returned with this service exception.
	 *
	 * @param statusCode
	 *            The HTTP status code that was returned with this service
	 *            exception.
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * Returns the HTTP status code that was returned with this service
	 * exception.
	 *
	 * @return The HTTP status code that was returned with this service
	 *         exception.
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * Returns a string summary of the details of this exception including the
	 * HTTP status code, WP request ID, WP error code and error message.
	 *
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		return "Status Code: " + getStatusCode() + ", " + "WP Request ID: "
				+ getRequestId() + ", " + "WP Error Code: " + getErrorCode()
				+ ", " + "WP Error Message: " + getMessage();
	}
}
