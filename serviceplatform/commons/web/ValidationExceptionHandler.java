/**
 * 
 */
package com.ruixue.serviceplatform.commons.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.api.validation.ResteasyConstraintViolation;
import org.jboss.resteasy.api.validation.ResteasyViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.ruixue.serviceplatform.commons.exception.InvalidField;

/**
 * the validation exception handler
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ValidationException> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationExceptionHandler.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse(ValidationException exception) {
		final ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
		errorInfo.setErrorCode(String.valueOf(Response.Status.BAD_REQUEST.getStatusCode()));
		errorInfo.setErrorMessage("invalid parameter(s)");
		if (exception instanceof ResteasyViolationException) {
			final ResteasyViolationException rv = (ResteasyViolationException) exception;
			LOGGER.warn("validation exception: " + rv.getParameterViolations());
			final List<ResteasyConstraintViolation> errors = rv.getParameterViolations();
			if (errors != null && !errors.isEmpty()) {
				final List<InvalidField> fields = new ArrayList<InvalidField>(errors.size());
				for (final ResteasyConstraintViolation rcv : errors) {
					final InvalidField f = new InvalidField();
					if (!StringUtils.isEmpty(rcv.getPath())) {
						// get the field name from the path
						final String[] a = rcv.getPath().split("\\.", -1);
						if (a.length > 0) {
							f.setField(a[a.length - 1]);
						}
					}
					f.setValue(rcv.getValue());
					f.setMessage(rcv.getMessage());
					if (!StringUtils.isEmpty(rcv.getMessage()) && rcv.getMessage().contains(":")) {
						// get the error code from error message
						final String[] a = rcv.getMessage().split("\\:", 2);
						if (a != null && a.length == 2) {
							f.setMessage(a[1].trim());
							f.setErrorCode(a[0].trim());
						}
					}
					fields.add(f);
				}
				errorInfo.setErrorFields(fields);
			}
		}
		return Response.status(errorInfo.getStatusCode()).entity(errorInfo).type(MediaType.APPLICATION_JSON_TYPE)
				.build();
	}

}
