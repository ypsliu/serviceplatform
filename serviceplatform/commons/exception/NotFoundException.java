/**
 * 
 */
package com.ruixue.serviceplatform.commons.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * the not found exception
 * 
 * @author shangchunming@rkylin.com.cn
 * 
 */
public class NotFoundException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8482417659818731233L;

	private final NotFoundErrorCode errorCode;

	private final Map<String, String> params = new HashMap<String, String>();

	/**
	 * @param message
	 * @param cause
	 */
	public NotFoundException(final NotFoundErrorCode errorCode, final String message, final Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	/**
	 * @param message
	 */
	public NotFoundException(final NotFoundErrorCode errorCode, final String message) {
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 * to get the error code
	 * 
	 * @return the errorCode
	 */
	public NotFoundErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * to get the error info parameters
	 * 
	 * @return the error info parameters
	 */
	public Map<String, String> getParams() {
		return params;
	}

	/**
	 * to add the parameter
	 * 
	 * @param name
	 *            the parameter name
	 * @param value
	 *            the value
	 */
	private void addParameter(String name, String value) {
		this.params.put(name, value);
	}

	/**
	 * to build the NotFoundException for: get type by code not found
	 * 
	 * @param type
	 *            the data type
	 * @param code
	 *            the code
	 * @return the NotFoundException
	 */
	public static NotFoundException getTypeByCodeNotFound(final DataType type, final String code) {
		return getTypeByCodeNotFound(type, code, null);
	}

	/**
	 * to build the NotFoundException for: get type by code not found
	 * 
	 * @param type
	 *            the data type
	 * @param code
	 *            the code
	 * @param cause
	 *            the cause
	 * @return the NotFoundException
	 */
	public static NotFoundException getTypeByCodeNotFound(final DataType type, final String code, final Throwable cause) {
		if (type != null) {
			NotFoundException e;
			final String message = "查找不到编号为" + code + "的" + type.getTypeName();
			if (cause != null) {
				e = new NotFoundException(NotFoundErrorCode.GET_TYPE_BY_CODE_NOT_FOUND, message, cause);
			} else {
				e = new NotFoundException(NotFoundErrorCode.GET_TYPE_BY_CODE_NOT_FOUND, message);
			}
			e.addParameter("type", type.getTypeName());
			e.addParameter("code", code);
			return e;
		}
		return null;
	}

	/**
	 * to build the NotFoundException for: get property by type and code not found
	 * 
	 * @param type
	 *            the data type
	 * @param code
	 *            the code
	 * @param property
	 *            the data property
	 * @return the NotFoundException
	 */
	public static NotFoundException getPropertyByTypeAndCodeNotFound(final DataType type, final String code,
			final DataProperty property) {
		return getPropertyByTypeAndCodeNotFound(type, code, property, null);
	}

	/**
	 * to build the NotFoundException for: get property by type and code not found
	 * 
	 * @param type
	 *            the data type
	 * @param code
	 *            the code
	 * @param property
	 *            the data property
	 * @param cause
	 *            the cause
	 * @return the NotFoundException
	 */
	public static NotFoundException getPropertyByTypeAndCodeNotFound(final DataType type, final String code,
			final DataProperty property, final Throwable cause) {
		if (type != null && property != null) {
			NotFoundException e;
			final String message = "查找不到" + type.getTypeName() + "编号为" + code + "的" + property.gePropertyName();
			if (cause != null) {
				e = new NotFoundException(NotFoundErrorCode.GET_PROPERTY_BY_TYPE_AND_CODE_NOT_FOUND, message, cause);
			} else {
				e = new NotFoundException(NotFoundErrorCode.GET_PROPERTY_BY_TYPE_AND_CODE_NOT_FOUND, message);
			}
			e.addParameter("type", type.getTypeName());
			e.addParameter("code", code);
			e.addParameter("property", property.gePropertyName());
			return e;
		}
		return null;
	}

	/**
	 * to build the NotFoundException for: get type by two code not found
	 * 
	 * @param type1
	 *            the data type
	 * @param code1
	 *            the code
	 * @param type2
	 *            the data type
	 * @param code2
	 *            the code
	 * @return the NotFoundException
	 */
	public static NotFoundException getTypeByCodeNotFound(final DataType type1, final String code1,
			final DataType type2, final String code2) {
		return getTypeByCodeNotFound(type1, code1, type2, code2, null);
	}

	/**
	 * to build the NotFoundException for: get type by code not found
	 * 
	 * @param type1
	 *            the data type
	 * @param code1
	 *            the code
	 * @param type2
	 *            the data type
	 * @param code2
	 *            the code
	 * @param cause
	 *            the cause
	 * @return the NotFoundException
	 */
	public static NotFoundException getTypeByCodeNotFound(final DataType type1, final String code1,
			final DataType type2, final String code2, final Throwable cause) {
		if (type1 != null && type2 != null) {
			NotFoundException e;
			final String message = "查找不到" + type1.getTypeName() + "编号为" + code1 + "且" + type2.getTypeName() + "编号为"
					+ code2 + "的信息";
			if (cause != null) {
				e = new NotFoundException(NotFoundErrorCode.GET_TYPE_BY_TWO_CODE_NOT_FOUND, message, cause);
			} else {
				e = new NotFoundException(NotFoundErrorCode.GET_TYPE_BY_TWO_CODE_NOT_FOUND, message);
			}
			e.addParameter("type1", type1.getTypeName());
			e.addParameter("code1", code1);
			e.addParameter("type2", type2.getTypeName());
			e.addParameter("code2", code2);
			return e;
		}
		return null;
	}

}
