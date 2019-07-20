/**
 * 
 */
package com.ruixue.serviceplatform.commons.cache;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.concurrent.ConcurrentMapCache;

import com.ruixue.serviceplatform.commons.CommonsConstants;
import com.ruixue.serviceplatform.commons.datetime.DateTimeProvider;
import com.ruixue.serviceplatform.commons.utils.DatetimeUtils;

/**
 * the monitorable ConcurrentMapCache
 * 
 * @author shangchunming@rkylin.com.cn
 * 
 */
public class MonitorableCache extends ConcurrentMapCache {

	private static final Logger LOGGER = LoggerFactory.getLogger(MonitorableCache.class);

	private final ConcurrentMap<String, CacheInfo> cacheInfos = new ConcurrentHashMap<String, CacheInfo>();

	private final ConcurrentMap<String, String> cacheKeyIds = new ConcurrentHashMap<String, String>();

	private final Object LOCK = new Object();

	private final CacheEvictionListener cacheEvictionListener;

	private final DateTimeProvider dateTimeProvider;

	/**
	 * @param name
	 * @param allowNullValues
	 */
	public MonitorableCache(String name, boolean allowNullValues, CacheEvictionListener cacheEvictionListener, DateTimeProvider dateTimeProvider) {
		super(name, allowNullValues);
		this.cacheEvictionListener = cacheEvictionListener;
		this.dateTimeProvider = dateTimeProvider;
		LOGGER.debug("the cache create, name=" + name);
	}

	/**
	 * @param name
	 * @param store
	 * @param allowNullValues
	 */
	public MonitorableCache(String name, ConcurrentMap<Object, Object> store, boolean allowNullValues, CacheEvictionListener cacheEvictionListener,
			DateTimeProvider dateTimeProvider) {
		super(name, store, allowNullValues);
		this.cacheEvictionListener = cacheEvictionListener;
		this.dateTimeProvider = dateTimeProvider;
		LOGGER.debug("the cache create, name=" + name);
	}

	/**
	 * @param name
	 * @param cacheEvictionListener
	 */
	public MonitorableCache(String name, CacheEvictionListener cacheEvictionListener, DateTimeProvider dateTimeProvider) {
		super(name);
		this.cacheEvictionListener = cacheEvictionListener;
		this.dateTimeProvider = dateTimeProvider;
		LOGGER.debug("the cache create, name=" + name);
	}

	/**
	 * to get the cache key value by keyId
	 * 
	 * @param keyId
	 *            the id of the key
	 * @return the cache key value
	 */
	public String getCacheKeyValue(String keyId) {
		return this.cacheKeyIds.get(keyId);
	}

	/**
	 * to get all cache infos
	 * 
	 * @return the cache info
	 */
	public Collection<CacheInfo> getCacheInfos() {
		return this.cacheInfos.values();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.cache.concurrent.ConcurrentMapCache#get(java.lang .Object)
	 */
	@Override
	public ValueWrapper get(Object key) {
		synchronized (this.LOCK) {
			CacheInfo info = this.cacheInfos.get(key);
			if (info != null) {
				info.increaseHits();
			}
			return super.get(key);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.cache.concurrent.ConcurrentMapCache#get(java.lang .Object, java.lang.Class)
	 */
	@Override
	public <T> T get(Object key, Class<T> type) {
		synchronized (this.LOCK) {
			CacheInfo info = this.cacheInfos.get(key);
			if (info != null) {
				info.increaseHits();
			}
			return super.get(key, type);
		}
	}

	/**
	 * to build the key id
	 * 
	 * @return the key id
	 */
	private String buildKeyId() {
		return UUID.randomUUID().toString().replaceAll("\\-", "");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.cache.concurrent.ConcurrentMapCache#put(java.lang .Object, java.lang.Object)
	 */
	@Override
	public void put(Object key, Object value) {
		synchronized (this.LOCK) {
			CacheInfo info = new CacheInfo(this.getName(), this.buildKeyId(), key.toString(), DatetimeUtils.dateToString(this.dateTimeProvider.nowDatetime(),
					CommonsConstants.DATE_TIME_FORMAT));
			this.cacheInfos.put(info.getKeyValue(), info);
			this.cacheKeyIds.put(info.getKeyId(), info.getKeyValue());
			super.put(info.getKeyValue(), value);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.cache.concurrent.ConcurrentMapCache#putIfAbsent(java .lang.Object, java.lang.Object)
	 */
	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		synchronized (this.LOCK) {
			CacheInfo old = this.cacheInfos.get(key);
			if (old == null) {
				CacheInfo info = new CacheInfo(this.getName(), this.buildKeyId(), key.toString(), DatetimeUtils.dateToString(
						this.dateTimeProvider.nowDatetime(), CommonsConstants.DATE_TIME_FORMAT));
				this.cacheInfos.put(info.getKeyValue(), info);
				this.cacheKeyIds.put(info.getKeyId(), info.getKeyValue());
			}
			return super.putIfAbsent(key, value);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.cache.concurrent.ConcurrentMapCache#evict(java.lang .Object)
	 */
	@Override
	public void evict(Object key) {
		// evict self
		String keyId = this.evictSelf(key);
		// notify others services
		if (this.cacheEvictionListener != null) {
			try {
				this.cacheEvictionListener.evict(this.getName(), keyId);
			} catch (Throwable e) {
				LOGGER.error("invoke the cacheEvictionListener.evict error: " + e.getMessage(), e);
			}
		}
	}

	/**
	 * to evict self cache
	 * 
	 * @param key
	 *            the key of the cache
	 * @return the keyId
	 */
	public String evictSelf(Object key) {
		synchronized (this.LOCK) {
			this.cacheInfos.remove(key);
			String keyId = this.cacheKeyIds.remove(key);
			super.evict(key);
			LOGGER.info("the cache evicted, name={}, key={}", this.getName(), key);
			return keyId;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.cache.concurrent.ConcurrentMapCache#clear()
	 */
	@Override
	public void clear() {
		// clear self
		this.clearSelf();
		// notify others services
		if (this.cacheEvictionListener != null) {
			try {
				this.cacheEvictionListener.clear(this.getName());
			} catch (Throwable e) {
				LOGGER.error("invoke the cacheEvictionDispatcher.clear error: " + e.getMessage(), e);
			}
		}
	}

	/**
	 * to clear self cache
	 */
	public void clearSelf() {
		synchronized (this.LOCK) {
			this.cacheInfos.clear();
			this.cacheKeyIds.clear();
			super.clear();
			LOGGER.info("the cache cleared, name={}", this.getName());
		}
	}

}
