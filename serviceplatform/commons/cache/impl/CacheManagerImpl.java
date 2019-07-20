/**
 * 
 */
package com.ruixue.serviceplatform.commons.cache.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.util.StringUtils;

import com.ruixue.serviceplatform.commons.cache.CacheEvictionListener;
import com.ruixue.serviceplatform.commons.cache.CacheInfo;
import com.ruixue.serviceplatform.commons.cache.CacheManager;
import com.ruixue.serviceplatform.commons.cache.CacheServer;
import com.ruixue.serviceplatform.commons.cache.MonitorableCache;
import com.ruixue.serviceplatform.commons.cache.RemoteCacheManager;
import com.ruixue.serviceplatform.commons.datetime.DateTimeProvider;

/**
 * the implementation for CacheManager
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public class CacheManagerImpl extends ConcurrentMapCacheManager implements CacheManager, CacheEvictionListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(CacheManagerImpl.class);

	private String localServerName;

	private Map<String, CacheServer> servers;

	private List<CacheServer> remoteServers;

	@Autowired
	private RemoteCacheManager remoteCacheManager;

	@Autowired
	private DateTimeProvider dateTimeProvider;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.cache.CacheEvictionListener#evict(java.lang.String, java.lang.String)
	 */
	@Override
	public void evict(String cacheName, String cacheKeyId) {
		// local cache evicted, to evict remote cache servers
		Collection<CacheServer> remotes = this.getRemoteCacheServers();
		if (remotes != null) {
			for (CacheServer server : remotes) {
				this.evict(server.getName(), cacheName, cacheKeyId);
			}
			return;
		}
		LOGGER.info("no remote cache servers found, evict remote ignored");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.cache.CacheEvictionListener#clear(java.lang.String)
	 */
	@Override
	public void clear(String cacheName) {
		// local cache cleared, to clear remote cache servers
		Collection<CacheServer> remotes = this.getRemoteCacheServers();
		if (remotes != null) {
			for (CacheServer server : remotes) {
				this.clear(server.getName(), cacheName);
			}
			return;
		}
		LOGGER.info("no remote cache servers found, clear remote ignored");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.cache.CacheManager#getLocalServerName()
	 */
	@Override
	public String getLocalServerName() {
		return this.localServerName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.cache.CacheManager#getCacheServer(java.lang.String)
	 */
	@Override
	public CacheServer getCacheServer(String serverName) {
		return this.servers.get(serverName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.cache.CacheManager#getCacheServers()
	 */
	@Override
	public Collection<CacheServer> getCacheServers() {
		return this.servers.values();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.cache.CacheManager#getRemoteCacheServers()
	 */
	@Override
	public Collection<CacheServer> getRemoteCacheServers() {
		return this.remoteServers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.cache.CacheManager#getLocalCacheInfos(java.lang.String)
	 */
	@Override
	public Collection<CacheInfo> getLocalCacheInfos(String cacheName) {
		List<String> cacheNames = new ArrayList<String>();
		if (!StringUtils.isEmpty(cacheName)) {
			cacheNames.add(cacheName);
		} else {
			cacheNames.addAll(this.getCacheNames());
		}
		if (cacheNames != null && !cacheNames.isEmpty()) {
			Collection<CacheInfo> infos = new ArrayList<CacheInfo>();
			for (String name : cacheNames) {
				MonitorableCache cache = (MonitorableCache) this.getCache(name);
				if (cache != null) {
					infos.addAll(cache.getCacheInfos());
				} else {
					LOGGER.warn("no cache found by cacheName={}", name);
				}
			}
			return infos;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.cache.CacheManager#getCacheInfos(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<CacheInfo> getCacheInfos(String serverName, String cacheName) {
		CacheServer cacheServer = this.servers.get(serverName);
		if (cacheServer != null) {
			if (cacheServer.isLocal()) {
				// local cache server
				return this.getLocalCacheInfos(cacheName);
			}
			// to get cache infos from remote cache server
			return this.remoteCacheManager.getCacheInfos(cacheServer, cacheName);
		}
		LOGGER.warn("no cache server found by serverName={}", serverName);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.cache.CacheManager#evictLocal(java.lang.String, java.lang.String)
	 */
	@Override
	public void evictLocal(String cacheName, String cacheKeyId) {
		Cache cache = this.getCache(cacheName);
		if (cache != null) {
			MonitorableCache target = (MonitorableCache) cache;
			String keyValue = target.getCacheKeyValue(cacheKeyId);
			if (keyValue != null) {
				target.evictSelf(keyValue);
				return;
			}
		}
		LOGGER.warn("no cache found by cacheName={}", cacheName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.cache.CacheManager#evict(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void evict(String serverName, String cacheName, String cacheKeyId) {
		CacheServer cacheServer = this.servers.get(serverName);
		if (cacheServer != null) {
			if (cacheServer.isLocal()) {
				// local cache server
				this.evictLocal(cacheName, cacheKeyId);
			} else {
				// to evict from remote cache server
				this.remoteCacheManager.evict(cacheServer, cacheName, cacheKeyId);
			}
			return;
		}
		LOGGER.warn("no cache server found by serverName={}", serverName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.cache.CacheManager#clearLocal(java.lang.String)
	 */
	@Override
	public void clearLocal(String cacheName) {
		Cache cache = this.getCache(cacheName);
		if (cache != null) {
			MonitorableCache target = (MonitorableCache) cache;
			target.clearSelf();
			return;
		}
		LOGGER.warn("no cache found by cacheName={}", cacheName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.cache.CacheManager#clear(java.lang.String, java.lang.String)
	 */
	@Override
	public void clear(String serverName, String cacheName) {
		CacheServer cacheServer = this.servers.get(serverName);
		if (cacheServer != null) {
			if (cacheServer.isLocal()) {
				// local cache server
				this.clearLocal(cacheName);
			} else {
				// to clear from remote cache server
				this.remoteCacheManager.clear(cacheServer, cacheName);
			}
			return;
		}
		LOGGER.warn("no cache server found by serverName={}", serverName);
	}

	/**
	 * to set the local server name
	 * 
	 * @param localServerName
	 */
	public void setLocalServerName(String localServerName) {
		this.localServerName = localServerName;
	}

	/**
	 * to set the cache servers
	 * 
	 * @param servers
	 *            the map of the cache servers
	 */
	public void setCacheServers(Map<String, String> servers) {
		this.servers = new HashMap<String, CacheServer>();
		this.remoteServers = new ArrayList<CacheServer>();
		if (servers != null && !servers.isEmpty()) {
			for (String serverName : servers.keySet()) {
				CacheServer cacheServer = new CacheServer();
				cacheServer.setName(serverName);
				cacheServer.setMonitorUrl(servers.get(serverName));
				cacheServer.setLocal(serverName.equals(this.localServerName));
				// get cache infos URL
				cacheServer.setCacheInfosUrl(cacheServer.getMonitorUrl() + RemoteCacheManager.CACHE_PATH + "/"
						+ RemoteCacheManager.CACHE_CONTROL_COMMAND_TEMPLATE_GET_INFOS);
				// cache eviction URL
				cacheServer.setEvictionUrl(cacheServer.getMonitorUrl() + RemoteCacheManager.CACHE_PATH + "/"
						+ RemoteCacheManager.CACHE_CONTROL_COMMAND_TEMPLATE_EVICTION);
				if (!cacheServer.isLocal()) {
					// it is the remote server
					this.remoteServers.add(cacheServer);
				}
				this.servers.put(serverName, cacheServer);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.cache.concurrent.ConcurrentMapCacheManager#createConcurrentMapCache(java.lang.String)
	 */
	@Override
	public Cache createConcurrentMapCache(String name) {
		// create the cache
		return new MonitorableCache(name, isAllowNullValues(), this, this.dateTimeProvider);
	}

	/**
	 * @param remoteCacheManager
	 *            the remoteCacheManager to set
	 */
	public void setRemoteCacheManager(RemoteCacheManager remoteCacheManager) {
		this.remoteCacheManager = remoteCacheManager;
	}

	/**
	 * @param dateTimeProvider
	 *            the dateTimeProvider to set
	 */
	public void setDateTimeProvider(DateTimeProvider dateTimeProvider) {
		this.dateTimeProvider = dateTimeProvider;
	}

}
