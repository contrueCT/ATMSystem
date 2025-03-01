package service.ServiceImpl;

import dao.DAOImpl.TransactionDAOImpl;
import dao.DAOImpl.UserDAOImpl;
import model.Transaction;
import model.User;
import util.AccountLockManager;
import util.DruidDBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class Service {
    public static boolean serviceDAO(Transaction transaction, String card_number) {
        Connection conn = null;
        UserDAOImpl userDAO = new UserDAOImpl();
        TransactionDAOImpl transactionDAO = new TransactionDAOImpl();

        //确定要锁定的账户
        String[] cardToLock;
        if(transaction.getType().equals("tranfer")){
            cardToLock = new String[]{card_number,transaction.getTargetCard()};
        }else{
            cardToLock = new String[]{card_number};
        }
        //获得涉及账户的锁
        AccountLockManager.acquireLocks(cardToLock);

        try{
            conn = DruidDBConnection.getConnection();
            conn.setAutoCommit(false);
            if(transaction==null){
                System.out.println("错误：交易记录对象不存在");
                return false;
            }
            //存款
            if(transaction.getType().equals("deposit")){
                User user = userDAO.findUserById(card_number,conn);
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
            //取款
            if(transaction.getType().equals("withdraw")){
                User user = userDAO.findUserById(card_number,conn);
                user.setBalance(user.getBalance().subtract(transaction.getAmount()));
                if(user.getBalance().compareTo(BigDecimal.ZERO)<0){
                    System.out.println("账户余额不足");
                    return false;
                }
                if(userDAO.updateBalance(user.getId_card(), user.getBalance(),conn)){
                    System.out.println("余额更新成功");
                }
                else{
                    System.out.println("余额更新失败");
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
            //转账
            if(transaction.getType().equals("transfer")){
                User user = userDAO.findUserById(card_number,conn);
                User target = userDAO.findUserById(transaction.getTargetCard());

                target.setBalance(target.getBalance().add(transaction.getAmount()));
                user.setBalance(user.getBalance().subtract(transaction.getAmount()));
                if(user.getBalance().compareTo(BigDecimal.ZERO)<0){
                    System.out.println("账户余额不足");
                }
                if(userDAO.updateBalance(user.getId_card(), user.getBalance(),conn)
                        && userDAO.updateBalance(transaction.getTargetCard(), target.getBalance(),conn)){
                    System.out.println("余额更新成功");
                }
                else{
                    System.out.println("余额更新失败");
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
            AccountLockManager.releaseLocks(cardToLock);
        }

    }
}
