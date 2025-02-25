package util;

import dao.DruidDBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static boolean initializeDatabase() {
        try(Connection conn = DruidDBConnection.getConnection();
            Statement stmt = conn.createStatement()){
            stmt.executeUpdate("create database if not exists atm");
            stmt.executeUpdate("use atm");

            String createUser = "create table if not exists user(" +
                    "id int auto_increment primary key," +
                    "name varchar(50) not null," +
                    "phone varchar(11) unique check (phone regexp '^1[3-9]\\\\d{9}$'),"+
                    "id_card char(18) unique ," +
                    "card_number varchar(19) unique check (length(card_number) between 16 and 19)," +
                    "balance decimal(15,2) unsigned default 0.00," +
                    "password varchar(6) check (password regexp '^\\\\d{6}$')" +
                    ");";
            stmt.executeUpdate(createUser);
            String createTransaction = "create table if not exists user(" +
                    "id int auto_increment primary key," +
                    "user_id int ," +
                    "type enum('deposit','withdrawal','transfer') ,"+
                    "amount decimal(15,2)," +
                    "source_card varchar(19)," +
                    "target_card varchar(19),"+
                    "timestamp datetime default current_timestamp," +
                    "foreign key (user_id) references user(id)" +
                    ");";
            stmt.executeUpdate(createTransaction);


            return true;
        }catch(SQLException e){
            System.out.println("数据库创建失败"+e.getMessage());
            return false;
        }

    }
}
