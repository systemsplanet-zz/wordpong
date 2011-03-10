package com.wordpong.api.err;

/**
 * Convert checked exception to a runtime exception
 *
 */
public class WPExceptionRuntime  extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public WPExceptionRuntime() {
    }

    public WPExceptionRuntime(String message, Throwable cause) {
        super(message, cause);
    }

    public WPExceptionRuntime(String message) {
        super(message);
    }

    public WPExceptionRuntime(Throwable cause) {
        super(cause);
    }
}