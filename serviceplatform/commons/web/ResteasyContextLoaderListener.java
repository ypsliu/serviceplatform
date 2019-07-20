/**
 * 
 */
package com.ruixue.serviceplatform.commons.web;

import javax.servlet.ServletContext;

import org.jboss.resteasy.plugins.spring.SpringContextLoaderSupport;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

/**
 * the ContextLoaderListener for resteasy
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public class ResteasyContextLoaderListener extends ContextLoaderListener {

	private SpringContextLoaderSupport springContextLoaderSupport = new SpringContextLoaderSupport();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.context.ContextLoader#customizeContext(javax.servlet.ServletContext,
	 * org.springframework.web.context.ConfigurableWebApplicationContext)
	 */
	@Override
	public void customizeContext(ServletContext sc, ConfigurableWebApplicationContext wac) {
		super.customizeContext(sc, wac);
		this.springContextLoaderSupport.customizeContext(sc, wac);
	}

}
