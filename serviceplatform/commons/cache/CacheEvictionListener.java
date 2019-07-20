/**
 * 
 */
package com.ruixue.serviceplatform.commons.cache;

/**
 * the cache eviction listener
 * 
 * @author shangchunming@rkylin.com.cn
 * 
 */
public interface CacheEvictionListener {

	/**
	 * will be invoked when the cache is evicting
	 * 
	 * @param cacheName
	 *            the name of the cache
	 * @param cacheKeyId
	 *            the keyId of the cache
	 */
	void evict(String cacheName, String cacheKeyId);

	/**
	 * will be invoked when the cache is clearing
	 * 
	 * @param cacheName
	 *            the name of the cache
	 */
	void clear(String cacheName);

}
