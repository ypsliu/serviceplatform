/**
 * 
 */
package com.ruixue.serviceplatform.commons.validation;

import com.ruixue.serviceplatform.commons.enums.CodedEnum;

/**
 * the field check error
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public enum CheckErrorCode implements CodedEnum<String> {

	/**
	 * unknown
	 */
	UNKNOWN("9000"),
	/**
	 * the field is empty
	 */
	EMPTY("9001"),

	/**
	 * the value is invalid
	 */
	INVALID_VALUE("9002"),

	/**
	 * the value format is invalid
	 */
	INVALID_FORMAT("9003"),

	/**
	 * the value out of range
	 */
	OUT_OF_RANGE("9004"),

	/**
	 * the value is null
	 */
	NULL("9005"),

	/**
	 * the value is not null
	 */
	NOT_NULL("9006");

	private final String code;

	private CheckErrorCode(String code) {
		this.code = code;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.enums.CodedEnum#getCode()
	 */
	@Override
	public String getCode() {
		return this.code;
	}

}
