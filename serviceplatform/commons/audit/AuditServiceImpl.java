/**
 * 
 */
package com.ruixue.serviceplatform.commons.audit;

import java.lang.reflect.Method;
import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;

import org.jboss.resteasy.core.interception.PostMatchContainerRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.ruixue.serviceplatform.commons.audit.concurrent.ConcurrentStatusService;
import com.ruixue.serviceplatform.commons.audit.timing.InvocationInfo;
import com.ruixue.serviceplatform.commons.audit.timing.InvocationTimingService;

/**
 * the implementation for AuditService
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public class AuditServiceImpl implements AuditService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuditServiceImpl.class);

	@Autowired
	private InvocationTimingService invocationTimingService;

	@Autowired
	private ConcurrentStatusService concurrentStatusService;

	private boolean auditInvocationTiming;

	private boolean auditConcurrentStatus;

	private List<String> ignoredPaths;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ruixue.serviceplatform.commons.audit.AuditService#beforeRequest(javax.ws.rs.container.ContainerRequestContext
	 * )
	 */
	@Override
	public void beforeRequest(ContainerRequestContext context) {
		// get path
		final String path = context.getUriInfo().getPath();
		if (this.isIgnoredPath(path)) {
			// ignored path
			return;
		}
		try {
			// invocation timing
			if (this.auditInvocationTiming) {
				// build the invocation info
				final InvocationInfo info = new InvocationInfo();
				info.setMethod(context.getMethod());
				info.setBeginTimestamp(System.currentTimeMillis());
				// set the info to context
				context.setProperty(InvocationTimingService.KEY_INVOCATION_INFO, info);
			}
		} catch (Throwable e) {
			// do not throw any exception
			LOGGER.error("audit error: " + e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.audit.AuditService#afterResourceMatched(javax.ws.rs.container.
	 * ContainerRequestContext)
	 */
	@Override
	public void afterResourceMatched(ContainerRequestContext context) {
		// get path
		final String path = context.getUriInfo().getPath();
		if (this.isIgnoredPath(path)) {
			// ignored path
			return;
		}
		PostMatchContainerRequestContext c = (PostMatchContainerRequestContext) context;
		try {
			// get the path from resource
			String pathOnClass = "";
			String pathOnMethod = "";
			final Method method = c.getResourceMethod().getMethod();
			if (method.getDeclaringClass().isAnnotationPresent(Path.class)) {
				pathOnClass = method.getDeclaringClass().getAnnotation(Path.class).value();
				if (!pathOnClass.startsWith("/")) {
					pathOnClass = "/" + pathOnClass;
				}
			}
			if (method.isAnnotationPresent(Path.class)) {
				pathOnMethod = method.getAnnotation(Path.class).value();
			}
			String resourcePath = pathOnClass + pathOnMethod;
			if (!pathOnClass.endsWith("/") && !pathOnMethod.startsWith("/")) {
				resourcePath = pathOnClass + "/" + pathOnMethod;
			}
			// invocation timing
			if (this.auditInvocationTiming) {
				// get the info from context
				final InvocationInfo info = (InvocationInfo) context
						.getProperty(InvocationTimingService.KEY_INVOCATION_INFO);
				if (info != null) {
					info.setPath(resourcePath);
				}
			}
			// concurrent status
			if (this.auditConcurrentStatus) {
				final String concurrentPath = "[" + context.getMethod() + "]" + resourcePath;
				// put the path to context
				context.setProperty(ConcurrentStatusService.KEY_CONCURRENT_STATUS, concurrentPath);
				// begin invocation
				this.concurrentStatusService.beginInvocation(concurrentPath);
			}
		} catch (Throwable e) {
			// do not throw any exception
			LOGGER.error("audit error: " + e.getMessage(), e);
		}
	}

	/**
	 * is the path ignored path
	 * 
	 * @param path
	 *            the path
	 * @return true: ignored
	 */
	private boolean isIgnoredPath(final String path) {
		if (this.ignoredPaths != null) {
			for (String p : this.ignoredPaths) {
				if (path.startsWith(p)) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.audit.AuditService#afterResourceInvocation(javax.ws.rs.container.
	 * ContainerRequestContext )
	 */
	@Override
	public void afterResourceInvocation(ContainerRequestContext context) {
		try {
			// invocation timing
			if (this.auditInvocationTiming) {
				// get the info from context
				final InvocationInfo info = (InvocationInfo) context
						.getProperty(InvocationTimingService.KEY_INVOCATION_INFO);
				if (info != null) {
					if (StringUtils.isEmpty(info.getPath())) {
						// unmatched path
						info.setPath(UNMATCHED_PATH);
					}
					info.setEndTimestamp(System.currentTimeMillis());
					info.setTime(info.getEndTimestamp() - info.getBeginTimestamp());
					this.invocationTimingService.invoked(info);
					// remove the info from context
					context.removeProperty(InvocationTimingService.KEY_INVOCATION_INFO);
				}
			}
			// concurrent status
			if (this.auditConcurrentStatus) {
				// get the path from context
				final String path = (String) context.getProperty(ConcurrentStatusService.KEY_CONCURRENT_STATUS);
				if (!StringUtils.isEmpty(path)) {
					// end invocation
					this.concurrentStatusService.endInvocation(path);
					// remove the path from context
					context.removeProperty(ConcurrentStatusService.KEY_CONCURRENT_STATUS);
				}
			}
		} catch (Throwable e) {
			// do not throw any exception
			LOGGER.error("audit error: " + e.getMessage(), e);
		}
	}

	/**
	 * @param invocationTimingService
	 *            the invocationTimingService to set
	 */
	public void setInvocationTimingService(InvocationTimingService invocationTimingService) {
		this.invocationTimingService = invocationTimingService;
	}

	/**
	 * @param concurrentStatusService
	 *            the concurrentStatusService to set
	 */
	public void setConcurrentStatusService(ConcurrentStatusService concurrentStatusService) {
		this.concurrentStatusService = concurrentStatusService;
	}

	/**
	 * @param auditInvocationTiming
	 *            the auditInvocationTiming to set
	 */
	public void setAuditInvocationTiming(boolean auditInvocationTiming) {
		this.auditInvocationTiming = auditInvocationTiming;
	}

	/**
	 * @param auditConcurrentStatus
	 *            the auditConcurrentStatus to set
	 */
	public void setAuditConcurrentStatus(boolean auditConcurrentStatus) {
		this.auditConcurrentStatus = auditConcurrentStatus;
	}

	/**
	 * @param ignoredPaths
	 *            the ignoredPaths to set
	 */
	public void setIgnoredPaths(List<String> ignoredPaths) {
		this.ignoredPaths = ignoredPaths;
	}

}
