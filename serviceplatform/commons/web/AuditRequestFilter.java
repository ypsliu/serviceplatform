/**
 * 
 */
package com.ruixue.serviceplatform.commons.web;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.ruixue.serviceplatform.commons.audit.AuditService;

/**
 * the audit request filter
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
@Provider
@PreMatching
public class AuditRequestFilter implements ContainerRequestFilter {

	@Autowired
	private AuditService auditService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ws.rs.container.ContainerRequestFilter#filter(javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// audit
		this.auditService.beforeRequest(requestContext);
	}

	/**
	 * @param auditService
	 *            the auditService to set
	 */
	public void setAuditService(AuditService auditService) {
		this.auditService = auditService;
	}

}
