/**
 * 
 */
package com.ruixue.serviceplatform.commons.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.ruixue.serviceplatform.commons.enums.CodedEnum;

/**
 * the query builder
 * 
 * @author shangchunming@rkylin.com.cn
 * 
 */
public final class QueryBuilder {

	private StringBuilder query;

	private final Map<String, Object> params = new HashMap<String, Object>();

	private int index = 1;

	private final Object LOCK = new Object();

	/**
	 * to create a empty QueryBuilder
	 */
	public QueryBuilder() {
		this.reset(null);
	}

	/**
	 * to create a QueryBuilder
	 * 
	 * @param baseQuery
	 *            the base Query
	 */
	public QueryBuilder(String baseQuery) {
		this.reset(baseQuery);
	}

	/**
	 * to append query
	 * 
	 * @param query
	 *            the query
	 * @return the QueryBuilder
	 */
	public QueryBuilder query(String query) {
		if (!StringUtils.isEmpty(query)) {
			synchronized (this.LOCK) {
				this.query.append(query);
			}
		}
		return this;
	}

	/**
	 * to append query
	 * 
	 * @param prefix
	 *            the prefix
	 * @param query
	 *            the query
	 * @return the QueryBuilder
	 */
	public QueryBuilder query(String prefix, String query) {
		if (!StringUtils.isEmpty(query)) {
			synchronized (this.LOCK) {
				if (!StringUtils.isEmpty(prefix)) {
					this.query.append(" ").append(prefix);
				}
				this.query.append(query);
			}
		}
		return this;
	}

	/**
	 * to append 'and'
	 * 
	 * @return the QueryBuilder
	 */
	public QueryBuilder and() {
		synchronized (this.LOCK) {
			this.query.append(" and ");
		}
		return this;
	}

	/**
	 * to append 'or'
	 * 
	 * @return the QueryBuilder
	 */
	public QueryBuilder or() {
		synchronized (this.LOCK) {
			this.query.append(" or ");
		}
		return this;
	}

	/**
	 * to append is null condition
	 * 
	 * @param fieldName
	 *            the name of the field
	 * @return the QueryBuilder
	 */
	public QueryBuilder isNull(String fieldName) {
		if (!StringUtils.isEmpty(fieldName)) {
			synchronized (this.LOCK) {
				this.query.append(" ").append(fieldName).append(" is null");
			}
		}
		return this;
	}

	/**
	 * to append is null condition
	 * 
	 * @param prefix
	 *            the prefix
	 * @param fieldName
	 *            the name of the field
	 * @return the QueryBuilder
	 */
	public QueryBuilder isNull(String prefix, String fieldName) {
		if (!StringUtils.isEmpty(fieldName)) {
			synchronized (this.LOCK) {
				if (!StringUtils.isEmpty(prefix)) {
					this.query.append(" ").append(prefix).append(" ");
				}
				this.query.append(" ").append(fieldName).append(" is null");
			}
		}
		return this;
	}

	/**
	 * to append is not null condition
	 * 
	 * @param fieldName
	 *            the name of the field
	 * @return the QueryBuilder
	 */
	public QueryBuilder isNotNull(String fieldName) {
		if (!StringUtils.isEmpty(fieldName)) {
			synchronized (this.LOCK) {
				this.query.append(" ").append(fieldName).append(" is not null");
			}
		}
		return this;
	}

	/**
	 * to append is not null condition
	 * 
	 * @param prefix
	 *            the prefix
	 * @param fieldName
	 *            the name of the field
	 * @return the QueryBuilder
	 */
	public QueryBuilder isNotNull(String prefix, String fieldName) {
		if (!StringUtils.isEmpty(fieldName)) {
			synchronized (this.LOCK) {
				if (!StringUtils.isEmpty(prefix)) {
					this.query.append(" ").append(prefix).append(" ");
				}
				this.query.append(" ").append(fieldName).append(" is not null");
			}
		}
		return this;
	}

	/**
	 * to append the condition
	 * 
	 * @param fieldName
	 *            the name of the field
	 * @param operator
	 *            the operator, "=" or "!=" or ">" or ">=" or "<" or "<="
	 * @param param
	 *            the parameter
	 * @return the QueryBuilder
	 */
	public QueryBuilder condition(String fieldName, String operator, CodedEnum<?> param) {
		if (param != null) {
			return this.condition(fieldName, operator, param.getCode());
		}
		return this;
	}

	/**
	 * to append the condition
	 * 
	 * @param fieldName
	 *            the name of the field
	 * @param operator
	 *            the operator, "=" or "!=" or ">" or ">=" or "<" or "<="
	 * @param param
	 *            the parameter
	 * @return the QueryBuilder
	 */
	public QueryBuilder condition(String fieldName, String operator, Object param) {
		if (!StringUtils.isEmpty(fieldName) && !StringUtils.isEmpty(param)) {
			if (param instanceof Object[] || param instanceof Collection<?>) {
				throw new IllegalArgumentException("the parameter must NOT be Object[] or Collection<?>");
			}
			synchronized (this.LOCK) {
				String paramName = "_param_" + this.index++;
				query.append(" ").append(fieldName).append(" ").append(operator).append(" ").append(":").append(paramName);
				params.put(paramName, param);
			}
		}
		return this;
	}

	/**
	 * to append the condition
	 * 
	 * @param prefix
	 *            the prefix
	 * @param fieldName
	 *            the name of the field
	 * @param operator
	 *            the operator, "=" or "!=" or ">" or ">=" or "<" or "<="
	 * @param param
	 *            the parameter
	 * @return the QueryBuilder
	 */
	public QueryBuilder condition(String prefix, String fieldName, String operator, CodedEnum<?> param) {
		if (param != null) {
			return this.condition(prefix, fieldName, operator, param.getCode());
		}
		return this;
	}

	/**
	 * to append the condition
	 * 
	 * @param prefix
	 *            the prefix
	 * @param fieldName
	 *            the name of the field
	 * @param operator
	 *            the operator, "=" or "!=" or ">" or ">=" or "<" or "<="
	 * @param param
	 *            the parameter
	 * @return the QueryBuilder
	 */
	public QueryBuilder condition(String prefix, String fieldName, String operator, Object param) {
		if (!StringUtils.isEmpty(fieldName) && !StringUtils.isEmpty(param)) {
			if (param instanceof Object[] || param instanceof Collection<?>) {
				throw new IllegalArgumentException("the parameter must NOT be Object[] or Collection<?>");
			}
			synchronized (this.LOCK) {
				String paramName = "_param_" + this.index++;
				if (!StringUtils.isEmpty(prefix)) {
					this.query.append(" ").append(prefix).append(" ");
				}
				query.append(" ").append(fieldName).append(" ").append(operator).append(" ").append(":").append(paramName);
				params.put(paramName, param);
			}
		}
		return this;
	}

	/**
	 * to append in condition
	 * 
	 * @param fieldName
	 *            the name of the field
	 * @param params
	 *            the parameters
	 * @return the QueryBuilder
	 */
	public QueryBuilder in(String fieldName, Object params) {
		if (!StringUtils.isEmpty(fieldName) && !StringUtils.isEmpty(params)) {
			if (!(params instanceof Object[]) && !(params instanceof Collection<?>)) {
				throw new IllegalArgumentException("the parameter must be Object[] or Collection<?>");
			}
			if (params instanceof Object[] && ((Object[]) params).length == 0) {
				return this;
			}
			if (params instanceof Collection<?> && ((Collection<?>) params).isEmpty()) {
				return this;
			}
			synchronized (this.LOCK) {
				String paramName = "_param_" + this.index++;
				query.append(" ").append(fieldName).append(" in (:").append(paramName).append(")");
				this.params.put(paramName, params);
			}
		}
		return this;
	}

	/**
	 * to append not in condition
	 * 
	 * @param fieldName
	 *            the name of the field
	 * @param params
	 *            the parameters
	 * @return the QueryBuilder
	 */
	public QueryBuilder notIn(String fieldName, Object params) {
		if (!StringUtils.isEmpty(fieldName) && !StringUtils.isEmpty(params)) {
			if (!(params instanceof Object[]) && !(params instanceof Collection<?>)) {
				throw new IllegalArgumentException("the parameter must be Object[] or Collection<?>");
			}
			if (params instanceof Object[] && ((Object[]) params).length == 0) {
				return this;
			}
			if (params instanceof Collection<?> && ((Collection<?>) params).isEmpty()) {
				return this;
			}
			synchronized (this.LOCK) {
				String paramName = "_param_" + this.index++;
				query.append(" ").append(fieldName).append(" not in (:").append(paramName).append(")");
				this.params.put(paramName, params);
			}
		}
		return this;
	}

	/**
	 * to append in condition
	 * 
	 * @param prefix
	 *            the prefix
	 * @param fieldName
	 *            the name of the field
	 * @param params
	 *            the parameters
	 * @return the QueryBuilder
	 */
	public QueryBuilder in(String prefix, String fieldName, Object params) {
		if (!StringUtils.isEmpty(fieldName) && !StringUtils.isEmpty(params)) {
			if (!(params instanceof Object[]) && !(params instanceof Collection<?>)) {
				throw new IllegalArgumentException("the parameter must be Object[] or Collection<?>");
			}
			if (params instanceof Object[] && ((Object[]) params).length == 0) {
				return this;
			}
			if (params instanceof Collection<?> && ((Collection<?>) params).isEmpty()) {
				return this;
			}
			synchronized (this.LOCK) {
				String paramName = "_param_" + this.index++;
				if (!StringUtils.isEmpty(prefix)) {
					this.query.append(" ").append(prefix).append(" ");
				}
				query.append(" ").append(fieldName).append(" in (:").append(paramName).append(")");
				this.params.put(paramName, params);
			}
		}
		return this;
	}

	/**
	 * to append not in condition
	 * 
	 * @param prefix
	 *            the prefix
	 * @param fieldName
	 *            the name of the field
	 * @param params
	 *            the parameters
	 * @return the QueryBuilder
	 */
	public QueryBuilder notIn(String prefix, String fieldName, Object params) {
		if (!StringUtils.isEmpty(fieldName) && !StringUtils.isEmpty(params)) {
			if (!(params instanceof Object[]) && !(params instanceof Collection<?>)) {
				throw new IllegalArgumentException("the parameter must be Object[] or Collection<?>");
			}
			if (params instanceof Object[] && ((Object[]) params).length == 0) {
				return this;
			}
			if (params instanceof Collection<?> && ((Collection<?>) params).isEmpty()) {
				return this;
			}
			synchronized (this.LOCK) {
				String paramName = "_param_" + this.index++;
				if (!StringUtils.isEmpty(prefix)) {
					this.query.append(" ").append(prefix).append(" ");
				}
				query.append(" ").append(fieldName).append(" not in (:").append(paramName).append(")");
				this.params.put(paramName, params);
			}
		}
		return this;
	}

	/**
	 * to add the parameter
	 * 
	 * @param paramName
	 *            the name of the parameter
	 * @param param
	 *            the parameter value
	 * @return the QueryBuilder
	 */
	public QueryBuilder addParameter(String paramName, Object param) {
		if (!StringUtils.isEmpty(paramName) && !StringUtils.isEmpty(param)) {
			synchronized (this.LOCK) {
				this.params.put(paramName, param);
			}
		}
		return this;
	}

	/**
	 * to reset the builder
	 * 
	 * @return the QueryBuilder
	 */
	public QueryBuilder reset() {
		return this.reset(null);
	}

	/**
	 * to reset the builder by new query
	 * 
	 * @param query
	 *            the new query
	 * @return the QueryBuilder
	 */
	public QueryBuilder reset(String query) {
		synchronized (this.LOCK) {
			this.query = new StringBuilder();
			this.params.clear();
			if (!StringUtils.isEmpty(query)) {
				this.query.append(query);
			}
			this.index = 1;
		}
		return this;
	}

	/**
	 * to get the query
	 * 
	 * @return the query
	 */
	public String getQuery() {
		return this.query.toString();
	}

	/**
	 * to get the parameters
	 * 
	 * @return the parameters
	 */
	public Map<String, Object> getParameters() {
		return this.params;
	}

}
