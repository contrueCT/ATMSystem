package dao;

import model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {
    @Override
    public boolean addTransaction(Transaction transaction) {
        if(transaction == null){
            return false;
        }
        String sql = "insert into transactions (user_id, type, amount, source_card, target_card) value (?,?,?,?,?)";
        try(Connection conn = DruidDBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, transaction.getUser_id());
            pstmt.setString(2, transaction.getType());
            pstmt.setBigDecimal(3, transaction.getAmount());
            pstmt.setString(4, transaction.getSourceCard());
            pstmt.setString(5, transaction.getTargetCard());
            pstmt.executeUpdate();
            return true;

        }catch (SQLException e){
            System.out.println("添加交易记录失败"+e.getMessage());
        }
        return false;
    }

    public boolean addTransaction(Transaction transaction,Connection conn) {
        if(transaction == null){
            return false;
        }
        String sql = "insert into transactions (user_id, type, amount, source_card, target_card) value (?,?,?,?,?)";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, transaction.getUser_id());
            pstmt.setString(2, transaction.getType());
            pstmt.setBigDecimal(3, transaction.getAmount());
            pstmt.setString(4, transaction.getSourceCard());
            pstmt.setString(5, transaction.getTargetCard());
            pstmt.executeUpdate();
            return true;

        }catch (SQLException e){
            System.out.println("添加交易记录失败"+e.getMessage());
        }
        return false;
    }

    @Override
    public List<Transaction> findTransactionByCardID(String card_id) {
        String sql = "select * from transactions where source_card = ?";
        List<Transaction> transactions = null;

        try(Connection conn = DruidDBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, card_id);
            ResultSet rs = pstmt.executeQuery();
            Transaction transaction = null;
            while(rs.next()){
                transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setUser_id(rs.getInt("user_id"));
                transaction.setType(rs.getString("type"));
                transaction.setAmount(rs.getBigDecimal("amount"));
                transaction.setSourceCard(rs.getString("source_card"));
                transaction.setTargetCard(rs.getString("target_card"));
                transactions.add(transaction);
            }
            return transactions;
        }catch (SQLException e){
            System.out.println("获取全部交易记录失败"+e.getMessage());
        }
        return Collections.emptyList();
    }
}
