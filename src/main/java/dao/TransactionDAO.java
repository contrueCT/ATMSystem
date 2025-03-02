package dao;

import model.Transaction;

import java.util.List;

/**
 * @author confff
 */
public interface TransactionDAO {

    /**
     * 新增交易记录
     * @param transaction 交易记录对象
     * @return 是否成功添加交易记录
     */
    boolean addTransaction(Transaction transaction);

    /**
     * 根据银行卡号查找交易记录
     * @param cardId 卡号
     * @return 所有交易记录集合
     */
    List<Transaction> findTransactionByCardID(String cardId);

}
