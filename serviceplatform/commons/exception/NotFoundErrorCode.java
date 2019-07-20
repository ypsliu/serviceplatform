/**
 * 
 */
package com.ruixue.serviceplatform.commons.exception;

import com.ruixue.serviceplatform.commons.enums.CodedEnum;

/**
 * the not found error code
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public enum NotFoundErrorCode implements CodedEnum<String> {

	/**
	 * unknown
	 */
	UNKONWN("4000"),

	/**
	 * get type by code not found
	 */
	GET_TYPE_BY_CODE_NOT_FOUND("4001"),

	/**
	 * get property by type and code not found
	 */
	GET_PROPERTY_BY_TYPE_AND_CODE_NOT_FOUND("4002"),

	/**
	 * get type by two code not found
	 */
	GET_TYPE_BY_TWO_CODE_NOT_FOUND("4003");

	private final String code;

	private NotFoundErrorCode(final String code) {
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
