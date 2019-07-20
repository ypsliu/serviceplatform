/**
 * 
 */
package com.ruixue.serviceplatform.commons.exception;

import java.util.List;

/**
 * the invalid parameter exception
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public class InvalidParameterException extends IllegalArgumentException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8643739310102915910L;

	private final List<InvalidField> fields;

	public InvalidParameterException(final List<InvalidField> fields) {
		super();
		this.fields = fields;
	}

	public InvalidParameterException(final String message, final List<InvalidField> fields, Throwable cause) {
		super(message, cause);
		this.fields = fields;
	}

	public InvalidParameterException(String s, final List<InvalidField> fields) {
		super(s);
		this.fields = fields;
	}

	public InvalidParameterException(Throwable cause, final List<InvalidField> fields) {
		super(cause);
		this.fields = fields;
	}

	/**
	 * @return the fields
	 */
	public List<InvalidField> getFields() {
		return fields;
	}

}
