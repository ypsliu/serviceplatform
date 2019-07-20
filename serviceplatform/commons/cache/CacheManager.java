/**
 * 
 */
package com.ruixue.serviceplatform.commons.cache;

import java.util.Collection;

/**
 * the cache manager
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public interface CacheManager {

	/**
	 * to get the local cache server name
	 * 
	 * @return the local cache server name
	 */
	String getLocalServerName();

	/**
	 * to get the cache server by name
	 * 
	 * @param serverName
	 *            the name of the cache server
	 * @return the cache server
	 */
	CacheServer getCacheServer(String serverName);

	/**
	 * to get all cache servers
	 * 
	 * @return the all cache servers
	 */
	Collection<CacheServer> getCacheServers();

	/**
	 * to get the remote cache servers
	 * 
	 * @return the remote cache servers
	 */
	Collection<CacheServer> getRemoteCacheServers();

	/**
	 * to get the local cache infos by cache name
	 * 
	 * @param cacheName
	 *            the name of the cache, if it is null, will return all caches infos
	 * @return the cache infos
	 */
	Collection<CacheInfo> getLocalCacheInfos(String cacheName);

	/**
	 * to get the cache infos by server name and cache name
	 * 
	 * @param serverName
	 *            the name of the server
	 * @param cacheName
	 *            the name of the cache
	 * @return the cache infos
	 */
	Collection<CacheInfo> getCacheInfos(String serverName, String cacheName);

	/**
	 * to evict the local cache
	 * 
	 * @param cacheName
	 *            the name of the cache
	 * @param cacheKeyId
	 *            the keyId of the cache
	 */
	void evictLocal(String cacheName, String cacheKeyId);

	/**
	 * to evict the cache
	 * 
	 * @param serverName
	 *            the name of the cache server
	 * @param cacheName
	 *            the name of the cache
	 * @param cacheKeyId
	 *            the keyId of the cache
	 */
	void evict(String serverName, String cacheName, String cacheKeyId);

	/**
	 * to clear the local cache
	 * 
	 * @param cacheName
	 *            the name of the cache
	 */
	void clearLocal(String cacheName);

	/**
	 * to clear the cache
	 * 
	 * @param serverName
	 *            the name of the cache server
	 * @param cacheName
	 *            the name of the cache
	 */
	void clear(String serverName, String cacheName);

}
