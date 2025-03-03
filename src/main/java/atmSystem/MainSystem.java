package atmSystem;

import model.User;
import service.ServiceImpl.ATMServiceImpl;
import util.InputValidator;
import util.DatabaseInitializer;

/**
 * @author confff
 */
public class MainSystem {
    public static void main(String[] args) {
        if(!DatabaseInitializer.initializeDatabase()){
            System.out.println("数据库初始化失败");
            return;
        }
        User user = null;
        ATMServiceImpl atmService = new ATMServiceImpl();

        System.out.println("欢迎使用ATM系统");


        while (true) {
            System.out.println("-------------------------");
            System.out.println("请选择登录或注册账户：");
            System.out.println("1.登录");
            System.out.println("2.注册");
            System.out.println("0.退出系统");
            int choice = InputValidator.isValidInt(2);
            user = null;
            switch (choice) {
                case 1:
                    user = atmService.login();
                    if (user == null) {
                        continue; // 如果登录失败，跳过当前循环
                    }
                    break;
                case 2:
                    user = atmService.register();
                    if (user == null) {
                        continue; // 如果注册失败，跳过当前循环
                    }
                    break;
                case 0:
                    return; // 直接退出当前方法
                default:
                    System.out.println("获取账户失败");
                    return; // 退出当前方法
            }
            break;
        }

        while (true) {
            System.out.println("请选择要进行的业务：");
            System.out.println("1.存款");
            System.out.println("2.取款");
            System.out.println("3.转账");
            System.out.println("4.查询交易记录");
            System.out.println("0.退出系统");
            int choice1 = InputValidator.isValidInt(4);
            switch (choice1) {
                case 1:
                    atmService.deposit(user);
                    break;
                case 2:
                    atmService.withdraw(user);
                    break;
                case 3:
                    atmService.transfer(user);
                    break;
                case 4:
                    atmService.findTransaction(user);
                    break;
                case 0:
                    return; // 直接退出当前方法
            }
            System.out.println("是否继续使用(Y/N)：");
            if(InputValidator.isValidYes()){
                continue;
            }else{
                break;
            }
        }
    }

    public static void userSession(){
        if(!DatabaseInitializer.initializeDatabase()){
            System.out.println("数据库初始化失败");
            return;
        }
        User user = null;
        ATMServiceImpl atmService = new ATMServiceImpl();

        System.out.println("欢迎使用ATM系统");


        while (true) {
            System.out.println("-------------------------");
            System.out.println("请选择登录或注册账户：");
            System.out.println("1.登录");
            System.out.println("2.注册");
            System.out.println("0.退出系统");
            int choice = InputValidator.isValidInt(2);
            user = null;
            switch (choice) {
                case 1:
                    user = atmService.login();
                    if (user == null) {
                        continue; // 如果登录失败，跳过当前循环
                    }
                    break;
                case 2:
                    user = atmService.register();
                    if (user == null) {
                        continue; // 如果注册失败，跳过当前循环
                    }
                    break;
                case 0:
                    return; // 直接退出当前方法
                default:
                    System.out.println("获取账户失败");
                    return; // 退出当前方法
            }
            break;
        }

        while (true) {
            System.out.println("请选择要进行的业务：");
            System.out.println("1.存款");
            System.out.println("2.取款");
            System.out.println("3.转账");
            System.out.println("4.查询交易记录");
            System.out.println("0.退出系统");
            int choice1 = InputValidator.isValidInt(4);
            switch (choice1) {
                case 1:
                    atmService.deposit(user);
                    break;
                case 2:
                    atmService.withdraw(user);
                    break;
                case 3:
                    atmService.transfer(user);
                    break;
                case 4:
                    atmService.findTransaction(user);
                    break;
                case 0:
                    return; // 直接退出当前方法
            }
            System.out.println("是否继续使用(Y/N)：");
            if(InputValidator.isValidYes()){
                continue;
            }else{
                break;
            }
        }
    }
}
