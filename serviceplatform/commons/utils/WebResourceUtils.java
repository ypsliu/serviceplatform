/**
 * 
 */
package com.ruixue.serviceplatform.commons.utils;

import javax.ws.rs.WebApplicationException;

import com.ruixue.serviceplatform.commons.web.ErrorInfo;

/**
 * the utils for web resource
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public final class WebResourceUtils {

	/**
	 * to read the error info from web exception
	 * 
	 * @param e
	 *            the exception
	 * @return the error info
	 */
	public static ErrorInfo readErrorInfo(final WebApplicationException e) {
		if (e != null && e.getResponse().hasEntity()) {
			return e.getResponse().readEntity(ErrorInfo.class);
		}
		return null;
	}

}
