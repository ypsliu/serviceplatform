/**
 * 
 */
package com.ruixue.serviceplatform.commons.testing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * the testing database helper
 * 
 * @author shangchunming@rongcapital.cn
 *
 */
public class TestingDatabaseHelper {

	private final Connection conn;

	/**
	 * to create the TestingDatabaseHelper
	 * 
	 * @param driverName
	 *            the driver class name
	 * @param url
	 *            the connection url
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @throws Exception
	 *             on error
	 */
	public TestingDatabaseHelper(final String driverName, final String url, final String userName, final String password)
			throws Exception {
		Class.forName(driverName);
		this.conn = DriverManager.getConnection(url, userName, password);
	}

	/**
	 * to execute the update SQL
	 * 
	 * @param sql
	 *            the SQL
	 * @return the row count
	 * @throws Exception
	 *             on error
	 */
	public int executeUpdateSql(final String sql) throws Exception {
		return this.executeUpdateSql(sql, null);
	}

	/**
	 * to execute the update SQL
	 * 
	 * @param sql
	 *            the SQL
	 * @param params
	 *            the parameters
	 * @return the row count
	 * @throws Exception
	 *             on error
	 */
	public int executeUpdateSql(final String sql, final Object[] params) throws Exception {
		PreparedStatement ps = null;
		try {
			ps = this.conn.prepareStatement(sql);
			if (params != null) {
				int index = 1;
				for (final Object param : params) {
					ps.setObject(index++, param);
				}
			}
			return ps.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e2) {
					//
				}
			}
		}
	}

	/**
	 * to execute the query SQL
	 * 
	 * @param sql
	 *            the query SQL
	 * @param params
	 *            the parameters
	 * @return the result
	 * @throws Exception
	 *             on error
	 */
	public List<Map<String, Object>> executeQuerySql(final String sql, final Object[] params) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// prepare statement
			ps = this.conn.prepareStatement(sql);
			// set the parameters
			if (params != null) {
				int index = 1;
				for (final Object param : params) {
					ps.setObject(index++, param);
				}
			}
			// query
			rs = ps.executeQuery();
			final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			// columns
			final Set<String> columns = new HashSet<String>(rs.getMetaData().getColumnCount());
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				columns.add(rs.getMetaData().getColumnLabel(i));
			}
			// build result
			while (rs.next()) {
				final Map<String, Object> row = new HashMap<String, Object>(columns.size());
				for (final String column : columns) {
					row.put(column, rs.getObject(column));
				}
				list.add(row);
			}
			return list;
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
					//
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e2) {
					//
				}
			}
		}
	}

	/**
	 * to close the helper
	 */
	public void close() {
		if (this.conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				//
			}
		}
	}

}
