/**
 * 
 */
package com.ruixue.serviceplatform.commons.exception;

/**
 * the duplicate exception
 * 
 * @author shangchunming@rkylin.com.cn
 * 
 */
public class DuplicateException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -557252238603381764L;

	/**
	 * 
	 */
	public DuplicateException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DuplicateException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public DuplicateException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DuplicateException(Throwable cause) {
		super(cause);
	}

}
