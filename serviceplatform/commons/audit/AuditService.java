/**
 * 
 */
package com.ruixue.serviceplatform.commons.audit;

import javax.ws.rs.container.ContainerRequestContext;

/**
 * the audit service
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public interface AuditService {

	/**
	 * unmatched path
	 */
	String UNMATCHED_PATH = "UNMATCHED_PATH";

	/**
	 * to audit before web request
	 * 
	 * @param context
	 *            the request context
	 */
	void beforeRequest(ContainerRequestContext context);

	/**
	 * to audit after resource matched
	 * 
	 * @param context
	 *            the request context
	 */
	void afterResourceMatched(ContainerRequestContext context);

	/**
	 * to audit after web response
	 * 
	 * @param context
	 *            the request context
	 */
	void afterResourceInvocation(ContainerRequestContext context);

}
