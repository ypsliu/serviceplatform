/**
 * 
 */
package com.ruixue.serviceplatform.commons.monitor.web;

import java.util.Collection;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import com.ruixue.serviceplatform.commons.audit.concurrent.ConcurrentStatus;
import com.ruixue.serviceplatform.commons.audit.concurrent.ConcurrentStatusService;
import com.ruixue.serviceplatform.commons.audit.timing.InvocationSummary;
import com.ruixue.serviceplatform.commons.audit.timing.InvocationTimingService;
import com.ruixue.serviceplatform.commons.cache.CacheInfo;
import com.ruixue.serviceplatform.commons.cache.CacheManager;
import com.ruixue.serviceplatform.commons.cache.RemoteCacheManager;

/**
 * the implementation for MonitorResource
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
@Controller
public class MonitorResourceImpl implements MonitorResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(MonitorResourceImpl.class);

	@Autowired
	private InvocationTimingService invocationTimingService;

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private ConcurrentStatusService concurrentStatusService;

	private String monitorPassword;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.monitor.web.MonitorResource#getConcurrentStatus(java.lang.String)
	 */
	@Override
	public Collection<ConcurrentStatus> getConcurrentStatus(final String password) {
		this.checkPassword(password);
		return this.concurrentStatusService.getStatus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.monitor.web.MonitorResource#resetConcurrentStatus(java.lang.String)
	 */
	@Override
	public void resetConcurrentStatus(final String password) {
		this.checkPassword(password);
		this.concurrentStatusService.reset();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.monitor.web.MonitorResource#getInvocationSummaries(java.lang.String)
	 */
	@Override
	public Collection<InvocationSummary> getInvocationSummaries(final String password) {
		this.checkPassword(password);
		return this.invocationTimingService.getSummaries();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.monitor.web.MonitorResource#getCacheInfos(java.lang.String)
	 */
	@Override
	public Collection<CacheInfo> getCacheInfos(final String password) {
		this.checkPassword(password);
		return this.cacheManager.getLocalCacheInfos(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.monitor.web.MonitorResource#getCacheInfos(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Collection<CacheInfo> getCacheInfos(final String name, final String password) {
		this.checkPassword(password);
		return this.cacheManager.getLocalCacheInfos(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.monitor.web.MonitorResource#evict(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public CacheEvictionResult evict(final String name, final String key, final String password) {
		this.checkPassword(password);
		final CacheEvictionResult result = new CacheEvictionResult();
		result.setCacheName(name);
		result.setKey(key);
		if (RemoteCacheManager.CACHE_CONTROL_COMMAND_KEY_ALL.equalsIgnoreCase(result.getKey())) {
			this.cacheManager.clearLocal(name);
		} else {
			this.cacheManager.evictLocal(name, result.getKey());
		}
		result.setStatusCode(Response.Status.OK.getStatusCode());
		return result;
	}

	/**
	 * to check the password
	 * 
	 * @param password
	 *            the password
	 */
	private void checkPassword(final String password) {
		if (!StringUtils.isEmpty(this.monitorPassword)) {
			// check the monitor password
			if (!this.monitorPassword.equals(password)) {
				// invalid password
				LOGGER.warn("invalid monitor password");
				throw new NotAuthorizedException("invalid monitor password");
			}
		}
	}

	/**
	 * @param monitorPassword
	 *            the monitorPassword to set
	 */
	public void setMonitorPassword(String monitorPassword) {
		if (!StringUtils.isEmpty(monitorPassword)) {
			this.monitorPassword = DigestUtils.md5Hex(monitorPassword);
		}
	}

	/**
	 * @param cacheManager
	 *            the cacheManager to set
	 */
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
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

}
