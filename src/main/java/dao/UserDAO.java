package dao;

import model.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

/**
 * @author confff
 */
public interface UserDAO {
    /**
     * 新增用户信息
     * @param user 用户对象
     * @return 是否成功
     */
    boolean addUser(User user);

    /**
     * 更新余额
     * @param cardId 卡号
     * @param balance 新余额
     * @return 是否成功
     */
    public boolean updateBalance(String cardId, BigDecimal balance, Connection conn);

    /**
     * 通过银行卡查找信息
     * @param cardId 卡号
     * @return 找到的用户对象
     */
    User findUserById(String cardId);

    /**
     * 获取所有用户信息
     * @return 用户对象集合
     */
    List<User> getAllUsers();

}
