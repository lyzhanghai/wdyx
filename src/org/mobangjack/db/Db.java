package org.mobangjack.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mobangjack.db.jodb.dialect.DefaultDialect;
import org.mobangjack.db.jodb.dialect.Dialect;
import org.mobangjack.db.jodb.dialect.MysqlDialect;
import org.mobangjack.db.jodb.dialect.OracleDialect;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Database resources.
 * @author 帮杰
 *
 */
public class Db {

	private final String name;
	private ComboPooledDataSource datasource;
	private Dialect dialect = new DefaultDialect();
	
	private static final Map<String,ComboPooledDataSource> dataSources = new HashMap<String,ComboPooledDataSource>();
	
	public static final String DEFAULT_DB_NAME = "default_db";
	
	private static final Logger logger = Logger.getLogger(Db.class);
	
	private Db() {
		this.name = DEFAULT_DB_NAME;
		init(name);
	}
	
	private Db(String name) {
		this.name = name;
		init(name);
	}
	
	private void init(String name) {
		datasource = dataSources.get(name);
		if(datasource==null) {
			datasource = new ComboPooledDataSource();
			dataSources.put(DEFAULT_DB_NAME, datasource);
		}
		String driver = datasource.getDriverClass().toLowerCase();
		if (driver.contains("mysql")) {
			dialect = new MysqlDialect();
		}else if(driver.contains("oracle")) {
			dialect = new OracleDialect();
		}
	}
	
	public final String getName() {
		return name;
	}

	public ComboPooledDataSource getDataSource() {
		return datasource;
	}
	
	public Dialect getDialect() {
		return dialect;
	}
	
	public Connection getConnection() {
		Connection con = null;
		try {
			con = datasource.getConnection();
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return con;
	}
	
	public static Db use(String name) {
		return new Db(name);
	}
	
	public static Db useDefault() {
		return new Db();
	}

	public static void close(Connection con) {
		try {
			if (con!=null) {
				con.close();
				con = null;
			}
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
}
