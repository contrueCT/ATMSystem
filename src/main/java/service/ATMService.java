package service;

import dao.Service;
import dao.TransactionDAOImpl;
import dao.UserDAOImpl;
import model.Transaction;
import model.User;

import java.math.BigDecimal;
import java.util.Scanner;

public class ATMService {
    public static void register(){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入姓名：");
        String name = sc.nextLine();
        System.out.println("请输入手机号：");
        String phone = sc.nextLine();
        System.out.println("请输入身份证号：");
        String id_card = sc.nextLine();
        System.out.println("请输入银行卡号");
        String card_number = sc.nextLine();
        System.out.println("请输入密码（6位数字）：");
        String password = sc.nextLine();
        User user = new User(name, phone, id_card, card_number, password);

        UserDAOImpl userDAO = new UserDAOImpl();
        if(!userDAO.addUser(user)){
            System.out.println("注册失败，请检查手机号、身份证号、银行卡号、密码是否有误");
        }
        System.out.println("注册成功，即将进入系统界面");

    }
    public static User login(){
        Scanner sc = new Scanner(System.in);
        UserDAOImpl userDAO = new UserDAOImpl();

        while (true) {
            System.out.println("请输入身份证号：");
            String id_card = sc.nextLine();
            System.out.println("请输入密码：");
            String password = sc.nextLine();

            User user = userDAO.findUserById(id_card);
            if(user == null){
                System.out.println("未查找到该账户,是否继续登录(Y/N)");
                String re = sc.nextLine();

                if(InputValidator.isValidYes()){
                    continue;
                }else{
                    return null;
                }

            }
            if(password.equals(user.getPassword())){
                System.out.println("登录成功，即将进入系统界面");
                return user;
            }else{
                System.out.println("密码错误，是否继续登录（Y/N）");

                if(InputValidator.isValidYes()){
                    continue;
                }else{
                    return null;
                }
            }
        }
    }

    public static void deposit(User user){
        Scanner sc = new Scanner(System.in);
        UserDAOImpl userDAO = new UserDAOImpl();
        TransactionDAOImpl transactionDAO = new TransactionDAOImpl();

        System.out.println("请输入要存款的金额：");
        BigDecimal money = InputValidator.isValidBigDecimal();
        Transaction deposit = new Transaction(user.getId(), "deposit", money, user.getCard_number(), user.getCard_number());
        if(Service.serviceDAO(deposit,String.valueOf(user.getId()))){
            System.out.println("存款成功");
            return;
        }
        System.out.println("存款失败");

    }

    public static void withdraw(User user){
        Scanner sc = new Scanner(System.in);
        TransactionDAOImpl transactionDAO = new TransactionDAOImpl();
    }
}
