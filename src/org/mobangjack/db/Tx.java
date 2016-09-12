package org.mobangjack.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * Tx in single thread mode.
 * @author 帮杰
 *
 */
public abstract class Tx {

	private static final Logger logger = Logger.getLogger(Tx.class);
	
	private Db db = null;
	private int level = Connection.TRANSACTION_READ_COMMITTED;
	private Bundle bundle = null;
	private Connection con = null;
	
	private static final ThreadLocal<Tx> threadLocalTx = new ThreadLocal<Tx>();
	
	protected static void setThreadLocalTx(Tx tx) {
		threadLocalTx.set(tx);
	}
	
	public static Tx getThreadLocalTx() {
		return threadLocalTx.get();
	}
	
	protected static void removeThreadLocalTx() {
		threadLocalTx.remove();
	}
	
	public Tx() {
		this(Db.useDefault(), Connection.TRANSACTION_READ_COMMITTED, null);
	}
	
	public Tx(Bundle bundle) {
		this(Db.useDefault(), Connection.TRANSACTION_READ_COMMITTED, bundle);
	}
	
	public Tx(Db db,int level,Bundle bundle) {
		this.db = db==null?Db.useDefault():db;
		this.level = level;
		this.bundle = bundle;
	}
	
	public abstract void run();
	
	public void start() {
		try {
			con = db.getConnection();
			con.setAutoCommit(false);
			con.setTransactionIsolation(level);
			setThreadLocalTx(this);
			run();
			con.commit();
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
			rollback();
		} finally {
			Db.close(con);
			removeThreadLocalTx();
		}
	}
	
	public void rollback(){
		if(con!=null)
			try {
				con.rollback();
				System.out.println("roll back");
			} catch (SQLException e) {
				logger.error(e);
				e.printStackTrace();
			}
	}
	
	public Db getDb() {
		return db;
	}

	public void setDb(Db db) {
		this.db = db;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Connection getConnection() {
		return con;
	}

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}
	
}
