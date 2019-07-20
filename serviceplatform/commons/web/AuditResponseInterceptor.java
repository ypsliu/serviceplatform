/**
 * 
 */
package com.ruixue.serviceplatform.commons.web;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.ruixue.serviceplatform.commons.audit.AuditService;

/**
 * the audit response interceptor
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
@Provider
@Priority(Priorities.USER)
public class AuditResponseInterceptor implements ContainerResponseFilter {

	@Autowired
	private AuditService auditService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ws.rs.container.ContainerResponseFilter#filter(javax.ws.rs.container.ContainerRequestContext,
	 * javax.ws.rs.container.ContainerResponseContext)
	 */
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		this.auditService.afterResourceInvocation(requestContext);
	}

	/**
	 * @param auditService
	 *            the auditService to set
	 */
	public void setAuditService(AuditService auditService) {
		this.auditService = auditService;
	}

}
