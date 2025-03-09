package service;

import model.User;

import java.math.BigDecimal;

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

    public Boolean deposit(String cardId, BigDecimal amount);

    /**
     * 取款功能
     * @param user 当前操作用户
     */
    public void withdraw(User user);

    public Boolean withdraw(String cardId, BigDecimal amount);

    /**
     * 转账功能
     * @param user 当前操作用户
     */
    public void transfer(User user);

    public Boolean transfer(String cardId, BigDecimal amount, String targetCardId);

    /**
     * 查找交易记录功能
     * @param user 当前操作用户
     */
    public void findTransaction(User user);

    /**
     * web登录方法
     * @param cardId    卡号
     * @param password  密码
     * @return  是否登录成功
     */
    public Boolean login(String cardId,String password);

    /**
     * web注册方法
     * @param name  姓名
     * @param phone 手机号
     * @param idCard    身份证号
     * @param cardId    卡号
     * @param password  密码
     * @return  是否注册成功
     */
    public Boolean register(String name,String phone,String idCard,String cardId,String password);


    /**
     *
     * @param cardId 用户卡号
     * @return 账户余额
     */
    public BigDecimal getBalance(String cardId);

    public void showBalance(User user);

}
