/**
 * 
 */
package com.ruixue.serviceplatform.commons.web;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.ruixue.serviceplatform.commons.exception.InvalidParameterException;
import com.ruixue.serviceplatform.commons.exception.InvalidStateException;
import com.ruixue.serviceplatform.commons.exception.NotFoundException;

/**
 * the global ExceptionMapper
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionMapper.class);

	private final Map<String, ErrorInfo> exceptionsMap = new HashMap<String, ErrorInfo>();;

	private int defaultStatusCode = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();

	private String defaultErrorCode = String.valueOf(this.defaultStatusCode);

	private String defaultMessage = "unknown error";

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse(Throwable exception) {
		LOGGER.error("unhandled exception: " + exception.getMessage(), exception);
		// find the error info by class name
		final ErrorInfo errorInfo = this.findErrorInfo(exception.getClass().getName());
		if (exception instanceof InvalidParameterException) {
			// it is the InvalidParameterException
			final InvalidParameterException ipe = (InvalidParameterException) exception;
			// fill the invalid fields
			if (ipe.getFields() != null && !ipe.getFields().isEmpty()) {
				errorInfo.setErrorFields(ipe.getFields());
			}
		} else if (exception instanceof InvalidStateException) {
			// it is the InvalidStateException
			final InvalidStateException ise = (InvalidStateException) exception;
			// error code
			errorInfo.setErrorCode(ise.getErrorCode().getCode());
			// error info parameters
			errorInfo.setErrorParams(ise.getParams());
		} else if (exception instanceof NotFoundException) {
			// it is the NotFoundException
			final NotFoundException nfe = (NotFoundException) exception;
			// error code
			errorInfo.setErrorCode(nfe.getErrorCode().getCode());
			// error info parameters
			errorInfo.setErrorParams(nfe.getParams());
		}
		if (!StringUtils.isEmpty(exception.getMessage())) {
			if (!StringUtils.isEmpty(errorInfo.getErrorMessage())) {
				errorInfo.setErrorMessage(errorInfo.getErrorMessage() + ": " + exception.getMessage());
			} else {
				errorInfo.setErrorMessage(exception.getMessage());
			}
		}
		// the response
		return Response.status(errorInfo.getStatusCode()).entity(errorInfo).type(MediaType.APPLICATION_JSON_TYPE)
				.build();
	}

	/**
	 * to find the error info by exception class name
	 * 
	 * @param className
	 *            exception class name
	 * @return the error info
	 */
	private ErrorInfo findErrorInfo(String className) {
		ErrorInfo e = this.exceptionsMap.get(className);
		if (e == null) {
			e = new ErrorInfo();
			e.setStatusCode(this.defaultStatusCode);
			e.setErrorCode(this.defaultErrorCode);
			e.setErrorMessage(this.defaultMessage);
		}
		// clone the ErrorInfo
		final ErrorInfo ei = new ErrorInfo();
		ei.setStatusCode(e.getStatusCode());
		ei.setErrorCode(e.getErrorCode());
		ei.setErrorMessage(e.getErrorMessage());
		return ei;
	}

	/**
	 * @param exceptiosnMap
	 *            the exceptionsMap to set
	 */
	public void setExceptionsMap(Map<String, String> exceptionsMap) {
		if (exceptionsMap != null && !exceptionsMap.isEmpty()) {
			// replace the exceptions map
			this.exceptionsMap.clear();
			for (String exceptionClassName : exceptionsMap.keySet()) {
				// each exception
				final ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setStatusCode(this.defaultStatusCode);
				errorInfo.setErrorMessage(this.defaultMessage);
				String value = exceptionsMap.get(exceptionClassName);
				if (!StringUtils.isEmpty(value)) {
					// split the value by ':', statusCode:errorCode:errorMessage
					String[] a = value.split("\\:", 3);
					if (a != null) {
						try {
							errorInfo.setStatusCode(Integer.parseInt(a[0]));
						} catch (Exception e) {
							LOGGER.warn("invalid statusCode in exception setting: {}", value);
						}
						if (a.length >= 2) {
							try {
								errorInfo.setErrorCode(a[1]);
							} catch (Exception e) {
								LOGGER.warn("invalid errorCode in exception setting: {}", value);
							}
						}
						if (a.length == 3) {
							errorInfo.setErrorMessage(a[2]);
						}
					}
				}
				this.exceptionsMap.put(exceptionClassName, errorInfo);
			}
		}
	}

	/**
	 * @param defaultStatusCode
	 *            the defaultStatusCode to set
	 */
	public void setDefaultStatusCode(final int defaultStatusCode) {
		this.defaultStatusCode = defaultStatusCode;
	}

	/**
	 * @param defaultMessage
	 *            the defaultMessage to set
	 */
	public void setDefaultMessage(final String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

	/**
	 * @return the defaultStatusCode
	 */
	public int getDefaultStatusCode() {
		return defaultStatusCode;
	}

	/**
	 * @return the defaultMessage
	 */
	public String getDefaultMessage() {
		return defaultMessage;
	}

	/**
	 * @return the defaultErrorCode
	 */
	public String getDefaultErrorCode() {
		return defaultErrorCode;
	}

	/**
	 * @param defaultErrorCode
	 *            the defaultErrorCode to set
	 */
	public void setDefaultErrorCode(final String defaultErrorCode) {
		this.defaultErrorCode = defaultErrorCode;
	}

}
