package util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author confff
 */
public class MyConnectionPool {
    private static MyConnectionPool DataSource;
    private LinkedList<Connection> connectionPool;
    private String url;
    private String user;
    private String password;
    private int initialSize;

    private MyConnectionPool() {
    }

    public static synchronized MyConnectionPool getDataSource() {

        if (DataSource == null) {
            DataSource = new MyConnectionPool();

            try {
                DataSource.initFromConfig("db.properties");
            } catch (IOException e) {
                SystemLogger.logError("初始化连接池时，文件读取失败，初始化失败",e);
                throw new RuntimeException(e);
            } catch (SQLException e) {
                SystemLogger.logError("初始化连接池时，连接创建失败，初始化失败",e);
                throw new RuntimeException(e);
            }
        }
        return DataSource;
    }

    public void initFromConfig(String configFilePath) throws IOException, SQLException {
        Properties prop = new Properties();

        try(InputStream input = MyConnectionPool
                .class.getClassLoader()
                .getResourceAsStream(configFilePath)) {
            if (input == null) {
                throw new FileNotFoundException(configFilePath);
            }
            prop.load(input);
            this.url = prop.getProperty("url");
            this.user = prop.getProperty("username");
            this.password = prop.getProperty("password");
            this.initialSize = Integer.parseInt(prop.getProperty("initialSize","5"));

            connectionPool = new LinkedList<>();
            for(int i = 0;i<this.initialSize;i++){
                Connection conn = DataSource.createConnection();
                connectionPool.add(conn);
            }
        }
    }

    //初始化时生成连接
    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }

    //获取连接，通过instance
    public synchronized Connection getConnection() throws SQLException, IOException {
        if(connectionPool.isEmpty()){
            throw new SQLException("已无空闲连接池，获取失败");
        }
        Connection conn = connectionPool.removeFirst();
        return new ConnectionWrapper(conn,this);
    }

    //释放连接
    public void releaseConnection(Connection conn) {
        if (conn != null) {
            try{
                if(!conn.isClosed()){
                    connectionPool.add(conn);
                }
            } catch (SQLException e) {
                SystemLogger.logError("连接回收失败",e);
                throw new RuntimeException(e);
            }
        }
    }

    //关闭连接池
    public void closeAll() {
        for(Connection conn:connectionPool){
            try{
                if(!conn.isClosed()){
                    conn.close();
                }
            }catch(SQLException e){
                SystemLogger.logError("关闭连接失败",e);
                System.out.println("关闭连接失败");
            }
        }
    }

    /**
     * 包装类，用于重写close方法
     */
    private static class ConnectionWrapper implements Connection {
        private final MyConnectionPool pool;
        private final Connection conn;

        public ConnectionWrapper(Connection conn,MyConnectionPool pool) {
            this.conn = conn;
            this.pool = pool;
        }

        @Override
        public void close() {
            System.out.println("ConnectionWrapper的close被执行");
            pool.releaseConnection(conn);
        }

        /**
         * 以下是委托方法，jdbc的原生实现
         *
         */

        @Override
        public Statement createStatement() throws SQLException {
            return conn.createStatement();
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return conn.prepareStatement(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return conn.prepareCall(sql);
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return conn.nativeSQL(sql);
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            conn.setAutoCommit(autoCommit);
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return conn.getAutoCommit();
        }

        @Override
        public void commit() throws SQLException {
            conn.commit();
        }

        @Override
        public void rollback() throws SQLException {
            conn.rollback();
        }


        @Override
        public boolean isClosed() throws SQLException {
            return conn.isClosed();
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return conn.getMetaData();
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            conn.setReadOnly(readOnly);
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return conn.isReadOnly();
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            conn.setCatalog(catalog);
        }

        @Override
        public String getCatalog() throws SQLException {
            return conn.getCatalog();
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            conn.setTransactionIsolation(level);
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return conn.getTransactionIsolation();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return conn.getWarnings();
        }

        @Override
        public void clearWarnings() throws SQLException {
            conn.clearWarnings();
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return conn.createStatement(resultSetType,resultSetConcurrency);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return null;
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return null;
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return Collections.emptyMap();
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            conn.setTypeMap(map);
        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            conn.setHoldability(holdability);
        }

        @Override
        public int getHoldability() throws SQLException {
            return conn.getHoldability();
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return conn.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return conn.setSavepoint(name);
        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {
            conn.rollback(savepoint);
        }

        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            conn.releaseSavepoint(savepoint);
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return conn.createStatement(resultSetType,resultSetConcurrency,resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return conn.prepareStatement(sql,resultSetType,resultSetConcurrency,resultSetHoldability);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return conn.prepareCall(sql,resultSetType,resultSetConcurrency,resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return conn.prepareStatement(sql,autoGeneratedKeys);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return conn.prepareStatement(sql,columnIndexes);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return conn.prepareStatement(sql,columnNames);
        }

        @Override
        public Clob createClob() throws SQLException {
            return conn.createClob();
        }

        @Override
        public Blob createBlob() throws SQLException {
            return conn.createBlob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            return conn.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return conn.createSQLXML();
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return conn.isValid(timeout);
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            conn.setClientInfo(name, value);
        }

        @Override
        public void setClientInfo(Properties properties) throws SQLClientInfoException {
            conn.setClientInfo(properties);
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return conn.getClientInfo(name);
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return conn.getClientInfo();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return conn.createArrayOf(typeName,elements);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return conn.createStruct(typeName,attributes);
        }

        @Override
        public void setSchema(String schema) throws SQLException {
            conn.setSchema(schema);
        }

        @Override
        public String getSchema() throws SQLException {
            return conn.getSchema();
        }

        @Override
        public void abort(Executor executor) throws SQLException {
            conn.abort(executor);
        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
            conn.setNetworkTimeout(executor,milliseconds);
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return conn.getNetworkTimeout();
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return conn.unwrap(iface);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return conn.isWrapperFor(iface);
        }
    }
}
