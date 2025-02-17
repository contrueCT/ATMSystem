package dao;

import model.Transaction;
import model.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Service {
    public static boolean serviceDAO(Transaction transaction, String user_id) {
        Connection conn = null;
        UserDAOImpl userDAO = new UserDAOImpl();
        TransactionDAOImpl transactionDAO = new TransactionDAOImpl();

        try{
            conn = DruidDBConnection.getConnection();
            conn.setAutoCommit(false);
            if(transaction==null){
                System.out.println("错误：交易记录对象不存在");
                return false;
            }
            if(transaction.getType().equals("deposit")){
                User user = userDAO.findUserById(user_id);
                user.setBalance(user.getBalance().add(transaction.getAmount()));
                if(userDAO.updateBalance(user.getId_card(), user.getBalance(),conn)){
                    System.out.println("更新余额成功");
                }
                else{
                    System.out.println("更新余额失败");
                    return false;
                }
                if(transactionDAO.addTransaction(transaction,conn)){
                    System.out.println("交易记录创建成功");
                }else{
                    System.out.println("交易记录创建失败");
                    return false;
                }

                conn.commit();
                return true;
            }
            //其他业务
            return false;
        }catch(SQLException e){
            System.out.println("操作失败"+e.getMessage());
            try {
                if(conn!=null){
                    conn.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("回滚事务失败"+ex.getMessage());
            }
            System.out.println("回滚成功");
            return false;
        }finally{
            DruidDBConnection.closeConnection(conn);
        }

    }
}
