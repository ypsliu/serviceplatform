/**
 * 
 */
package com.ruixue.serviceplatform.commons.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.ruixue.serviceplatform.commons.CommonsConstants;

/**
 * the default jackson provider
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
@Provider
public class DefaultJacksonJaxbJsonProvider extends JacksonJaxbJsonProvider {

	public DefaultJacksonJaxbJsonProvider() {
		final ObjectMapper mapper = new ObjectMapper();
		// ignore null fields
		mapper.setSerializationInclusion(Inclusion.NON_NULL);
		// ignore unknown fields
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// date time format
		final DateFormat dateFormat = new SimpleDateFormat(CommonsConstants.DATE_TIME_FORMAT);
		mapper.setDateFormat(dateFormat);
		super.setMapper(mapper);
	}

}
