/**
 * 
 */
package com.ruixue.serviceplatform.commons.exception;

import com.ruixue.serviceplatform.commons.enums.CodedEnum;

/**
 * the invalid state error code
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public enum InvalidStateErrorCode implements CodedEnum<String> {

	/**
	 * unknown
	 */
	UNKONWN("6000"),

	/**
	 * type not found can not do something
	 */
	TYPE_NOT_FOUND_CAN_NOT_DO("6001"),

	/**
	 * property not found can not do something
	 */
	PROPERTY_NOT_FOUND_CAN_NOT_DO("6002"),

	/**
	 * property not equal the value can not do something
	 */
	PROPERTY_NOT_EQUAL_VALUE_CAN_NOT_DO("6003"),

	/**
	 * property equal the value can not do something
	 */
	PROPERTY_EQUAL_VALUE_CAN_NOT_DO("6004");

	private final String code;

	private InvalidStateErrorCode(final String code) {
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
