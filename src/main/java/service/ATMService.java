package service;

import model.User;

/**
 * @author confff
 */
public interface ATMService {
    /**
     * 注册功能
     * @return 新用户对象
     */
    public User register();

    /**
     * 登录功能
     * @return 登录的用户对象
     */
    public User login();

    /**
     * 存款功能
     * @param user 当前操作用户
     */
    public void deposit(User user);

    /**
     * 取款功能
     * @param user 当前操作用户
     */
    public void withdraw(User user);

    /**
     * 转账功能
     * @param user 当前操作用户
     */
    public void transfer(User user);

    /**
     * 查找交易记录功能
     * @param user 当前操作用户
     */
    public void findTransaction(User user);

}
