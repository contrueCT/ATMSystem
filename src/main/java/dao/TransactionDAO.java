package dao;

import model.Transaction;

import java.util.List;

public interface TransactionDAO {
    boolean addTransaction(Transaction transaction);
    List<Transaction> findTransactionByCardID(String card_id);

}
