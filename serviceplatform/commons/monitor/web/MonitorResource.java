/**
 * 
 */
package com.ruixue.serviceplatform.commons.monitor.web;

import java.util.Collection;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ruixue.serviceplatform.commons.CommonsConstants;
import com.ruixue.serviceplatform.commons.audit.concurrent.ConcurrentStatus;
import com.ruixue.serviceplatform.commons.audit.timing.InvocationSummary;
import com.ruixue.serviceplatform.commons.cache.CacheInfo;
import com.ruixue.serviceplatform.commons.cache.RemoteCacheManager;

/**
 * the monitor resource
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
@Path(CommonsConstants.MONITOR_PATH)
@Produces({ MediaType.APPLICATION_JSON })
public interface MonitorResource {

	/**
	 * to get the invocation concurrent status
	 * 
	 * @param password
	 *            the monitor password
	 * @return the concurrent status
	 */
	@Path("invocation/concurrent")
	@GET
	Collection<ConcurrentStatus> getConcurrentStatus(
			@HeaderParam(CommonsConstants.HEADER_KEY_MONITOR_PASSWORD) String password);

	/**
	 * to reset the invocation concurrent status
	 * 
	 * @param password
	 *            the monitor password
	 */
	@Path("invocation/concurrent")
	@DELETE
	void resetConcurrentStatus(@HeaderParam(CommonsConstants.HEADER_KEY_MONITOR_PASSWORD) String password);

	/**
	 * to get the invocation timing summaries
	 * 
	 * @param password
	 *            the monitor password
	 * @return the summaries
	 * @throws NotAuthorizedException
	 *             invalid monitor password
	 */
	@Path("invocation/timing")
	@GET
	Collection<InvocationSummary> getInvocationSummaries(
			@HeaderParam(CommonsConstants.HEADER_KEY_MONITOR_PASSWORD) String password);

	/**
	 * to get all cache infos
	 * 
	 * @param password
	 *            the monitor password
	 * @return the cache infos
	 * @throws NotAuthorizedException
	 *             invalid monitor password
	 */
	@Path(RemoteCacheManager.CACHE_PATH)
	@GET
	Collection<CacheInfo> getCacheInfos(@HeaderParam(CommonsConstants.HEADER_KEY_MONITOR_PASSWORD) String password);

	/**
	 * to get the cache infos
	 * 
	 * @param name
	 *            the cache name
	 * @param password
	 *            the monitor password
	 * @return the cache infos
	 * @throws NotAuthorizedException
	 *             invalid monitor password
	 */
	@Path(RemoteCacheManager.CACHE_PATH + "/{" + RemoteCacheManager.CACHE_CONTROL_COMMAND_KEY_CACHE_NAME + "}")
	@GET
	Collection<CacheInfo> getCacheInfos(
			@PathParam(RemoteCacheManager.CACHE_CONTROL_COMMAND_KEY_CACHE_NAME) String name,
			@HeaderParam(CommonsConstants.HEADER_KEY_MONITOR_PASSWORD) String password);

	/**
	 * to evict the cache
	 * 
	 * @param name
	 *            the cache name
	 * @param key
	 *            the cache key
	 * @param password
	 *            the monitor password
	 * @return the result
	 * @throws NotAuthorizedException
	 *             invalid monitor password
	 */
	@Path(RemoteCacheManager.CACHE_PATH + "/{" + RemoteCacheManager.CACHE_CONTROL_COMMAND_KEY_CACHE_NAME + "}/{"
			+ RemoteCacheManager.CACHE_CONTROL_COMMAND_KEY_CACHE_KEY + "}")
	@DELETE
	CacheEvictionResult evict(@PathParam(RemoteCacheManager.CACHE_CONTROL_COMMAND_KEY_CACHE_NAME) String name,
			@PathParam(RemoteCacheManager.CACHE_CONTROL_COMMAND_KEY_CACHE_KEY) String key,
			@HeaderParam(CommonsConstants.HEADER_KEY_MONITOR_PASSWORD) String password);

}
