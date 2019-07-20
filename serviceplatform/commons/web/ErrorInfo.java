/**
 * 
 */
package com.ruixue.serviceplatform.commons.web;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.ruixue.serviceplatform.commons.exception.InvalidField;

/**
 * the error info
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ErrorInfo {

	private boolean success = false;

	private int statusCode;

	private String errorCode;

	private String errorMessage;

	private Map<String, String> errorParams;

	private List<InvalidField> errorFields;

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
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
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the errorParams
	 */
	public Map<String, String> getErrorParams() {
		return errorParams;
	}

	/**
	 * @param errorParams
	 *            the errorParams to set
	 */
	public void setErrorParams(Map<String, String> errorParams) {
		this.errorParams = errorParams;
	}

	/**
	 * @return the errorFields
	 */
	public List<InvalidField> getErrorFields() {
		return errorFields;
	}

	/**
	 * @param errorFields
	 *            the errorFields to set
	 */
	public void setErrorFields(List<InvalidField> errorFields) {
		this.errorFields = errorFields;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ErrorInfo [success=" + success + ", statusCode=" + statusCode + ", errorCode=" + errorCode
				+ ", errorMessage=" + errorMessage + ", errorParams=" + errorParams + ", errorFields=" + errorFields
				+ "]";
	}

}
