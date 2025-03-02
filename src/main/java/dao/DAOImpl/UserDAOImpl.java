package dao.DAOImpl;

import dao.UserDAO;
import model.User;
import util.DruidDBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean addUser(User user) {
        if(user==null){
            System.out.println("用户不能为空");
            return false;
        }
        String sql = "insert into user (name, phone, id_card, card_number, password) values(?,?,?,?,?)";

        try(Connection conn = DruidDBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPhone());
            pstmt.setString(3,user.getId_card());
            pstmt.setString(4, user.getCard_number());
            pstmt.setString(5, user.getPassword());
            int rows = pstmt.executeUpdate();
            return rows>0;
        } catch (SQLException e) {
            System.out.println("添加用户失败:"+ e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateBalance(String cardId, BigDecimal balance) {
        String sql = "update user set balance = ? where id_card = ?";
        try(Connection conn = DruidDBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setBigDecimal(1, balance);
            pstmt.setString(2, cardId);
            pstmt.executeUpdate();
            return true;
        }catch (SQLException e){
            System.out.println("添加用户失败:"+ e.getMessage());
        }
        return false;
    }

    public boolean updateBalance(String card_id, BigDecimal balance,Connection conn) {
        String sql = "update user set balance = ? where id_card = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setBigDecimal(1, balance);
            pstmt.setString(2, card_id);
            pstmt.executeUpdate();
            return true;
        }catch (SQLException e){
            System.out.println("添加用户失败:"+ e.getMessage());
        }
        return false;
    }

    @Override
    public User findUserById(String cardId) {
        String sql = "select * from user where id_card = ?";
        User user = new User();
        try(Connection conn = DruidDBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, cardId);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            user.setId(rs.getInt("id"));
            user.setId_card(rs.getString("id_card"));
            user.setName(rs.getString("name"));
            user.setPhone(rs.getString("phone"));
            user.setCard_number(rs.getString("card_number"));
            user.setPassword(rs.getString("password"));
            user.setBalance(rs.getBigDecimal("balance"));
            return user;
        }catch(SQLException e){
            System.out.println("查找用户失败:"+ e.getMessage());
        }
        return null;
    }

    public User findUserById(String card_id, Connection conn) {
        String sql = "select * from user where card_number = ?";
        User user = new User();
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, card_id);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            user.setId(rs.getInt("id"));
            user.setId_card(rs.getString("id_card"));
            user.setName(rs.getString("name"));
            user.setPhone(rs.getString("phone"));
            user.setCard_number(rs.getString("card_number"));
            user.setPassword(rs.getString("password"));
            user.setBalance(rs.getBigDecimal("balance"));
            return user;
        }catch(SQLException e){
            System.out.println("查找用户失败:"+ e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "select * from user";
        List<User> users = new ArrayList<User>();
        try(Connection conn = DruidDBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            ResultSet rs = pstmt.executeQuery();
            User user = null;
            while(rs.next()){
                user = new User();
                user.setId(rs.getInt("id_card"));
                user.setId_card(rs.getString("id_card"));
                user.setName(rs.getString("name"));
                user.setPhone(rs.getString("phone"));
                user.setCard_number(rs.getString("card_number"));
                user.setPassword(rs.getString("password"));
                user.setBalance(rs.getBigDecimal("balance"));
                users.add(user);
            }
            return users;
        }catch (SQLException e){
            System.out.println("获取全部用户信息失败:"+ e.getMessage());
        }
        return Collections.emptyList();
    }
}
