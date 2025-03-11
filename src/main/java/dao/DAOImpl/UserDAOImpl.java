package dao.DAOImpl;

import dao.UserDAO;
import model.User;
import util.DruidDBConnection;
import util.SystemLogger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author confff
 */
public class UserDAOImpl implements UserDAO {
    @Override
    public boolean addUser(User user) {
        if(user==null){
            System.out.println("用户不能为空");
            return false;
        }
        String sql = "insert into atm.user (name, phone, id_card, card_number, password) values(?,?,?,?,?)";

        try(Connection conn = DruidDBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPhone());
            pstmt.setString(3,user.getIdCard());
            pstmt.setString(4, user.getCardId());
            pstmt.setString(5, user.getPassword());
            int rows = pstmt.executeUpdate();
            return rows>0;
        } catch (SQLException e) {
            System.out.println("添加用户失败:"+ e.getMessage());
            SystemLogger.logError(e.getMessage(),e);
        }
        return false;
    }

    @Override
    public boolean updateBalance(String cardId, BigDecimal balance, Connection conn) {
        String sql = "update atm.user set balance = ? where card_number = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setBigDecimal(1, balance);
            pstmt.setString(2, cardId);
            int rows = pstmt.executeUpdate();
            return rows>0;
        }catch (SQLException e){
            System.out.println("添加用户失败:"+ e.getMessage());
            SystemLogger.logError(e.getMessage(),e);
        }
        return false;
    }

    @Override
    public User findUserById(String cardId) {
        String sql = "select * from atm.user where card_number = ?";
        User user = new User();
        try(Connection conn = DruidDBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, cardId);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            user.setId(rs.getInt("id"));
            user.setIdCard(rs.getString("id_card"));
            user.setName(rs.getString("name"));
            user.setPhone(rs.getString("phone"));
            user.setCardId(rs.getString("card_number"));
            user.setPassword(rs.getString("password"));
            user.setBalance(rs.getBigDecimal("balance"));
            return user;
        }catch(SQLException e){
            System.out.println("查找用户失败:"+ e.getMessage());
            SystemLogger.logError(e.getMessage(),e);
        }
        return null;
    }

    public User findUserById(String cardId, Connection conn) {
        String sql = "select * from atm.user where card_number = ?";
        User user = new User();
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, cardId);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            user.setId(rs.getInt("id"));
            user.setIdCard(rs.getString("id_card"));
            user.setName(rs.getString("name"));
            user.setPhone(rs.getString("phone"));
            user.setCardId(rs.getString("card_number"));
            user.setPassword(rs.getString("password"));
            user.setBalance(rs.getBigDecimal("balance"));
            return user;
        }catch(SQLException e){
            System.out.println("查找用户失败:"+ e.getMessage());
            SystemLogger.logError(e.getMessage(),e);
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "select * from atm.user";
        List<User> users = new ArrayList<User>();
        try(Connection conn = DruidDBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            ResultSet rs = pstmt.executeQuery();
            User user = null;
            while(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setIdCard(rs.getString("id_card"));
                user.setName(rs.getString("name"));
                user.setPhone(rs.getString("phone"));
                user.setCardId(rs.getString("card_number"));
                user.setPassword(rs.getString("password"));
                user.setBalance(rs.getBigDecimal("balance"));
                users.add(user);
            }
            return users;
        }catch (SQLException e){
            System.out.println("获取全部用户信息失败:"+ e.getMessage());
            SystemLogger.logError("获取全部用户信息失败:",e);
        }
        return Collections.emptyList();
    }
}
