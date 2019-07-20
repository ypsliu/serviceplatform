/**
 * 
 */
package com.ruixue.serviceplatform.commons.exception;

import java.io.Serializable;

/**
 * the invalid field
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public class InvalidField implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2236761072236237718L;

	private String field;

	private String errorCode;

	private String message;

	private String value;

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field
	 *            the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InvalidField [field=" + field + ", errorCode=" + errorCode + ", message=" + message + ", value="
				+ value + "]";
	}

}
