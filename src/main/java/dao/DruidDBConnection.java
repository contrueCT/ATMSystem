package dao;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DruidDBConnection {
    private DruidDBConnection() {}
    private static DataSource dataSource;
    //初始化连接池
    private static void initDataSource() {
        if (dataSource == null) {
            synchronized (DruidDBConnection.class) {
                if (dataSource == null) {
                    try {
                        Properties prop = new Properties();
                        try (InputStream in = DruidDBConnection.class
                                .getClassLoader()
                                .getResourceAsStream("druid.properties")) {
                            if (in == null) {
                                throw new IOException("Cannot find druid.properties");

                            }
                            prop.load(in);
                        }
                        dataSource = DruidDataSourceFactory.createDataSource(prop);

                        String sql = "use user";
                        try (Connection conn = dataSource.getConnection();
                             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                            pstmt.execute();
                        }

                    }catch (IOException e){
                        e.printStackTrace();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
    //获取连接
    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            initDataSource();
        }
        return dataSource.getConnection();
    }

    //关闭连接
    public static void closeConnection(Connection conn) {
        if(conn != null) {
            try{
                conn.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
