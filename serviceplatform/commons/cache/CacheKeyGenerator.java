/**
 * 
 */
package com.ruixue.serviceplatform.commons.cache;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.cache.interceptor.KeyGenerator;

/**
 * the KeyGenerator for spring cache
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public class CacheKeyGenerator implements KeyGenerator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.cache.interceptor.KeyGenerator#generate(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object generate(Object target, Method method, Object... params) {
		// the cache key is: className#methodName(parameters)
		return target.getClass().getName() + "#" + method.getName() + "(" + Arrays.toString(params) + ")";
	}

}
