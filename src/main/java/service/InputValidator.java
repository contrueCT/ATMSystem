package service;

import java.math.BigDecimal;
import java.util.Scanner;

public class InputValidator {
    public static boolean isValidYes() {
        while(true){
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if(input.length() != 1){
                System.out.println("请输入Y/N");
                continue;
            }
            if(input.charAt(0)!='Y'&&input.charAt(0)!='N'){
                System.out.println("请输入Y/N");
                continue;
            }
            if(input.charAt(0)=='Y'){
                return true;
            }else{
                return false;
            }
        }
    }

    public static BigDecimal isValidBigDecimal() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                String input = sc.nextLine().trim().replace(",", "");
                if(input.isEmpty()){
                    System.out.println("输入格式错误，请重新输入");
                    continue;
                }
                BigDecimal money = new BigDecimal(input);
                if(money.compareTo(BigDecimal.ZERO)>0){
                    return money;
                }else{
                    System.out.println("请输入正数");
                    continue;
                }
            } catch(NumberFormatException e)  {
                System.out.println("输入格式错误，请重新输入");
            }
        }
    }
}
