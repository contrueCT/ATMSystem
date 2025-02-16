package dao;

import model.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserDAO {
    boolean addUser(User user);
    boolean updateBalance(String card_id, BigDecimal balance);
    User findUserById(String card_id);//通过银行卡查找信息
    List<User> getAllUsers();

}
