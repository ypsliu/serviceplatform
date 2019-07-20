/**
 * 
 */
package com.ruixue.serviceplatform.commons.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * the invalid state exception
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public class InvalidStateException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5570996362589082915L;

	private final InvalidStateErrorCode errorCode;

	private final Map<String, String> params = new HashMap<String, String>();

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public InvalidStateException(final InvalidStateErrorCode errorCode, final String message, final Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	/**
	 * 
	 * @param message
	 */
	public InvalidStateException(final InvalidStateErrorCode errorCode, final String message) {
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 * to get the error code
	 * 
	 * @return the errorCode
	 */
	public InvalidStateErrorCode getErrorCode() {
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
	 * to build the InvalidStateException for: type not found can not do something
	 * 
	 * @param type
	 *            the data type
	 * @param action
	 *            the action
	 * @param actionValue
	 *            the action value
	 * @return the InvalidStateException
	 */
	public static InvalidStateException typeNotFoundCanNotDo(final DataType type, final Action action,
			final String actionValue) {
		return typeNotFoundCanNotDo(type, action, actionValue, null);
	}

	/**
	 * to build the InvalidStateException for: type not found can not do something
	 * 
	 * @param type
	 *            the data type
	 * @param action
	 *            the action
	 * @param actionValue
	 *            the action value
	 * @param cause
	 *            the cause
	 * @return the InvalidStateException
	 */
	public static InvalidStateException typeNotFoundCanNotDo(final DataType type, final Action action,
			final String actionValue, final Throwable cause) {
		if (type != null && action != null) {
			InvalidStateException e;
			final String message = "当前" + type.getTypeName() + "不存在，无法" + action.getActionName() + actionValue;
			if (cause != null) {
				e = new InvalidStateException(InvalidStateErrorCode.TYPE_NOT_FOUND_CAN_NOT_DO, message, cause);
			} else {
				e = new InvalidStateException(InvalidStateErrorCode.TYPE_NOT_FOUND_CAN_NOT_DO, message);
			}
			e.addParameter("type", type.getTypeName());
			e.addParameter("action", action.getActionName());
			e.addParameter("actionValue", actionValue);
			return e;
		}
		return null;
	}

	/**
	 * to build the InvalidStateException for: property not found can not do something
	 * 
	 * @param type
	 *            the data type
	 * @param property
	 *            the property
	 * @param action
	 *            the action
	 * @param actionValue
	 *            the action value
	 * @return the InvalidStateException
	 */
	public static InvalidStateException propertyNotFoundCanNotDo(final DataType type, final DataProperty property,
			final Action action, final String actionValue) {
		return propertyNotFoundCanNotDo(type, property, action, actionValue, null);
	}

	/**
	 * to build the InvalidStateException for: property not found can not do something
	 * 
	 * @param type
	 *            the data type
	 * @param property
	 *            the property
	 * @param action
	 *            the action
	 * @param actionValue
	 *            the action value
	 * @param cause
	 *            the cause
	 * @return the InvalidStateException
	 */
	public static InvalidStateException propertyNotFoundCanNotDo(final DataType type, final DataProperty property,
			final Action action, final String actionValue, final Throwable cause) {
		if (type != null && property != null && action != null) {
			InvalidStateException e;
			final String message = "当前" + type.getTypeName() + "的" + property.gePropertyName() + "不存在，无法"
					+ action.getActionName() + actionValue;
			if (cause != null) {
				e = new InvalidStateException(InvalidStateErrorCode.PROPERTY_NOT_FOUND_CAN_NOT_DO, message, cause);
			} else {
				e = new InvalidStateException(InvalidStateErrorCode.PROPERTY_NOT_FOUND_CAN_NOT_DO, message);
			}
			e.addParameter("type", type.getTypeName());
			e.addParameter("property", property.gePropertyName());
			e.addParameter("action", action.getActionName());
			e.addParameter("actionValue", actionValue);
			return e;
		}
		return null;
	}

	/**
	 * to build the InvalidStateException for: property not equal the value can not do something
	 * 
	 * @param type
	 *            the data type
	 * @param property
	 *            the property
	 * @param propertyValue
	 *            the property value
	 * @param action
	 *            the action
	 * @param actionValue
	 *            the action value
	 * @return the InvalidStateException
	 */
	public static InvalidStateException propertyNotEqualCanNotDo(final DataType type, final DataProperty property,
			final String propertyValue, final Action action, final String actionValue) {
		return propertyNotEqualCanNotDo(type, property, propertyValue, action, actionValue, null);
	}

	/**
	 * to build the InvalidStateException for: property not equal the value can not do something
	 * 
	 * @param type
	 *            the data type
	 * @param property
	 *            the property
	 * @param propertyValue
	 *            the property value
	 * @param action
	 *            the action
	 * @param actionValue
	 *            the action value
	 * @param cause
	 *            the cause
	 * @return the InvalidStateException
	 */
	public static InvalidStateException propertyNotEqualCanNotDo(final DataType type, final DataProperty property,
			final String propertyValue, final Action action, final String actionValue, final Throwable cause) {
		if (type != null && property != null && action != null) {
			InvalidStateException e;
			final String message = "当前" + type.getTypeName() + "的" + property.gePropertyName() + "不为" + propertyValue
					+ "，无法" + action.getActionName() + actionValue;
			if (cause != null) {
				e = new InvalidStateException(InvalidStateErrorCode.PROPERTY_NOT_EQUAL_VALUE_CAN_NOT_DO, message, cause);
			} else {
				e = new InvalidStateException(InvalidStateErrorCode.PROPERTY_NOT_EQUAL_VALUE_CAN_NOT_DO, message);
			}
			e.addParameter("type", type.getTypeName());
			e.addParameter("property", property.gePropertyName());
			e.addParameter("propertyValue", propertyValue);
			e.addParameter("action", action.getActionName());
			e.addParameter("actionValue", actionValue);
			return e;
		}
		return null;
	}

	/**
	 * to build the InvalidStateException for: property equal the value can not do something
	 * 
	 * @param type
	 *            the data type
	 * @param property
	 *            the property
	 * @param propertyValue
	 *            the property value
	 * @param action
	 *            the action
	 * @param actionValue
	 *            the action value
	 * @return the InvalidStateException
	 */
	public static InvalidStateException propertyEqualCanNotDo(final DataType type, final DataProperty property,
			final String propertyValue, final Action action, final String actionValue) {
		return propertyEqualCanNotDo(type, property, propertyValue, action, actionValue, null);
	}

	/**
	 * to build the InvalidStateException for: property equal the value can not do something
	 * 
	 * @param type
	 *            the data type
	 * @param property
	 *            the property
	 * @param propertyValue
	 *            the property value
	 * @param action
	 *            the action
	 * @param actionValue
	 *            the action value
	 * @param cause
	 *            the cause
	 * @return the InvalidStateException
	 */
	public static InvalidStateException propertyEqualCanNotDo(final DataType type, final DataProperty property,
			final String propertyValue, final Action action, final String actionValue, final Throwable cause) {
		if (type != null && property != null && action != null) {
			InvalidStateException e;
			final String message = "当前" + type.getTypeName() + "的" + property.gePropertyName() + "为" + propertyValue
					+ "，无法" + action.getActionName() + actionValue;
			if (cause != null) {
				e = new InvalidStateException(InvalidStateErrorCode.PROPERTY_EQUAL_VALUE_CAN_NOT_DO, message, cause);
			} else {
				e = new InvalidStateException(InvalidStateErrorCode.PROPERTY_EQUAL_VALUE_CAN_NOT_DO, message);
			}
			e.addParameter("type", type.getTypeName());
			e.addParameter("property", property.gePropertyName());
			e.addParameter("propertyValue", propertyValue);
			e.addParameter("action", action.getActionName());
			e.addParameter("actionValue", actionValue);
			return e;
		}
		return null;
	}

}
