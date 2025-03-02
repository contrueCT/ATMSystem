package service;

import model.User;

/**
 * @author confff
 */
public interface ATMService {
    public User register();

    public User login();

    public void deposit(User user);

    public void withdraw(User user);

    public void transfer(User user);

    public void findTransaction(User user);

}
