package com.felipe221.rftb.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.felipe221.rftb.RFTB;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {
	private static final Logger logger = Bukkit.getLogger();
	protected boolean connected = false;
	private String driver;
	private final ExecutorService service = Executors.newCachedThreadPool();
	private String connectionString;
	private RFTB plugin;
	private Connection c = null;
	private HikariDataSource dataSource;
	public static int DB = 0;


	public DatabaseManager(String hostname, int port, String database, String username, String password, RFTB plugin) { 
		driver="com.mysql.jdbc.Driver";
		connectionString="jdbc:mysql://" + hostname + ":" + port + "/" + database+ "?user=" + username + "&password=" + password+"&autoReconnect=true";
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(connectionString);
		config.setDriverClassName("com.mysql.jdbc.Driver");
		config.addDataSourceProperty("cachePrepStmts", true);
		config.addDataSourceProperty("prepStmtCacheSize", 250);
		config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
		config.addDataSourceProperty("useServerPrepStmts", true);
		config.addDataSourceProperty("useLocalSessionState", true);
		config.addDataSourceProperty("rewriteBatchedStatements", true);
		config.addDataSourceProperty("cacheResultSetMetadata", true);
		config.addDataSourceProperty("cacheServerConfiguration", true);
		config.addDataSourceProperty("elideSetAutoCommits", true);
		config.addDataSourceProperty("maintainTimeStats", false);
		config.addDataSourceProperty("characterEncoding", "utf8");
		config.addDataSourceProperty("encoding", "UTF-8");
		config.addDataSourceProperty("useUnicode", "true");
		config.addDataSourceProperty("useSSL", false);
		//config.addDataSourceProperty("autoReconnect", true);
		config.addDataSourceProperty("tcpKeepAlive", true);
		config.setLeakDetectionThreshold(60 * 1000);
		config.setPoolName("ComuHikariCP "+DB++);
		config.setMaxLifetime(60000);
		config.setMinimumIdle(0);
		config.setIdleTimeout(30000);
		config.setConnectionTimeout(10000);
		config.setLeakDetectionThreshold(30000);
		config.setMaximumPoolSize(40);
		config.validate();
		this.dataSource = new HikariDataSource(config);
		this.plugin = plugin;

    }

	public DatabaseManager(RFTB plugin) { 
		this.plugin = plugin;
	}
	    
	/*public Connection open() {
		try {
			Class.forName(driver);

			this.c = dataSource.getConnection();
			return c;
		} catch (SQLException e) { 
			System.out.println("Could not connect to Database! because: " + e.getMessage()); 
		} catch (ClassNotFoundException e) { 
			System.out.println(driver+" not found!"); 
		} catch (Exception e) { 
			System.out.println(e.getMessage()); 
		} 
		return this.c;
	} */
	   
	public Connection getConn() { 
		return this.c;
	} 
	
	public void close() {
		dataSource.close();
		c = null;

	}
	
	public boolean isConnected() {
		try {
			return((c==null || c.isClosed()) ? false:true);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public class Result implements AutoCloseable {
		private ResultSet resultSet;
		private Statement statement;
		private Connection cn;

		public Result(Statement statement, ResultSet resultSet, Connection connection) {
			this.statement = statement;
			this.resultSet = resultSet;
			this.cn = connection;
		}

		public ResultSet getResultSet() {
			return this.resultSet;
		}

		public void close() {
			try {
				if (this.statement != null){
					if (!this.statement.isClosed()) {
						this.statement.close();
					}
				}
				if (this.resultSet != null) {
					if (!this.resultSet.isClosed()	){
						this.resultSet.close();
					}
				}
				if (this.cn != null){
					if (!this.cn.isClosed()){
						this.cn.close();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	} 
	
	public Result query(final String query) {
		return query(query,false);
	}
	
	public Result query(final String query, boolean retry) {
		/*ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<Result> result = executor.submit(new Callable<Result>() {
			@Override
			public synchronized Result call() throws Exception {
			}
		});*/
		try {
			if (getStatement(query) == Statements.SELECT){
                try (Connection c = dataSource.getConnection()) {
                    PreparedStatement statement = c.prepareStatement(query);
                    statement.execute();
                    return new Result(statement, statement.getResultSet(), c);

                }
				/*AsyncQuery(new Callback<Result>() {
					@Override
					public void onSuccess(Result done) {
						new Result(done.statement, done.resultSet, c);
						return;
					}

					@Override
					public void onFailure(Throwable cause) {

					}
				}, query, true);*/
			} else {
				Bukkit.getScheduler().runTaskAsynchronously(RFTB.getInstance(), new BukkitRunnable() {
					@Override
					public void run() {
                        /*try {
                            Connection c = dataSource.getConnection();
                        try {
                            PreparedStatement statement = c.prepareStatement(query);
							statement.executeUpdate();
							DebugMode.senddebug(DebugMode.Debug.INFO,"DEBUG UPDATE " +query + " ASYNC");

						} catch (SQLException e) {
							e.printStackTrace();
						} finally {
                            try {
                                if (c != null || !c.isClosed()) {
                                    c.close();
									DebugMode.senddebug(DebugMode.Debug.INFO,"DEBUG UPDATE " + query + " CLOSE");
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }*/
						try (Connection c = dataSource.getConnection()) {
							PreparedStatement statement = c.prepareStatement(query);
							statement.executeUpdate();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				});
				return new Result(null, null, c);
			}
		} catch (final SQLException e) {
			final String msg = e.getMessage();
			logger.severe("Database query error: " + msg);
			if (retry && msg.contains("_BUSY")) {
				logger.severe("Retrying query...");
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						query(query,false);
					}
				}, 20);
			}
			return null;
		}
	}

	public Result querySync(final String query, boolean retry) {
		/*ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<Result> result = executor.submit(new Callable<Result>() {
			@Override
			public synchronized Result call() throws Exception {
			}
		});*/
		try {
			if (getStatement(query) == Statements.SELECT){
                try (Connection c = dataSource.getConnection()) {
                    PreparedStatement statement = c.prepareStatement(query);
                    statement.execute();
                    return new Result(statement, statement.getResultSet(), c);

                }
				/*AsyncQuery(new Callback<Result>() {
					@Override
					public void onSuccess(Result done) {
						new Result(done.statement, done.resultSet, c);
						return;
					}

					@Override
					public void onFailure(Throwable cause) {

					}
				}, query, true);*/
			} else {
				try (Connection c = dataSource.getConnection()) {
					PreparedStatement statement = c.prepareStatement(query);
					statement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				return new Result(null, null, c);
			}
		} catch (final SQLException e) {
			final String msg = e.getMessage();
			logger.severe("Database query error: " + msg);
			if (retry && msg.contains("_BUSY")) {
				logger.severe("Retrying query...");
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						querySync(query,false);
					}
				}, 20);
			}
			return null;
		}
	}
	
	 
	 
	protected Statements getStatement(String query) { 
		String trimmedQuery = query.trim(); 
	    if (trimmedQuery.substring(0, 6).equalsIgnoreCase("SELECT")) 
	      return Statements.SELECT; 
	    if (trimmedQuery.substring(0, 6).equalsIgnoreCase("INSERT")) 
	      return Statements.INSERT; 
	    if (trimmedQuery.substring(0, 6).equalsIgnoreCase("UPDATE")) 
	      return Statements.UPDATE; 
	    if (trimmedQuery.substring(0, 6).equalsIgnoreCase("DELETE")) 
	      return Statements.DELETE; 
	    if (trimmedQuery.substring(0, 6).equalsIgnoreCase("CREATE")) 
	      return Statements.CREATE; 
	    if (trimmedQuery.substring(0, 5).equalsIgnoreCase("ALTER")) 
	      return Statements.ALTER; 
	    if (trimmedQuery.substring(0, 4).equalsIgnoreCase("DROP")) 
	      return Statements.DROP; 
	    if (trimmedQuery.substring(0, 8).equalsIgnoreCase("TRUNCATE")) 
	      return Statements.TRUNCATE; 
	    if (trimmedQuery.substring(0, 6).equalsIgnoreCase("RENAME")) 
	      return Statements.RENAME; 
	    if (trimmedQuery.substring(0, 2).equalsIgnoreCase("DO")) 
	      return Statements.DO; 
	    if (trimmedQuery.substring(0, 7).equalsIgnoreCase("REPLACE")) 
	      return Statements.REPLACE; 
	    if (trimmedQuery.substring(0, 4).equalsIgnoreCase("LOAD")) 
	      return Statements.LOAD; 
	    if (trimmedQuery.substring(0, 7).equalsIgnoreCase("HANDLER")) 
	      return Statements.HANDLER; 
	    if (trimmedQuery.substring(0, 4).equalsIgnoreCase("CALL")) { 
	      return Statements.CALL; 
	    } 
	    return Statements.SELECT; 
	} 
	    
	protected static enum Statements { 
	    SELECT, INSERT, UPDATE, DELETE, DO, REPLACE, LOAD, HANDLER, CALL,  
	    CREATE, ALTER, DROP, TRUNCATE, RENAME, START, COMMIT, ROLLBACK,  
	    SAVEPOINT, LOCK, UNLOCK, PREPARE, EXECUTE, DEALLOCATE, SET, SHOW,  
	    DESCRIBE, EXPLAIN, HELP, USE, ANALYZE, ATTACH, BEGIN, DETACH,  
	    END, INDEXED, ON, PRAGMA, REINDEX, RELEASE, VACUUM; 
	}


	public void AsyncQuery(final Callback<Result> callback, final String query, boolean retry) {
		Bukkit.getScheduler().runTaskAsynchronously(RFTB.getInstance(), new Runnable() {
			@Override
			public void run() {
				Result result = null;

				Result finalResult = result;
				Bukkit.getScheduler().runTask(RFTB.getInstance(), new BukkitRunnable() {
					@Override
					public void run() {
						try {
							callback.onSuccess(finalResult);
						} catch (Exception ex) {
							callback.onFailure(ex.getCause());
						}
					}
				});
			}
		});

	}

	public interface Callback<T> {
		void onSuccess(T done);
		void onFailure(Throwable cause);
	}	  	
}
