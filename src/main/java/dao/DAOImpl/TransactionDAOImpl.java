package dao.DAOImpl;

import dao.TransactionDAO;
import model.Transaction;
import util.DruidDBConnection;
import util.SystemLogger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author confff
 */
public class TransactionDAOImpl implements TransactionDAO {
    @Override
    public boolean addTransaction(Transaction transaction) {
        if(transaction == null){
            return false;
        }
        String sql = "insert into atm.transactions (user_id, type, amount, source_card, target_card) value (?,?,?,?,?)";
        try(Connection conn = DruidDBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, transaction.getUserId());
            pstmt.setString(2, transaction.getType());
            pstmt.setBigDecimal(3, transaction.getAmount());
            pstmt.setString(4, transaction.getSourceCard());
            pstmt.setString(5, transaction.getTargetCard());
            pstmt.executeUpdate();
            return true;

        }catch (SQLException e){
            System.out.println("添加交易记录失败"+e.getMessage());
            SystemLogger.logError(e.getMessage(),e);
        }
        return false;
    }

    public boolean addTransaction(Transaction transaction,Connection conn) {
        if(transaction == null){
            return false;
        }
        String sql = "insert into atm.transactions (user_id, type, amount, source_card, target_card) value (?,?,?,?,?)";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, transaction.getUserId());
            pstmt.setString(2, transaction.getType());
            pstmt.setBigDecimal(3, transaction.getAmount());
            pstmt.setString(4, transaction.getSourceCard());
            pstmt.setString(5, transaction.getTargetCard());
            pstmt.executeUpdate();
            return true;

        }catch (SQLException e){
            System.out.println("添加交易记录失败"+e.getMessage());
            SystemLogger.logError(e.getMessage(),e);
        }
        return false;
    }

    @Override
    public List<Transaction> findTransactionByCardID(String cardId) {
        String sql = "select * from atm.transactions where source_card = ?";
        List<Transaction> transactions = new ArrayList<>();

        try(Connection conn = DruidDBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, cardId);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                int id= rs.getInt("id");
                int userId = rs.getInt("user_id");
                String type = rs.getString("type");
                BigDecimal amount = rs.getBigDecimal("amount");
                String sourceCard = rs.getString("source_card");
                String targetCard = rs.getString("target_card");
                LocalDateTime time = rs.getTimestamp("timestamp").toLocalDateTime();
                Transaction transaction = new Transaction(id,userId,type,amount,sourceCard,targetCard,time);
                transactions.add(transaction);
            }
            return transactions;
        }catch (SQLException e){
            System.out.println("获取全部交易记录失败"+e.getMessage());
            SystemLogger.logError(e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }
}
