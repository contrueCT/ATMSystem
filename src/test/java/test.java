import util.MyDBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class test {
    public static void main(String[] args) throws SQLException {
        Connection conn = MyDBConnection.getConnection();
        System.out.println(conn.getClass().getName()); // 打印实际的类名
        conn.close();
    }
}
