/**
 * 
 */
package com.ruixue.serviceplatform.commons.cache;

import java.util.Collection;

/**
 * the remote cache manager
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public interface RemoteCacheManager {

	/**
	 * the cache control command all
	 */
	String CACHE_CONTROL_COMMAND_KEY_ALL = "ALL";

	/**
	 * the key for cache name in the cache control command template
	 */
	String CACHE_CONTROL_COMMAND_KEY_CACHE_NAME = "cacheName";

	/**
	 * the key for cache name template in the cache control command template
	 */
	String CACHE_CONTROL_COMMAND_KEY_CACHE_NAME_TEMPLATE = "${" + CACHE_CONTROL_COMMAND_KEY_CACHE_NAME + "}";

	/**
	 * the key for cache key in the cache control command template
	 */
	String CACHE_CONTROL_COMMAND_KEY_CACHE_KEY = "cacheKey";

	/**
	 * the key for cache key template in the cache control command template
	 */
	String CACHE_CONTROL_COMMAND_KEY_CACHE_KEY_TEMPLATE = "${" + CACHE_CONTROL_COMMAND_KEY_CACHE_KEY + "}";

	/**
	 * the template for get infos
	 */
	String CACHE_CONTROL_COMMAND_TEMPLATE_GET_INFOS = CACHE_CONTROL_COMMAND_KEY_CACHE_NAME_TEMPLATE;

	/**
	 * the template for eviction
	 */
	String CACHE_CONTROL_COMMAND_TEMPLATE_EVICTION = CACHE_CONTROL_COMMAND_KEY_CACHE_NAME_TEMPLATE + "/" + CACHE_CONTROL_COMMAND_KEY_CACHE_KEY_TEMPLATE;

	/**
	 * the cache path
	 */
	String CACHE_PATH = "cache";

	/**
	 * to get the cache infos by cache name
	 * 
	 * @param cacheServer
	 *            the cache server
	 * @param cacheName
	 *            the name of the cache, if it is null, will return all caches infos
	 * @return the cache infos
	 */
	Collection<CacheInfo> getCacheInfos(CacheServer cacheServer, String cacheName);

	/**
	 * to evict the cache
	 * 
	 * @param cacheServer
	 *            the cache server
	 * @param cacheName
	 *            the name of the cache
	 * @param cacheKeyId
	 *            the keyId of the cache
	 */
	void evict(CacheServer cacheServer, String cacheName, String cacheKeyId);

	/**
	 * to clear the cache
	 * 
	 * @param cacheServer
	 *            the cache server
	 * @param cacheName
	 *            the name of the cache
	 */
	void clear(CacheServer cacheServer, String cacheName);

}
