package service.ServiceImpl;

import dao.DAOImpl.TransactionDAOImpl;
import dao.DAOImpl.UserDAOImpl;
import model.Transaction;
import model.User;
import service.ATMService;
import util.InputValidator;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ATMServiceImpl implements ATMService {
    Scanner sc = new Scanner(System.in);
    UserDAOImpl userDAO = new UserDAOImpl();
    TransactionDAOImpl transactionDAO = new TransactionDAOImpl();

    public User register(){
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

        if(!userDAO.addUser(user)){
            System.out.println("注册失败，请检查手机号、身份证号、银行卡号、密码是否有误");
            return null;
        }
        System.out.println("注册成功，即将进入系统界面");
        return user;
    }

    public User login(){
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

    public void deposit(User user){
        System.out.println("请输入要存款的金额：");
        BigDecimal money = InputValidator.isValidBigDecimal();
        Transaction deposit = new Transaction(user.getId(), "deposit", money, user.getCard_number(), user.getCard_number());
        if(Service.serviceDAO(deposit,String.valueOf(user.getCard_number()))){
            System.out.println("存款成功");
            return;
        }
        System.out.println("存款失败");

    }

    public void withdraw(User user){

        System.out.println("请输入要取款的金额：");
        BigDecimal money = InputValidator.isValidBigDecimal();
        Transaction withdraw = new Transaction(user.getId(), "withdraw", money, user.getCard_number(), user.getCard_number());
        if(Service.serviceDAO(withdraw,String.valueOf(user.getCard_number()))){
            System.out.println("取款成功");
            return;
        }
        System.out.println("取款失败");

    }

    public void transfer(User user){

        System.out.println("请输入要转账的金额：");
        BigDecimal money = InputValidator.isValidBigDecimal();
        System.out.println("请输入要转账的目标账户卡号");
        String target = InputValidator.isValidCardId();
        Transaction transfer = new Transaction(user.getId(), "transfer", money, user.getCard_number(), target);
        if(Service.serviceDAO(transfer,String.valueOf(user.getCard_number()))){
            System.out.println("转账成功");
            return;
        }
        System.out.println("转账失败");

    }

    public void findTransaction(User user){

        List<Transaction> transactions = transactionDAO.findTransactionByCardID(user.getCard_number());
        if(transactions.isEmpty()){
            System.out.println("暂无交易记录");
            return;
        }
        for(Transaction transaction : transactions){
            System.out.println("交易编号："+transaction.getId());
            System.out.println("交易类型："+transaction.getType());
            System.out.println("交易金额："+transaction.getAmount());
            if(transaction.getType().equals("transfer")){
                System.out.println("交易内容：由卡号"+transaction.getSourceCard()+"向"+transaction.getTargetCard()+"转账");
            }
            System.out.println("-------------------------------------------");
        }

        System.out.println("是否导出账单？(Y/N)");
        if(InputValidator.isValidYes()){
            String filePath = "transactions.csv";
            String content = transactions.stream()
                    .map(Transaction::toCsvString)
                    .collect(Collectors.joining("\n"));
            String head = "id,user_id,type,amount,sourceCard,targetCard,transactionDate";
            try(FileOutputStream fos = new FileOutputStream(filePath);
                BufferedOutputStream bos = new BufferedOutputStream(fos,8192)){
                bos.write((head+"\n").getBytes(StandardCharsets.UTF_8));
                bos.write(content.getBytes(StandardCharsets.UTF_8));
            }catch (IOException e){
                System.out.println("导出失败"+e.getMessage());
            }
        }else{
            return;
        }


    }
}
